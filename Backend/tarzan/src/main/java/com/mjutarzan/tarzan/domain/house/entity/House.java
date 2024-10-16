package com.mjutarzan.tarzan.domain.house.entity;

import com.mjutarzan.tarzan.domain.bookmark.entity.Bookmark;
import com.mjutarzan.tarzan.domain.review.entity.Review;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
@Table(name = "house")
public abstract class House extends CommonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    private Long id;

    @Column(name = "house_name", nullable = false)
    private String name;

    @Lob
    @Column(name = "house_address", nullable = false)
    private String address;

    @Column(columnDefinition = "geometry(Point,4326)", name = "house_location", nullable = false)
    private Point location;

    @Column(name = "house_category", nullable = false)
    private String category;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // 지연 로딩 설정
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // 지연 로딩 설정
    private List<Bookmark> bookmarkList = new ArrayList<>();


    protected House(String name, String address, Point location, String category){
        this.name = name;
        this.address = address;
        this.location = location;
        this.category = category;
    }

    // Review 추가 메서드
    public void addReview(Review review) {
        this.reviewList.add(review);
    }

    public void addBookmark(Bookmark bookmark){
        this.bookmarkList.add(bookmark);
    }
}
