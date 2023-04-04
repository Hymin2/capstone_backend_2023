package ac.kr.tukorea.capstone.product.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_detail_table")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "detail_id")
    private Detail detail;
}