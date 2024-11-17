package com.mjutarzan.tarzan.domain.house.api.request;

import com.mjutarzan.tarzan.global.common.dto.CustomPageable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class HouseSearchRequestDto  extends CustomPageable {
    private String search;

    public HouseSearchRequestDto(int page, int size, String search) {
        super(page, size, Sort.unsorted());
        this.search = search;
    }
}
