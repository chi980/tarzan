package com.mjutarzan.tarzan.domain.bookmark.entity;

import com.mjutarzan.tarzan.domain.user.entity.User;
import com.mjutarzan.tarzan.global.common.entity.CommonEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MoverCheckList extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mover_checklist_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User user;


    // 가전 제품 관련 체크리스트 항목 (이사 전 필수품)
    private Boolean router = false;
    private Boolean multiTap = false;
    private Boolean hairDryer = false;
    private Boolean microwave = false;
    private Boolean vacuum = false;
    private Boolean refrigerator = false;
    private Boolean airConditioner = false;
    private Boolean washingMachine = false;

    // 가전 제품 관련 체크리스트 항목 (이사 후 사도 되는 물품)
    private Boolean induction = false;
    private Boolean standLight = false;
    private Boolean airPurifier = false;
    private Boolean miniIroning = false;
    private Boolean fan = false;
    private Boolean electricMat = false;

    // 섬유 관련 체크리스트 항목 (이사 전 필수품)
    private Boolean mattress = false;
    private Boolean bedFrame = false;
    private Boolean blanket = false;
    private Boolean pillow = false;
    private Boolean mattressCover = false;
    private Boolean mattressPad = false;

    // 섬유 관련 체크리스트 항목 (이사 후 사도 되는 물품)
    private Boolean hanger = false;
    private Boolean diningTable = false;
    private Boolean vanityMirror = false;
    private Boolean fullLengthMirror = false;
    private Boolean bathMat = false;
    private Boolean rug = false;
    private Boolean basicTools = false;
    private Boolean curtain = false;

    // 욕실 용품 관련 체크리스트 항목 (이사 전 필수품)
    private Boolean toiletPaper = false;
    private Boolean toothbrush = false;
    private Boolean towelCount = false;
    private Boolean shampoo = false;
    private Boolean bodyCleanser = false;
    private Boolean soap = false;
    private Boolean basin = false;

    // 욕실 용품 관련 체크리스트 항목 (이사 후 사도 되는 물품)
    private Boolean bathroomSlippers = false;
    private Boolean bathroomCleaner = false;
    private Boolean cleaningGloves = false;
    private Boolean floorCleaningBrush = false;
    private Boolean plunger = false;
    private Boolean magicBlock = false;
    private Boolean cottonSwab = false;

    // 재료 관련 체크리스트 항목 (이사 전 필수품)
    private Boolean water = false;
    private Boolean kimchi = false;
    private Boolean instantRice = false;
    private Boolean ramen = false;
    private Boolean seaweed = false;

    // 재료 관련 체크리스트 항목 (이사 후 사도 되는 물품)
    private Boolean cookingOil = false;
    private Boolean saltSugar = false;
    private Boolean gochujang = false;
    private Boolean soySauce = false;
    private Boolean vinegar = false;
    private Boolean pepper = false;
    private Boolean eggs = false;
    private Boolean cannedTuna = false;
    private Boolean butter = false;
    private Boolean jam = false;

    // 주방 용품 관련 체크리스트 항목 (이사 전 필수품)
    private Boolean riceBowlSet = false;
    private Boolean spoonSet = false;
    private Boolean cup = false;
    private Boolean plasticWrap = false;
    private Boolean potForOneRamen = false;
    private Boolean sponge = false;
    private Boolean trashBags = false;

    // 주방 용품 관련 체크리스트 항목 (이사 후 사도 되는 물품)
    private Boolean fryingPan = false;
    private Boolean potForTwoRamen = false;
    private Boolean potHolder = false;
    private Boolean smallPlate = false;
    private Boolean largePlate = false;
    private Boolean knife = false;
    private Boolean longChopsticks = false;
    private Boolean tongs = false;
    private Boolean spatula = false;
    private Boolean cuttingBoard = false;
    private Boolean bowl = false;
    private Boolean strainer = false;
    private Boolean kitchenScissors = false;
    private Boolean sealedContainer = false;
    private Boolean disposableContainer = false;
    private Boolean dishDryingRack = false;
    private Boolean utensilHolder = false;
    private Boolean bakingSoda = false;
    private Boolean dishcloth = false;
    private Boolean rubberGloves = false;

    // 생활 용품 관련 체크리스트 항목 (이사 전 필수품)
//    private Boolean hanger; // 옷걸이
    private Boolean cutterKnife; // 커터칼
    private Boolean broom; // 빗자루(막대걸레, 정전기포)
    private Boolean boxTape; // 박스테이프
    private Boolean emergencyMedicine; // 비상약
    private Boolean foodWasteBag; // 음식물, 일반 쓰레기봉투
    private Boolean insecticide; // 에프킬라(바퀴벌레, 모기용)

    // 생활 용품 관련 체크리스트 항목 (이사 후 사도 되는 물품)
    private Boolean laundryDetergent; // 세탁세제, 섬유유연제
    private Boolean neutralDetergent; // 중성세제(울샴푸)
    private Boolean laundryBasket; // 빨래바구니, 빨래집게
    private Boolean laundryDryingRack; // 빨래건조대
    private Boolean trashBin; // 쓰레기통
    private Boolean laundryBag; // 세탁망
    private Boolean windowLock; // 창문 잠금장치
    private Boolean recyclingBin; // 분리수거함
    private Boolean mosquitoNet; // 물구멍방충망
    private Boolean cockroachTrap; // 바퀴벌레 트랩
    private Boolean miniSewingKit; // 미니반짇고리
    private Boolean nailClipper; // 손톱깎이
    private Boolean roller; // 돌돌이
    private Boolean umbrella; // 우산

}
