package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.response.ChecklistResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.entity.ChecklistItem;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType;
import com.mjutarzan.tarzan.domain.bookmark.repository.ChecklistItemRepository;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ChecklistServiceImpl implements ChecklistService{

    private final UserRepository userRepository;
    private final ChecklistItemRepository checklistItemRepository;

    @Override
    public Map<String, ChecklistResponseDto> getChecklist(ChecklistType checklistType, UserDto loginedUserDto) {

        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        List<ChecklistItem> checklist = checklistItemRepository.findByUserIdAndType(loginedUser.getId(), checklistType);
        if(checklist.size() == 0) {
            throw new ResourceNotFoundException("체크리스트가 존재하지 않습니다.");
        }

        Map<String, ChecklistResponseDto> result = checklist.stream()
                // title별로 그룹화
                .collect(Collectors.groupingBy(ChecklistItem::getTitle))
                // 각 그룹을 ChecklistResponseDto로 변환
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,  // title을 key로 사용
                        entry -> {
                            List<ChecklistItem> items = entry.getValue();
                            return ChecklistResponseDto.builder()
                                    .count((long) items.size())  // 아이템 수
                                    .nameList(items.stream()
                                            .map(ChecklistItem::getName)
                                            .collect(Collectors.toList()))  // name 리스트
                                    .valueList(items.stream()
                                            .map(ChecklistItem::getValue)
                                            .collect(Collectors.toList()))  // value 리스트
                                    .build();
                        }
                ));
        return result;
    }

    @Override
    public void createChecklist(ChecklistType checklistType, Map<String, ChecklistResponseDto> requestDto, UserDto loginedUserDto) {

        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();


        // ChecklistItem 목록 생성
        List<ChecklistItem> checklistItems = requestDto.entrySet().stream()
                .flatMap(entry -> {
                    String title = entry.getKey();
                    ChecklistResponseDto checklistResponseDto = entry.getValue();
                    Long count = checklistResponseDto.getCount();
                    List<String> nameList = checklistResponseDto.getNameList();
                    List<Boolean> valueList = checklistResponseDto.getValueList();

                    // 체크리스트 갯수가 동일하지 않을 경우 예외 처리
                    if (nameList.size() != count || valueList.size() != count) {
                        throw new IllegalArgumentException("체크리스트 갯수가 동일하지 않습니다.");
                    }

                    // IntStream을 사용하여 index로 ChecklistItem 생성
                    return IntStream.range(0, count.intValue())
                            .mapToObj(idx -> ChecklistItem.builder()
                                    .type(checklistType)
                                    .title(title)
                                    .order(idx) // 순서는 0부터 시작
                                    .name(nameList.get(idx))
                                    .value(valueList.get(idx))
                                    .user(loginedUser)
                                    .build());
                })
                .collect(Collectors.toList());

        // ChecklistItem을 DB에 저장
        checklistItemRepository.saveAll(checklistItems);
    }
}
