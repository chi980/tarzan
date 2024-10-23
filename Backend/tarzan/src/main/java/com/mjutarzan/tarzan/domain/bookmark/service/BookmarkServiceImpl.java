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
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
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

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService{

    private final ApiHouseRepository apiHouseRepository;
    private final UserHouseRepository userHouseRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkChecklistItemRepository bookmarkChecklistItemRepository;

    private final LocationService locationService;


    @Override
    public void createBookmarkWithApiHouse(BookmarkWithApiHouseRequestDto requestDto, UserDto loginedUserDto) {
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
    public void createBookmarkWithUserHouse(BookmarkWithUserHouseRequestDto requestDto, UserDto loginedUserDto) {
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
    public BookmarkListResponseDto getBookmarks(BookmarkListRequestDto requestDto, UserDto loginedUserDto) {
        Pageable pageable = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSort());
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        Page<BookmarkListItemResponseDto> bookmarkPages = bookmarkRepository.findAllBookmarksByUserIdAndStatus(loginedUser.getId(), requestDto.getStatus(), pageable);

        return BookmarkListResponseDto.builder()
                .count(bookmarkPages.getTotalElements())
                .list(bookmarkPages.getContent())
                .build();
    }

    @Override
    public BookmarkDetailResponseDto getBookmark(Long bookmarkIdx, UserDto loginedUserDto) {
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
    public void updateBookmark(Long bookmarkIdx, UpdateBookmarkRequestDto requestDto, UserDto loginedUserDto) {
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();
        Bookmark bookmark = bookmarkRepository.findById(bookmarkIdx).orElseThrow();

        if (!bookmark.getUser().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("북마크의 등록자만 수정할 수 있습니다.");
        }

        bookmark.update(requestDto);
        List<BookmarkChecklistItem> checklist = bookmarkChecklistItemRepository.findAllById(requestDto.getChecklist().keySet().stream().collect(Collectors.toList()));
        checklist.forEach(bookmarkChecklistItem -> {
            bookmarkChecklistItem.update(requestDto.getChecklist().get(bookmarkChecklistItem.getId()));
        });
    }

    @Override
    public void deleteBookmark(Long bookmarkIdx, UserDto loginedUserDto) {
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
    public CompareBookmarkResponseDto compareBookmarks(CompareBookmarksRequestDto compareBookmarksRequestDto, UserDto loginedUserDto) {
        if(compareBookmarksRequestDto.getIdList().size() <= 1 || compareBookmarksRequestDto.getIdList().size()>3){
            throw new IllegalArgumentException("2개 이상 3개 이하의 집들만 비교할 수 있습니다.");
        }

        List<Bookmark> bookmarks = bookmarkRepository.findAllById(compareBookmarksRequestDto.getIdList().stream().collect(Collectors.toList()));
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
            Map<HouseIndexType, Long> indexes = getHouseIndexesScore(bookmark.getHouse());
            Map<BookmarkChecklistType, Long> checks = getCheckListScore(bookmark.getCheckListItemList());
            
            return CompareBookmarkDetailResponseDto.builder()
                    .id(house.getId())
                    .name(house.getName())
                    .address(house.getAddress())
                    .category(house.getCategory())
                    .score(80)  // 항상 100점
                    .indexes(indexes)
                    .checks(checks)
                    .build();
        }).collect(Collectors.toList());

        return CompareBookmarkResponseDto.builder()
                .list(list)
                .build();
    }

    private Map<BookmarkChecklistType, Long> getCheckListScore(List<BookmarkChecklistItem> checkListItemList) {
        return checkListItemList.stream()
                // type이 CHECK로 시작하는 항목만 필터링
                .filter(item -> item.getType().name().startsWith("CHECK"))
                // type별로 그룹화
                .collect(Collectors.groupingBy(
                        BookmarkChecklistItem::getType,  // type으로 그룹화
                        // 각 그룹에서 value가 true인 항목을 카운트
                        Collectors.filtering(
                                BookmarkChecklistItem::getValue, // value가 true인 항목만 필터링
                                Collectors.counting() // true인 항목을 카운트
                        )
                ));

    }

    private Map<HouseIndexType, Long> getHouseIndexesScore(House house) {
        Point location = house.getLocation();
        return null;
    }
}
