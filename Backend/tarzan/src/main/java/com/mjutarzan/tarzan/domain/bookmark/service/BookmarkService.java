package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkListRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithApiHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.BookmarkWithUserHouseRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.request.UpdateBookmarkRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkDetailResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListResponseDto;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

public interface BookmarkService {
    void createBookmarkWithApiHouse(BookmarkWithApiHouseRequestDto bookmarkRequestDto, UserDto userDto);

    void createBookmarkWithUserHouse(BookmarkWithUserHouseRequestDto bookmarkRequestDto, UserDto userDto);

    BookmarkListResponseDto getBookmarks(BookmarkListRequestDto bookmarkListRequestDto, UserDto userDto);

    BookmarkDetailResponseDto getBookmark(Long bookmarkIdx, UserDto userDto);

    void updateBookmark(Long bookmarkIdx, UpdateBookmarkRequestDto updateBookmarkRequestDto, UserDto userDto);

    void deleteBookmark(Long bookmarkIdx, UserDto userDto);
}
