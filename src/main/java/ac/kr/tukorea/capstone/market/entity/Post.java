package ac.kr.tukorea.capstone.market.entity;

import ac.kr.tukorea.capstone.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post_table")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    private long id;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_content")
    private String postContent;

    @Column(name = "post_created_time")
    @CreationTimestamp
    private LocalDateTime postCreatedTime;

    @Column(name = "post_updated_time")
    @UpdateTimestamp
    private LocalDateTime postUpdatedTime;

    @Column(name = "price")
    private int price;

    @Column(name = "is_on_sales")
    private String isOnSales;

    @ManyToOne
    @JoinColumn(name = "market_id")
    private Market market;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
