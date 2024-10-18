package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.response.ChecklistResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;

import java.util.Map;

public interface ChecklistService {
    Map<String, ChecklistResponseDto> getChecklist(ChecklistType checklistType, UserDto userDto);

    void createChecklist(ChecklistType checklistType, Map<String, ChecklistResponseDto> requestDto, UserDto userDto);
}
