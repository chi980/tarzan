package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.request.*;
import com.mjutarzan.tarzan.domain.bookmark.api.response.*;
import com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark;
import com.mjutarzan.tarzan.domain.bookmark.entity.BookmarkChecklistItem;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkChecklistType;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkStatus;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.HouseIndexType;
import com.mjutarzan.tarzan.domain.bookmark.repository.BookmarkChecklistItemRepository;
import com.mjutarzan.tarzan.domain.bookmark.repository.BookmarkRepository;
import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.entity.House;
import com.mjutarzan.tarzan.domain.house.entity.UserHouse;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import com.mjutarzan.tarzan.domain.house.repository.UserHouseRepository;
import com.mjutarzan.tarzan.domain.map.entity.Building;
import com.mjutarzan.tarzan.domain.map.entity.amenity.Amenity;
import com.mjutarzan.tarzan.domain.map.entity.clinic.Clinic;
import com.mjutarzan.tarzan.domain.map.entity.security.Security;
import com.mjutarzan.tarzan.domain.map.entity.shopping.Shopping;
import com.mjutarzan.tarzan.domain.map.entity.transportation.Transportation;
import com.mjutarzan.tarzan.domain.map.model.vo.BuildingCategory;
import com.mjutarzan.tarzan.domain.map.repository.BuildingRepository;
import com.mjutarzan.tarzan.domain.map.service.IndexService;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.ResourceNotFoundException;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import com.mjutarzan.tarzan.global.common.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
// @Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService{

    private final ApiHouseRepository apiHouseRepository;
    private final UserHouseRepository userHouseRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkChecklistItemRepository bookmarkChecklistItemRepository;
    private final BuildingRepository buildingRepository;

    private static final Double radiusHaveToCheck = 0.05;

    private final LocationService locationService;
    private final IndexService indexService;


    @Override
    public void createBookmarkWithApiHouse(BookmarkWithApiHouseRequestDto requestDto, CustomUserDetails loginedUserDto) {
        ApiHouse apiHouse = apiHouseRepository.findById(requestDto.getHouseId()).orElseThrow();
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        Bookmark bookmark = Bookmark.builder()
                .house(apiHouse)
                .user(loginedUser)
                .status(BookmarkStatus.CHECK_PENDING)
                .build();

        Bookmark save = bookmarkRepository.save(bookmark);

        List<BookmarkChecklistItem> checkList = createCheckList(save);

        bookmarkChecklistItemRepository.saveAll(checkList);
    }

    @Override
    public void createBookmarkWithUserHouse(BookmarkWithUserHouseRequestDto requestDto, CustomUserDetails loginedUserDto) {
        Double latitude = requestDto.getHouseLatitude();
        Double longitude = requestDto.getHouseLongitude();
        Point location = locationService.createPoint(latitude, longitude);

        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        UserHouse userHouse = UserHouse.builder()
                .name(requestDto.getHouseName())
                .address(requestDto.getHouseAddress())
                .location(location)
                .category(requestDto.getHouseCategory())
                .register(loginedUser)
                .build();

        UserHouse savedHouse = userHouseRepository.save(userHouse);

        Bookmark bookmark = Bookmark.builder()
                .house(savedHouse)
                .user(loginedUser)
                .status(BookmarkStatus.CHECK_PENDING)
                .build();

        Bookmark save = bookmarkRepository.save(bookmark);


        List<BookmarkChecklistItem> checkList = createCheckList(save);
        bookmarkChecklistItemRepository.saveAll(checkList);
    }

    private List<BookmarkChecklistItem> createCheckList(Bookmark save) {
        return Arrays.stream(BookmarkChecklistType.values())
                .flatMap(bookmarkChecklistType ->
                        // names 리스트를 순회하며 BookmarkChecklistItem을 생성
                        bookmarkChecklistType.getNames().stream()
                                .map(name -> BookmarkChecklistItem.builder()
                                        .type(bookmarkChecklistType)                          // 타입 설정
                                        .title(bookmarkChecklistType.getKor())                // 제목 설정
                                        .order(bookmarkChecklistType.getNames().indexOf(name)) // order를 names의 인덱스로 설정
                                        .name(name)                                           // name 설정
                                        .value(false)                                         // value는 항상 false
                                        .bookmark(save)                                       // bookmark는 매개변수로 받은 save 사용
                                        .build()
                                )
                )
                .collect(Collectors.toList());
    }

    @Override
    public BookmarkListResponseDto getBookmarks(BookmarkListRequestDto requestDto, CustomUserDetails loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        Page<Bookmark> bookmarkPages = null;
        if(requestDto.getStatus() == BookmarkStatus.ALL){
            bookmarkPages = bookmarkRepository.findByUserId(loginedUser.getId(), pageable);
        }else {
            bookmarkPages = bookmarkRepository.findAllBookmarksByUserIdAndStatus(loginedUser.getId(), requestDto.getStatus(), pageable);
        }

        log.info("{}", loginedUser.getId());
        log.info("{}", bookmarkPages.getTotalElements());
        return BookmarkListResponseDto.builder()
                .count(bookmarkPages.getTotalElements())
                .list(bookmarkPages.getContent().stream()
                        .map(bookmark -> BookmarkListItemResponseDto
                                .builder()
                                .id(bookmark.getId())
                                .houseId(bookmark.getHouse().getId())
                                .houseName(bookmark.getHouse().getName())
                                .houseAddress(bookmark.getHouse().getAddress())
                                .houseCategory(bookmark.getHouse().getCategory())
//                                이거 고민해보자
                                .createdAt(bookmark.getCreatedAt())
                                .build())
                        .collect(Collectors.toList())
                )
                .isNext(bookmarkPages.hasNext())
                .build();
    }

    @Override
    public BookmarkDetailResponseDto getBookmark(Long bookmarkIdx, CustomUserDetails loginedUserDto) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkIdx).orElseThrow();
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        Map<String, BookmarkChecklistResponseDto> checklist = bookmark.getCheckListItemList().stream()
                // type을 기준으로 그룹화
                .collect(Collectors.groupingBy(
                        item -> item.getType().name(), // type을 key로 사용
                        Collectors.collectingAndThen(
                                // 그룹화된 BookmarkChecklistItem들을 order 순으로 정렬
                                Collectors.toList(),
                                list -> {
                                    list.sort(Comparator.comparing(BookmarkChecklistItem::getOrder)); // order 순으로 정렬

                                    List<Long> idList = list.stream()
                                            .map(BookmarkChecklistItem::getId)
                                            .collect(Collectors.toList());

                                    List<String> nameList = list.stream()
                                            .map(BookmarkChecklistItem::getName)
                                            .collect(Collectors.toList());

                                    List<Integer> orderList = list.stream()
                                            .map(BookmarkChecklistItem::getOrder)
                                            .collect(Collectors.toList());

                                    List<Boolean> valueList = list.stream()
                                            .map(BookmarkChecklistItem::getValue)
                                            .collect(Collectors.toList());



                                    return BookmarkChecklistResponseDto.builder()
                                            .count(list.size())
                                            .title(list.get(0).getType().name())
                                            .idList(idList)
                                            .orderList(orderList)
                                            .nameList(nameList)
                                            .valueList(valueList)
                                            .build();
                                }
                        )
                ));



        return BookmarkDetailResponseDto.builder()
                .id(bookmark.getId())
                .leaseType(bookmark.getLeaseType())
                .rent(bookmark.getRent())
                .deposit(bookmark.getDeposit())
                .commissionFee(bookmark.getCommissionFee())
                .managementFee(bookmark.getManagementFee())
                .realEstate(bookmark.getRealEstate())
                .realEstatePhoneNumber(bookmark.getRealEstatePhoneNumber())
                .canAnimal(bookmark.getCanAnimal())
                .parkingLogCoverage(bookmark.getParkingLotCoverage())
                .roomCnt(bookmark.getRoomCnt())
                .bathRoomCnt(bookmark.getBathRoomCnt())
                .availableMoveInDate(bookmark.getAvailableMoveInDate())
                .floor(bookmark.getFloor())
                .direction(bookmark.getDirection())
                .checklist(checklist)
                .build();
    }

    @Override
    public void updateBookmark(Long bookmarkIdx, UpdateBookmarkRequestDto requestDto, CustomUserDetails loginedUserDto) {
        // 1) 권한 및 엔티티 조회
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        Bookmark bookmark = bookmarkRepository.findById(bookmarkIdx).orElseThrow();

        if (!bookmark.getUser().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("북마크의 등록자만 수정할 수 있습니다.");
        }

        // 2) 기본 북마크 필드 업데이트
        bookmark.update(requestDto);

        // 3) checklist 가 넘어왔으면 한 번에 flatten → 업데이트
        Map<String, List<BookmarkChecklistResponseDto2>> groups
                = requestDto.getChecklist();
        if (groups != null && !groups.isEmpty()) {
            // 3-1) Map<String, List<Dto>> → Stream<Dto> → Map<id, value>
            Map<Long, Boolean> flat = groups.values().stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toMap(
                            BookmarkChecklistResponseDto2::getId,
                            BookmarkChecklistResponseDto2::getValue
                    ));

            // 3-2) DB 에서 해당 ID 목록만 조회
            List<BookmarkChecklistItem> items
                    = bookmarkChecklistItemRepository.findAllById(flat.keySet());

            // 3-3) 각 엔티티의 값만 update() 호출
            items.forEach(item -> {
                Boolean newVal = flat.get(item.getId());
                if (newVal != null) {
                    item.update(newVal);
                }
            });
        }
    }


    @Override
    public void updateBookmarkChecklist(Long bookmarkIdx, UpdateBookmarkChecklistRequestDto requestDto, CustomUserDetails loginedUserDto) {
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        Bookmark bookmark = bookmarkRepository.findById(bookmarkIdx).orElseThrow();

        if (!bookmark.getUser().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("북마크의 등록자만 수정할 수 있습니다.");
        }
        List<BookmarkChecklistItem> checklist = bookmarkChecklistItemRepository.findAllById(requestDto.getChecklist().keySet().stream().collect(Collectors.toList()));
        checklist.forEach(bookmarkChecklistItem -> {
            bookmarkChecklistItem.update(requestDto.getChecklist().get(bookmarkChecklistItem.getId()));
        });
    }

    @Override
    public BookmarkDetailResponseDto2 getBookmark2(Long bookmarkIdx, CustomUserDetails loginedUserDto) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkIdx).orElseThrow();
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        if (!bookmark.getUser().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("북마크의 등록자만 수정할 수 있습니다.");
        }
        log.info("getBOokmrk2");


        Map<String, List<BookmarkChecklistResponseDto2>> checklist = bookmark.getCheckListItemList().stream()
                .collect(Collectors.groupingBy(
                        item -> item.getType().name(), // key: BookmarkChecklistType.name()
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    return list.stream()
                                            .map(item -> BookmarkChecklistResponseDto2.builder()
                                                    .id(item.getId())
                                                    .label(item.getName())
                                                    .value(item.getValue())
                                                    .build())
                                            .collect(Collectors.toList());
                                }
                        )
                ));


        return BookmarkDetailResponseDto2.builder()
                .id(bookmark.getId())
                .leaseType(bookmark.getLeaseType())
                .rent(bookmark.getRent())
                .deposit(bookmark.getDeposit())
                .commissionFee(bookmark.getCommissionFee())
                .managementFee(bookmark.getManagementFee())
                .realEstate(bookmark.getRealEstate())
                .realEstatePhoneNumber(bookmark.getRealEstatePhoneNumber())
                .canAnimal(bookmark.getCanAnimal())
                .parkingLogCoverage(bookmark.getParkingLotCoverage())
                .roomCnt(bookmark.getRoomCnt())
                .bathRoomCnt(bookmark.getBathRoomCnt())
                .availableMoveInDate(bookmark.getAvailableMoveInDate())
                .floor(bookmark.getFloor())
                .direction(bookmark.getDirection())
                .checklist(checklist)
                .build();
    }

    @Override
    public void deleteBookmark(Long bookmarkIdx, CustomUserDetails loginedUserDto) {
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        Bookmark bookmark = bookmarkRepository.findById(bookmarkIdx).orElseThrow();

        if (!bookmark.getUser().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("북마크의 등록자만 삭제할 수 있습니다.");
        }
        House house = bookmark.getHouse();
        if (house instanceof UserHouse) {
            UserHouse userHouse = (UserHouse) house;
            userHouseRepository.delete(userHouse);
        }

        bookmarkRepository.delete(bookmark);
    }

    @Override
    public CompareBookmarkResponseDto compareBookmarks(CompareBookmarksRequestDto compareBookmarksRequestDto, CustomUserDetails loginedUserDto) {
        if(compareBookmarksRequestDto.getBookmark_ids().size() <= 1 || compareBookmarksRequestDto.getBookmark_ids().size()>3){
            throw new IllegalArgumentException("2개 이상 3개 이하의 집들만 비교할 수 있습니다.");
        }

        List<Bookmark> bookmarks = bookmarkRepository.findAllById(compareBookmarksRequestDto.getBookmark_ids().stream().collect(Collectors.toList()));
        if(bookmarks.size() <= 1 || bookmarks.size()>3){
            throw new IllegalArgumentException("북마크의 id를 잘못 전달했습니다.");
        }

        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        bookmarks.stream().forEach(bookmark -> {
            if(bookmark.getUser().getId() != loginedUser.getId()) throw new UnauthorizedException("북마크의 작성자만 비교할 수 있습니다.");
        });

        List<CompareBookmarkDetailResponseDto> list = bookmarks.stream().map(bookmark -> {
            House house = bookmark.getHouse();
            
            // index 구하는 과정
            Double longitude = house.getLocation().getX();  // Longitude (x)
            Double latitude = house.getLocation().getY();   // Latitude (y)
            Map<BuildingCategory, Double> radiuses = indexService.getRadius();
            Map<BuildingCategory, Long> indices = indexService.getIndex(longitude, latitude, radiuses);

            log.info("checklist 갯수:{}",bookmark.getCheckListItemList().size());
            Map<BookmarkChecklistType, Long> checks = getCheckListScore(bookmark.getCheckListItemList());


            Map<String, String>costs = new HashMap<>();
            costs.put("money-type", bookmark.getLeaseType() != null ? bookmark.getLeaseType().getKor() : "");
            costs.put("deposit", bookmark.getDeposit() != null ? Integer.toString(bookmark.getDeposit()) : "");
            costs.put("rent", bookmark.getRent() != null ? Integer.toString(bookmark.getRent()) : "");
            costs.put("utilities", bookmark.getManagementFee() != null ? Integer.toString(bookmark.getManagementFee()) : "");
            Map<String, String> details = new HashMap<>();
            details.put("pet", bookmark.getCanAnimal() != null ? (bookmark.getCanAnimal() ? "가능" : "불가능") : "");
            details.put("parking", bookmark.getParkingLotCoverage() != null ? Integer.toString(bookmark.getParkingLotCoverage()) : "");
            details.put("roomCnt", bookmark.getRoomCnt() != null ? Integer.toString(bookmark.getRoomCnt()) : "");
            details.put("bathroomCnt", bookmark.getBathRoomCnt() != null ? Integer.toString(bookmark.getBathRoomCnt()) : "");
            details.put("movingDay", bookmark.getAvailableMoveInDate() != null ? bookmark.getAvailableMoveInDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) : "");
            details.put("floor", bookmark.getFloor() != null ? Integer.toString(bookmark.getFloor()) : "");
            details.put("direction", bookmark.getDirection() != null ? bookmark.getDirection().getKor() : "");

            return CompareBookmarkDetailResponseDto.builder()
                    .id(bookmark.getId())
                    .houseId(house.getId())
                    .name(house.getName())
                    .address(house.getAddress())
                    .category(house.getCategory())
                    .score(getScore(indices, checks))  // 항상 100점
                    .costs(costs)
                    .details(details)
                    .indexes(indices)
                    .checks(checks)
                    .bookmarkCreatedAt(bookmark.getCreatedAt())
                    .build();
        }).collect(Collectors.toList());

        return CompareBookmarkResponseDto.builder()
                .list(list)
                .build();
    }

    private Integer getScore(Map<BuildingCategory, Long> indexes, Map<BookmarkChecklistType, Long> checks) {
        // 각 카테고리의 비율 (총합이 100%가 되도록 설정)
        Map<BuildingCategory, Double> categoryWeights = new HashMap<>();
        categoryWeights.put(BuildingCategory.AMENITY, 0.25);  // 25% (상업시설)
        categoryWeights.put(BuildingCategory.CLINIC, 0.2);    // 20% (의료시설)
        categoryWeights.put(BuildingCategory.SECURITY, 0.2);  // 20% (보안)
        categoryWeights.put(BuildingCategory.SHOPPING, 0.15); // 15% (쇼핑)
        categoryWeights.put(BuildingCategory.TRANSPORTATION, 0.2); // 20% (교통)
// 각 체크리스트 항목의 비율 (각 카테고리 내에서 총합이 100%가 되도록 설정)
        Map<BookmarkChecklistType, Double> checklistWeights = new HashMap<>();
        checklistWeights.put(BookmarkChecklistType.OPTION_UTILITY_ROOM, 0.12);  // 12%
        checklistWeights.put(BookmarkChecklistType.OPTION_LIVING_ROOM, 0.1);     // 10%
        checklistWeights.put(BookmarkChecklistType.OPTION_ROOM, 0.1);           // 10%
        checklistWeights.put(BookmarkChecklistType.OPTION_BATH_ROOM, 0.1);      // 10%
        checklistWeights.put(BookmarkChecklistType.OPTION_SECURITY, 0.15);      // 15%
        checklistWeights.put(BookmarkChecklistType.CHECK_WATER, 0.1);           // 10%
        checklistWeights.put(BookmarkChecklistType.CHECK_WINDOW, 0.1);          // 10%
        checklistWeights.put(BookmarkChecklistType.CHECK_BATHROOM, 0.1);        // 10%
        checklistWeights.put(BookmarkChecklistType.CHECK_SURROUNDINGS, 0.1);    // 10%
        checklistWeights.put(BookmarkChecklistType.CHECK_OPTION, 0.05);         // 5%
        checklistWeights.put(BookmarkChecklistType.CHECK_DETAIL, 0.05);         // 5%
        checklistWeights.put(BookmarkChecklistType.CHECK_SECURITY, 0.1);        // 10%
        checklistWeights.put(BookmarkChecklistType.CHECK_ETC, 0.05);            // 5%

        // 점수 초기화
        double totalScore = 0;
        double totalWeight = 0;

        // BuildingCategory에 대한 점수 계산 (카테고리 비율 반영)
        for (Map.Entry<BuildingCategory, Long> entry : indexes.entrySet()) {
            BuildingCategory category = entry.getKey();
            Long count = entry.getValue();

            // 카테고리별 비율
            double categoryWeight = categoryWeights.getOrDefault(category, 0.0);

            // 해당 카테고리의 비율에 따라 점수 계산
            double categoryScore = (count > 0) ? categoryWeight : 0;  // 항목이 있으면 비율을 반영하여 점수 부여
            totalScore += categoryScore;
            totalWeight += categoryWeight;  // 전체 가중치 더하기
        }

        // BookmarkChecklistType에 대한 점수 계산 (체크리스트 비율 반영)
        for (Map.Entry<BookmarkChecklistType, Long> entry : checks.entrySet()) {
            BookmarkChecklistType checklistType = entry.getKey();
            Long checkedCount = entry.getValue();

            // 체크리스트 항목별 비율
            double checklistWeight = checklistWeights.getOrDefault(checklistType, 0.0);

            // 체크된 항목에 비례한 점수 계산
            double checklistScore = (checkedCount > 0) ? checklistWeight : 0;
            totalScore += checklistScore;
            totalWeight += checklistWeight;  // 전체 가중치 더하기
        }

        // 비율로 계산한 점수 평균
        double finalScore = (totalWeight > 0) ? (totalScore / totalWeight) * 100 : 0;

        // 최종 점수를 0~100 범위로 제한
        return Math.max(0, Math.min(100, (int) finalScore));
    }

    private Map<BookmarkChecklistType, Long> getCheckListScore(List<BookmarkChecklistItem> checkListItemList) {
            // 1. CHECK_로 시작하는 항목만 필터링하여 초기화 (기본값 0)
            Map<BookmarkChecklistType, Long> result = Arrays.stream(BookmarkChecklistType.values())
                    .filter(type -> type.name().startsWith("CHECK"))  // CHECK_로 시작하는 항목만 필터링
                    .collect(Collectors.toMap(
                            type -> type, // BookmarkChecklistType을 key로 사용
                            type -> 0L // 기본값은 0
                    ));

            // 2. 각 항목에서 true인 것만 카운트하여 결과에 반영
            for (BookmarkChecklistItem item : checkListItemList) {
                if (item.getValue() != null && item.getValue()) {  // value가 true인 항목만 처리
                    BookmarkChecklistType type = item.getType();
                    if (result.containsKey(type)) {
                        result.put(type, result.get(type) + 1); // 해당 항목의 값을 증가
                    }
                }
            }

            return result;



    }

}
