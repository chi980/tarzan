package com.mjutarzan.tarzan.domain.map.entity.transportation;

import com.mjutarzan.tarzan.domain.map.model.vo.BicycleOperType;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@DiscriminatorValue("bicycle")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bicycle  extends Transportation{

    @Column(name="bicycle_unique_number", nullable = true)
    private String uniqueNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "bicycle_gu", nullable = true)
    private SiGunGu gu;

    @Column(name = "bicycle_lcd_cnt", nullable = true)
    private int lcdCnt;

    @Column(name = "bicycle_qr_cnt", nullable = true)
    private int qrCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "bicycle_oper_type", nullable = true)
    private BicycleOperType operType;

    @Builder(builderMethodName = "subwayBuilder")
    public Bicycle(String name, String address, Point location, String phoneNumber, String uniqueNumber, SiGunGu gu, int lcdCnt, int qrCnt, BicycleOperType operType){
        super(name, address, location, phoneNumber);
        this.uniqueNumber = uniqueNumber;
        this.gu = gu;
        this.lcdCnt = lcdCnt;
        this.qrCnt = qrCnt;
        this.operType = operType;
    }

}
