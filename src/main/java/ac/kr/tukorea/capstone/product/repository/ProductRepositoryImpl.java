package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ProductDto> findByCategoryAndFilter(Category category, String[][] filters, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductDetail productDetail = QProductDetail.productDetail;
        QDetail detail = QDetail.detail;
        QProductImage productImage = QProductImage.productImage;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for(String[] filter : filters){
            booleanBuilder.or(detail.detailName.eq(filter[0]).and(detail.detailContent.eq(filter[1])));
        }

        List<ProductDto> products = jpaQueryFactory
                .select(Projections.bean(ProductDto.class, product.id, product.productName, product.modelName, product.companyName, productImage.path))
                .distinct()
                .from(product)
                .innerJoin(productDetail)
                .on(productDetail.product.eq(product))
                .innerJoin(detail)
                .on(productDetail.detail.eq(detail).and(booleanBuilder))
                .innerJoin(productImage)
                .on(productImage.product.eq(product))
                .where(product.category.eq(category).and(booleanBuilder))
                .fetch();

        return products;
    }
}
