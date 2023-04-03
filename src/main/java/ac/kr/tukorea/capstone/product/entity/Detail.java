package ac.kr.tukorea.capstone.product.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "detail_table")
@Getter
@RequiredArgsConstructor
@Builder
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "detail_name")
    private String detailName;

    @Column(name = "detail_content")
    private String  detailContent;

    @OneToMany(mappedBy = "product_detail_id", fetch = FetchType.LAZY)
    private List<ProductDetail> productDetails;
}
