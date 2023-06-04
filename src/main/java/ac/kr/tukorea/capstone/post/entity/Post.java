package ac.kr.tukorea.capstone.post.entity;

import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostImage> postImages;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
