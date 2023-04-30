package ac.kr.tukorea.capstone.market.entity;

import ac.kr.tukorea.capstone.user.entity.User;

import javax.persistence.*;

@Entity
@Table(name = "market_table")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "market_name")
    private String market_name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
