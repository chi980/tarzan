package com.mjutarzan.tarzan.domain.test;

import io.lettuce.core.protocol.ProtocolKeyword;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends Item{

    @Column(name = "book_author")
    private String author;

    @Builder(builderMethodName = "bookBuilder")
    public Book(Long id, String name, Integer price, String author) {
        super(id, name, price);
        this.author = author;
    }

}
