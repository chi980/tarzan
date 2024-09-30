package com.mjutarzan.tarzan.domain.test;

import com.mjutarzan.tarzan.global.common.dto.CustomPageable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Sort;


@Getter
@Setter
@ToString
public class MyPageable extends CustomPageable {
    private String gu;

    // Constructor for MyPageable
    public MyPageable(int page, int size, Sort sort, String gu) {
        super(page, size, null);
        this.gu = gu; // Initialize the gu field
        System.out.println(super.toString());
    }
}
