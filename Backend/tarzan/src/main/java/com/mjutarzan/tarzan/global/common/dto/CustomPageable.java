package com.mjutarzan.tarzan.global.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
public class CustomPageable implements Pageable {

    private int page;
    private int size;
    private Sort sort;

    public CustomPageable(int page, int size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort != null ? sort : Sort.unsorted(); // Default to unsorted if null
    }

    // Pageable 메서드 구현
    @Override
    public int getPageNumber() {
        return page;
    }

    @Override
    public int getPageSize() {
        return size;
    }

    @Override
    public long getOffset() {
        return (long) page * size;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new CustomPageable(page + 1, size, sort);
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new CustomPageable(page - 1, size, sort) : first();
    }

    @Override
    public Pageable first() {
        return new CustomPageable(0, size, sort);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new CustomPageable(pageNumber, size, sort); // Implementing withPage to return new instance
    }

    @Override
    public boolean hasPrevious() {
        return page > 0;
    }
}
