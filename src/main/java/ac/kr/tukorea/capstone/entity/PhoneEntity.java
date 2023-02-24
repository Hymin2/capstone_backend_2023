package ac.kr.tukorea.capstone.entity;


import javax.persistence.*;

@Entity(name = "phone")
@Table(name = "phone_table")
public class PhoneEntity {
    @Id
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

}
