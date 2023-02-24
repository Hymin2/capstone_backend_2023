package ac.kr.tukorea.capstone.entity;

import javax.persistence.*;

@Entity(name = "sale_image")
@Table(name = "sale_image_table")
public class SaleImageEntity {
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
            name = "image_path",
            nullable = false
    )
    private String image_path;

    @Column(
            name = "image_size",
            nullable = false
    )
    private int image_size;

    @Column(
            name = "create_time",
            nullable = false
    )
    private String create_time;
}
