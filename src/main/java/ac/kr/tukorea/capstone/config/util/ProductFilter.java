package ac.kr.tukorea.capstone.config.util;

import ac.kr.tukorea.capstone.product.entity.QDetail;
import ac.kr.tukorea.capstone.product.entity.QProduct;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
@AllArgsConstructor
public enum ProductFilter {
    COMPANY_EQ((s1, s2) -> QProduct.product.companyName.eq(s1)),
    COMPANY_ETC((s1, s2) -> QProduct.product.companyName.ne("삼성전자").and(QProduct.product.companyName.ne("APPLE"))),
    RAM_EQ((s1, s2) -> QDetail.detail.detailName.eq("RAM").and(QDetail.detail.detailContent.eq(s1))),
    MEMORY_EQ((s1, s2) -> QDetail.detail.detailName.eq("내장메모리").and(QDetail.detail.detailContent.eq(s1))),
    SIZE_BETWEEN((s1, s2) -> QDetail.detail.detailName.eq("크기").and(QDetail.detail.detailContent.between(s1, s2))),
    PROCESSOR_EQ((s1, s2) -> QDetail.detail.detailName.eq("프로세서").and(QDetail.detail.detailContent.like(s1))),
    PRICE_BETWEEN((s1, s2) -> QDetail.detail.detailName.eq("가격").and(QDetail.detail.detailContent.between(s1, s2)));

    private BiFunction<String, String, BooleanExpression> filter;

    public BooleanExpression getQuery(String s1, String s2){
        return filter.apply(s1, s2);
    }
}
