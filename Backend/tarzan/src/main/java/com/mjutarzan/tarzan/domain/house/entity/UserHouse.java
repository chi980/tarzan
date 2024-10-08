package com.mjutarzan.tarzan.domain.house.entity;

import com.mjutarzan.tarzan.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
@Entity
@Getter
@DiscriminatorValue(value = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHouse extends House{

    @ManyToOne
    @JoinColumn(name = "user_house_register", nullable = true)
    private User register;

    @Builder
    public UserHouse(String name, String address, Point location, String category, User register){
        super(name, address, location, category);

        this.register = register;
        this.register.addUserHouse(this);
    }
}
