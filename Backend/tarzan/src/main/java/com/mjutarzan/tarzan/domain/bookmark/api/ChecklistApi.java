package com.mjutarzan.tarzan.domain.bookmark.api;

import com.mjutarzan.tarzan.domain.bookmark.api.response.ChecklistResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType;
import com.mjutarzan.tarzan.domain.bookmark.service.ChecklistService;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.global.common.entity.BaseResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ChecklistApi {

    private final ChecklistService checklistService;

    @GetMapping("/checklist/mover")
    public ResponseEntity<?> getMoverChecklist(@AuthenticationPrincipal UserDto userDto){
        Map<String, ChecklistResponseDto> result =  checklistService.getChecklist(ChecklistType.ITEM, userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }
    @PostMapping("/checklist/mover")
    public ResponseEntity<?> createMoverChecklist(@RequestBody Map<String, ChecklistResponseDto> checklistRequestDto,@AuthenticationPrincipal UserDto userDto){

        checklistService.createChecklist(ChecklistType.MOVER, checklistRequestDto, userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("체크리스트가 성공적으로 등록되었습니다.")
                .build());
    }
    @GetMapping("/checklist/item")
    public ResponseEntity<?> getItemChecklist(@AuthenticationPrincipal UserDto userDto){
        Map<String, ChecklistResponseDto> result =  checklistService.getChecklist(ChecklistType.ITEM, userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("완료되었습니다.")
                .data(result)
                .build());
    }

    @PostMapping("/checklist/item")
    public ResponseEntity<?> createItemChecklist(@RequestBody Map<String, ChecklistResponseDto> checklistRequestDto,@AuthenticationPrincipal UserDto userDto){

        checklistService.createChecklist(ChecklistType.ITEM, checklistRequestDto, userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .success(true)
                .message("체크리스트가 성공적으로 등록되었습니다.")
                .build());
    }
}
