package com.mjutarzan.tarzan.domain.user.entity;

import com.mjutarzan.tarzan.domain.board.entity.Board;
import com.mjutarzan.tarzan.domain.board.entity.Comment;
import com.mjutarzan.tarzan.domain.user.model.vo.Role;
import com.mjutarzan.tarzan.domain.user.model.vo.SocialType;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users") // h2는 user가 키워드이므로 이름 지정
@ToString(of = {"id", "nickname", "role", "socialId","socialType"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    // 사용자 상태 정보

    // sns 로그인 -> 처음 로그인한 사용자는 GUEST 권한으로 추가 정보를 입력받기 위해 사용하는 필드
    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private Role role;

    // SNS 타입; KAKAO, GOOGLE
    @Column(name = "user_social_type")
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // 로그인한 소셜 타입의 식별자 값
    @Column(name = "user_social_id")
    private String socialId;

    @Column(name = "user_refreshToken")
    private String refreshToken;

    // 사용자 추가 정보
    @Column(name = "user_image_url")
    private String imageUrl;

    @Column(name = "user_nickname", unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_gu")
    private SiGunGu gu;

    @Column(name = "user_have_animal")
    @ColumnDefault("false")
    private boolean haveAnimal;

    @Column(name = "user_have_car")
    @ColumnDefault("false")
    private boolean haveCar;

    @Column(name = "user_job_address")
    private String jobAddress;

    @Column(columnDefinition = "geometry(Point,4326)", name = "user_job_location")
    private Point jobLocation;

    @OneToMany
    @JoinColumn
    private List<Board> boardList = new ArrayList<>();

    @OneToMany
    @JoinColumn
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public User(String email, String password, Role role, SocialType socialType, String socialId,
                String refreshToken, String imageUrl, String nickname, SiGunGu gu, boolean haveAnimal,
                boolean haveCar, String jobAddress, Point jobLocation) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.refreshToken = refreshToken;
        this.imageUrl = imageUrl;
        this.nickname = nickname;
        this.gu = gu;
        this.haveAnimal = haveAnimal;
        this.haveCar = haveCar;
        this.jobAddress = jobAddress;
        this.jobLocation = jobLocation;
    }
    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }
    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void addBoard(Board board) {
        this.boardList.add(board);
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
    }
}
