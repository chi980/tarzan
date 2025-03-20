package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.request.*;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkDetailResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.BookmarkListResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.CompareBookmarkResponseDto;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

public interface BookmarkService {
    void createBookmarkWithApiHouse(BookmarkWithApiHouseRequestDto bookmarkRequestDto, CustomUserDetails userDto);

    void createBookmarkWithUserHouse(BookmarkWithUserHouseRequestDto bookmarkRequestDto, CustomUserDetails userDto);

    BookmarkListResponseDto getBookmarks(BookmarkListRequestDto bookmarkListRequestDto, CustomUserDetails userDto);

    BookmarkDetailResponseDto getBookmark(Long bookmarkIdx, CustomUserDetails userDto);

    void updateBookmark(Long bookmarkIdx, UpdateBookmarkRequestDto updateBookmarkRequestDto, CustomUserDetails userDto);

    void deleteBookmark(Long bookmarkIdx, CustomUserDetails userDto);

    CompareBookmarkResponseDto compareBookmarks(CompareBookmarksRequestDto compareBookmarksRequestDto, CustomUserDetails userDto);
}
