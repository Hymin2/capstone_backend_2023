package ac.kr.tukorea.capstone.entity;


import javax.persistence.*;
import java.util.Set;

@Entity(name = "phone") // 엔터티 이름 설정, 코드 내에서 엔터티 이름으로 불러오기 가능
@Table(name = "phone_table") // 데이터베이스에서 쓰는 테이블 이름
public class PhoneEntity {
    @Id // pk
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "id",
            nullable = false
    )
    private int id;

    @Column(
            name = "phone_name",
            nullable = false
    )
    private String phone_name;

    @Column(
            name = "display_name",
            nullable = false
    )
    private String display_name;

    @Column(
            name = "display_size",
            nullable = false
    )
    private String display_size;

    @Column(
            name = "resolution",
            nullable = false
    )
    private String resolution;

    @Column(
            name = "user_interface",
            nullable = false
    )
    private String user_interface;

    @Column(
            name = "main_camera",
            nullable = false
    )
    private String main_camera;

    @Column(
            name = "front_camera",
            nullable = false
    )
    private String front_camera;

    @Column(
            name = "battery",
            nullable = false
    )
    private String battery;

    @Column(
            name = "ram",
            nullable = false
    )
    private String ram;

    @Column(
            name = "memory",
            nullable = false
    )
    private String memory;

    @ManyToOne
    @JoinColumn(name = "ap_name")
    private BenchmarkEntity ap_name;

    @OneToMany(mappedBy = "phone_idx")
    private Set<UsedMarketPriceEntity> phone_idx;
}
