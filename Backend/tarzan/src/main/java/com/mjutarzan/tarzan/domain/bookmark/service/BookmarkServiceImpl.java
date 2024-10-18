package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkListRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithApiHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithUserHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.UpdateBookmarkRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkDetailResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark;
import com.mjutarzan.tarzan.domain.bookmark.repository.BookmarkRepository;
import com.mjutarzan.tarzan.domain.house.entity.ApiHouse;
import com.mjutarzan.tarzan.domain.house.entity.UserHouse;
import com.mjutarzan.tarzan.domain.house.repository.ApiHouseRepository;
import com.mjutarzan.tarzan.domain.house.repository.UserHouseRepository;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BookmarkServiceImpl implements BookmarkService{

    private final ApiHouseRepository apiHouseRepository;
    private final UserHouseRepository userHouseRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    private final LocationService locationService;


    @Override
    public void createBookmarkWithApiHouse(BookmarkWithApiHouseRequestDto requestDto, UserDto loginedUserDto) {
        ApiHouse apiHouse = apiHouseRepository.findById(requestDto.getHouseId()).orElseThrow();
        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        Bookmark bookmark = Bookmark.builder()
                .house(apiHouse)
                .user(loginedUser)
                .build();

        Bookmark savedBookmark = bookmarkRepository.save(bookmark);
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

        userHouseRepository.save(userHouse);

        Bookmark bookmark = Bookmark.builder()
                .house(userHouse)
                .user(loginedUser)
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Override
    public BookmarkListResponseDto getBookmarks(BookmarkListRequestDto bookmarkListRequestDto, UserDto userDto) {
        return null;
    }

    @Override
    public BookmarkDetailResponseDto getBookmark(Long bookmarkIdx, UserDto userDto) {
        return null;
    }

    @Override
    public void updateBookmark(Long bookmarkIdx, UpdateBookmarkRequestDto updateBookmarkRequestDto, UserDto userDto) {

    }

    @Override
    public void deleteBookmark(Long bookmarkIdx, UserDto userDto) {

    }
}
