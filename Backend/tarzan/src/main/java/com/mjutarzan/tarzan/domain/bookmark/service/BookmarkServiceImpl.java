package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkListRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithApiHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithUserHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.UpdateBookmarkRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkDetailResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListItemResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark;
import com.mjutarzan.tarzan.domain.bookmark.entity.BookmarkChecklistItem;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkChecklistType;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.BookmarkStatus;
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
import java.util.List;
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
        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();

        Page<BookmarkListItemResponseDto> bookmarkPages = bookmarkRepository.findAllBookmarksByUserIdAndStatus(loginedUser.getId(), requestDto.getStatus(), pageable);

        return BookmarkListResponseDto.builder()
                .count(bookmarkPages.getTotalElements())
                .list(bookmarkPages.getContent())
                .build();
    }

    @Override
    public BookmarkDetailResponseDto getBookmark(Long bookmarkIdx, UserDto userDto) {
        return null;
    }

    @Override
    public void updateBookmark(Long bookmarkIdx, UpdateBookmarkRequestDto updateBookmarkRequestDto, UserDto userDto) {

    }

    @Override
    public void deleteBookmark(Long bookmarkIdx, UserDto loginedUserDto) {
        User loginedUser = userRepository.findByNickname(loginedUserDto.getNickname()).orElseThrow();
        Bookmark bookmark = bookmarkRepository.findById(bookmarkIdx).orElseThrow();

        if (!bookmark.getUser().getId().equals(loginedUser.getId())) {
            throw new UnauthorizedException("북마크의 주인만 삭제할 수 있습니다.");
        }
        House house = bookmark.getHouse();
        if (house instanceof UserHouse) {
            UserHouse userHouse = (UserHouse) house;
            userHouseRepository.delete(userHouse);
        }

        bookmarkRepository.delete(bookmark);
    }
}
