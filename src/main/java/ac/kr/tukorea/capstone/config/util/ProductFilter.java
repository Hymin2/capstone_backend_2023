package ac.kr.tukorea.capstone.config.util;

import ac.kr.tukorea.capstone.product.entity.QDetail;
import ac.kr.tukorea.capstone.product.entity.QProduct;
import ac.kr.tukorea.capstone.product.entity.QUsedProductPrice;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
@AllArgsConstructor
public enum ProductFilter {
    COMPANY_EQ((s1, s2) -> QProduct.product.companyName.eq(s1)),
    COMPANY_ETC((s1, s2) -> QProduct.product.companyName.ne("삼성전자").and(QProduct.product.companyName.ne("APPLE"))
            .and(QProduct.product.companyName.ne("Microsoft")).and(QProduct.product.companyName.ne("레노버"))),
    COMPANY_LAPTOP_ETC((s1, s2) -> QProduct.product.companyName.ne("삼성전자").and(QProduct.product.companyName.ne("APPLE"))
            .and(QProduct.product.companyName.ne("LG전자")).and(QProduct.product.companyName.ne("레노버")).and(QProduct.product.companyName.ne("MSI"))
            .and(QProduct.product.companyName.ne("ASUS"))),
    RAM_EQ((s1, s2) -> QDetail.detail.detailName.eq("RAM").and(QDetail.detail.detailContent.eq(s1))),
    RAM_LIKE((s1, s2) -> QDetail.detail.detailName.eq("RAM").and(QDetail.detail.detailContent.like(s1))),
    MEMORY_EQ((s1, s2) -> QDetail.detail.detailName.eq("내장메모리").and(QDetail.detail.detailContent.eq(s1))),
    SIZE_BETWEEN((s1, s2) -> QDetail.detail.detailName.eq("크기").and(QDetail.detail.detailContent.between(s1, s2))),
    PROCESSOR_LIKE((s1, s2) -> QDetail.detail.detailName.eq("프로세서").and(QDetail.detail.detailContent.like(s1))),
    PROCESSOR_NONE_EQ((s1, s2) -> QDetail.detail.detailName.eq("프로세서").and(QDetail.detail.detailContent.notLike(s1))),
    GRAPHIC_LIKE((s1, s2) -> QDetail.detail.detailName.eq("그래픽").and(QDetail.detail.detailContent.like(s1))),
    PRICE_BETWEEN((s1, s2) -> QUsedProductPrice.usedProductPrice.price.avg().between(Double.parseDouble(s1), Double.parseDouble(s2)));
    private BiFunction<String, String, BooleanExpression> filter;

    public BooleanExpression getQuery(String s1, String s2){
        return filter.apply(s1, s2);
    }
}
