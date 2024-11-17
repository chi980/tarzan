package com.mjutarzan.tarzan.domain.board.api.request;

import com.mjutarzan.tarzan.global.common.dto.CustomPageable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class CommentListRequestDto extends CustomPageable {

    private Long boardIdx;
    private String sortBy;

    @Builder
    public CommentListRequestDto(int page, int size, String sortBy, Long boardIdx){
        super(page, size, null);
        this.boardIdx = boardIdx;
        this.sortBy = sortBy;

        super.setSort(convertToSort(sortBy.trim()));
    }

    private Sort convertToSort(String sortBy) {
        if (sortBy.equals("최신순")) {
            return Sort.by(Sort.Direction.DESC, "createdAt");  // 최신순 정렬: createdAt 필드 기준으로 내림차순
        } else if (sortBy.equals("오래된순")) {
            return Sort.by(Sort.Direction.ASC, "createdAt");   // 오래된순 정렬: createdAt 필드 기준으로 오름차순
        } else {
            return Sort.unsorted(); // 기본값: 정렬하지 않음
        }
    }
}
