package com.mjutarzan.tarzan.domain.map.entity.transportation;

import com.mjutarzan.tarzan.domain.map.model.vo.BicycleOperType;
import com.mjutarzan.tarzan.domain.map.model.vo.BuildingType;
import com.mjutarzan.tarzan.global.common.dto.DataInstance;
import com.mjutarzan.tarzan.global.common.vo.SiGunGu;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@DiscriminatorValue("bicycle")
@NoArgsConstructor
@ToString(callSuper = true)
public class Bicycle  extends Transportation implements DataInstance {

    @Column(name="bicycle_unique_number", nullable = true)
    private Integer uniqueNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "bicycle_gu", nullable = true)
    private SiGunGu gu;

    @Column(name = "bicycle_lcd_cnt", nullable = true)
    private Double lcdCnt;

    @Column(name = "bicycle_qr_cnt", nullable = true)
    private Double qrCnt;

    @Enumerated(EnumType.STRING)
    @Column(name = "bicycle_oper_type", nullable = true)
    private BicycleOperType operType;

    @Builder(builderMethodName = "subwayBuilder")
    public Bicycle(String name, String address, Point location, String phoneNumber, Integer uniqueNumber, SiGunGu gu, Double lcdCnt, Double qrCnt, BicycleOperType operType){
        super(name, address, BuildingType.BICYCLE.getKor(), location, phoneNumber);
        this.uniqueNumber = uniqueNumber;
        this.gu = gu;
        this.lcdCnt = lcdCnt;
        this.qrCnt = qrCnt;
        this.operType = operType;
    }
    @Override
    public DataInstance getInstance(String[] info, Point location) {
//        ID,NAME,SIGUNGU,ADDRESS,LAT,LONG,OPEN_DAY,LCD_CNT,QR_CNT,OPER_TYPE
        this.setUniqueNumber(Integer.parseInt(info[0]));
        super.setName(info[1]);
        this.setGu(SiGunGu.fromKor(info[2].trim()));
        super.setAddress(info[3]);
        super.setLocation(location);
        if(!info[7].equals("")) this.setLcdCnt(Double.parseDouble(info[7]));
        if(!info[8].equals(""))this.setQrCnt(Double.parseDouble(info[8]));
        this.setOperType(BicycleOperType.valueOf(info[9].trim()));
        return this;
    }
}
