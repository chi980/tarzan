package com.mjutarzan.tarzan.domain.bookmark.service;

import com.mjutarzan.tarzan.domain.bookmark.api.request.ChecklistRequestDto;
import com.mjutarzan.tarzan.domain.bookmark.api.response.ChecklistResponseDto;
import com.mjutarzan.tarzan.domain.bookmark.entity.ChecklistItem;
import com.mjutarzan.tarzan.domain.bookmark.model.vo.ChecklistType;
import com.mjutarzan.tarzan.domain.bookmark.repository.ChecklistItemRepository;
import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.domain.user.model.dto.UserDto;
import com.mjutarzan.tarzan.domain.user.repository.UserRepository;
import com.mjutarzan.tarzan.global.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        if(checklist.size() == 0 || checklistType.getCnt() != checklist.size()) {
//            없는 경우 신규 사용자이므로 만든다.
            log.info("{}", "신규 사용자이므로 생성");
            checklist = createChecklist(checklistType, loginedUser);
            checklist = checklistItemRepository.saveAllAndFlush(checklist);
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
                                    .idList(items.stream()
                                            .map(ChecklistItem::getId)
                                            .collect(Collectors.toList())
                                    )
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

    private List<ChecklistItem> createChecklist(ChecklistType checklistType, User user) {
        return checklistType.getNames().entrySet().stream()
                .flatMap(entry ->
                    entry.getValue().stream()
                            .map(name -> ChecklistItem.builder()
                                    .type(checklistType)
                                    .title(entry.getKey())
                                    .order(entry.getValue().indexOf(name))
                                    .name(name)
                                    .value(false)
                                    .user(user)
                                    .build())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void createChecklist(ChecklistType checklistType, ChecklistRequestDto requestDto, UserDto loginedUserDto) {

        if(requestDto.getCount() != requestDto.getValueList().size()){
            throw new IllegalArgumentException("리스트간 개수가 다릅니다.");
        }

        User loginedUser = userRepository.findByEmail(loginedUserDto.getEmail()).orElseThrow();

        List<Long> idList = requestDto.getValueList().keySet().stream().collect(Collectors.toList());
        List<ChecklistItem> checklist = checklistItemRepository.findAllByIdIn(idList);
        checklist.stream().forEach(checklistItem -> {
            if(!(checklistItem.getUser().getId() != loginedUser.getId())){
                throw new UnauthorizedException("북마크의 주인만 수정할 수 있습니다.");
            }
            checklistItem.update(requestDto.getValueList().get(checklistItem.getId()));
        });
    }
}
