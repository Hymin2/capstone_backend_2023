package ac.kr.tukorea.capstone.product.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalTime;

@Entity
@Table(name = "used_product_price_table")
public class UsedProductPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private int price;

    @Column(name = "time")
    private Date time;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
