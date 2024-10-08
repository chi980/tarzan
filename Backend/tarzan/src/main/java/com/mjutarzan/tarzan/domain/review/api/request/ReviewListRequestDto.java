package com.mjutarzan.tarzan.domain.review.api.request;

import com.mjutarzan.tarzan.global.common.dto.CustomPageable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class ReviewListRequestDto extends CustomPageable {

    private Long houseIdx;
    private String sortBy;

    public ReviewListRequestDto(Long houseIdx, int page, int size, String sortBy) {
        super(page, size, null);

        this.houseIdx = houseIdx;
        this.sortBy = sortBy;

        super.setSort(convertToSort(sortBy.trim()));
    }

    private Sort convertToSort(String sortBy) {
        if (sortBy.equals("최신순")) {
            return Sort.by(Sort.Direction.DESC, "createdAt");  // 최신순 정렬: createdAt 필드 기준으로 내림차순
        } else {
            return Sort.unsorted(); // 기본값: 정렬하지 않음
        }
    }
}
