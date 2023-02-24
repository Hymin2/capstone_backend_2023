package ac.kr.tukorea.capstone.entity;


import javax.persistence.*;

@Entity(name = "used_market_price")
@Table(name = "used_market_price_table")
public class UsedMarketPriceEntity {
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
            name = "price",
            nullable = false
    )
    private int price;

    @Column(
            name = "time",
            nullable = false
    )
    private String time;

    @ManyToOne
    @JoinColumn(name = "phone_idx")
    private PhoneEntity phone_idx;
}
