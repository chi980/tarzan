package com.mjutarzan.tarzan.domain.board.api.request;

import com.mjutarzan.tarzan.global.common.dto.CustomPageable;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class BoardListRequestDto extends CustomPageable {
    @NotBlank
    private SiGunGu gu;
    private String tag;
    private String sortBy;

    @Builder
    public BoardListRequestDto(int page, int size, String sortBy, SiGunGu gu, String tag){
        super(page, size, null);
        this.gu = gu;
        this.tag = tag;
        this.sortBy = sortBy;

        super.setSort(convertToSort(sortBy.trim()));
    }

    private Sort convertToSort(String sortBy) {
        if (sortBy.trim().equals("최신순")) {
            return Sort.by(Sort.Direction.DESC, "createdAt");  // 최신순 정렬: createdAt 필드 기준으로 내림차순
        } else if (sortBy.equals("오래된순")) {
            return Sort.by(Sort.Direction.ASC, "createdAt");   // 오래된순 정렬: createdAt 필드 기준으로 오름차순
        } else if (sortBy.equals("조회수순")) {
            return Sort.by(Sort.Direction.DESC, "readCount");  // 조회수순 정렬: readCount 필드 기준으로 내림차순
        } else {
            return Sort.unsorted(); // 기본값: 정렬하지 않음
        }
    }
}
