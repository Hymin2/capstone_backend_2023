package ac.kr.tukorea.capstone.product.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "detail_table")
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
    private List<ProductDetail> productDetails;
}
