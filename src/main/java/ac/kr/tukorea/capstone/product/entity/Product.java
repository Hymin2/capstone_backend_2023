package ac.kr.tukorea.capstone.product.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product_table")
@Getter
@RequiredArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "model_name")
    private String modelName;

    @Column(name = "company_name")
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product_detail_id", fetch = FetchType.LAZY)
    private List<ProductDetail> productDetails;

}
