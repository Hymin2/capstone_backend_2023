package ac.kr.tukorea.capstone.market.entity;

import ac.kr.tukorea.capstone.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "market_img_path")
    private String marketImagePath;

    @Column(name = "market_img_size")
    private long marketImageSize;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "market")
    private List<Post> posts;

    public void setMarketName(String marketName){
        this.marketName = marketName;
    }
    public void setMarketImagePath(String marketImagePath) {this.marketImagePath = marketImagePath;}
    public void setMarketImageSize(long marketImageSize) {this.marketImageSize = marketImageSize;}
}
