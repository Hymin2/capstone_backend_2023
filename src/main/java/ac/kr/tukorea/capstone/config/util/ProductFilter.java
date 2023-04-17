package ac.kr.tukorea.capstone.config.util;

import ac.kr.tukorea.capstone.product.entity.QDetail;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public enum ProductFilter {
    PriceBetween400KAnd500K("1001", () -> QDetail.detail.detailName.eq("가격").and(QDetail.detail.detailContent.between("400000", "500000"))),
    RamEqual4GB("1002", () -> QDetail.detail.detailName.eq("RAM").and(QDetail.detail.detailContent.eq("4GB")));
    private String code;
    private Supplier<BooleanExpression> filter;

    public static ProductFilter findFilterByCode(String code){
        return Arrays.stream(ProductFilter.values()).filter((n) -> code == n.getCode()).findAny().orElse(null);
    }
}
