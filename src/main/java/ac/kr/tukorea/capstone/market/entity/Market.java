package ac.kr.tukorea.capstone.market.entity;

import ac.kr.tukorea.capstone.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "market_table")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "market_name", unique = true)
    private String marketName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void setMarketName(String marketName){
        this.marketName = marketName;
    }
}
