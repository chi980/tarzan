package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.request.ChecklistRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.ChecklistResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType;
import com.mjutarzan.tarzan.domain.user.entity.CustomUserDetails;

import java.util.Map;

public interface ChecklistService {
    Map<String, ChecklistResponseDto> getChecklist(ChecklistType checklistType, CustomUserDetails userDto);

    void createChecklist(ChecklistType checklistType, ChecklistRequestDto requestDto, CustomUserDetails userDto);
}
