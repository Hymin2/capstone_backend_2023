package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.config.util.ProductFilter;
import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct product = QProduct.product;
    private final QProductDetail productDetail = QProductDetail.productDetail;
    private final QDetail detail = QDetail.detail;
    private final QProductImage productImage = QProductImage.productImage;


    @Override
    public List<ProductVo> findByCategoryAndFilter(Category category, List<BooleanExpression> productFilters, String name) {
        List<ProductVo> products = jpaQueryFactory
                .select(Projections.bean(ProductVo.class, product.id, product.productName, product.modelName, product.companyName, productImage.path))
                .distinct()
                .from(product)
                .innerJoin(productDetail)
                .on(productDetail.product.eq(product))
                .innerJoin(detail)
                .on(productDetail.detail.eq(detail))
                .innerJoin(productImage)
                .on(productImage.product.eq(product))
                .where(product.category.eq(category), eqFilter(productFilters), containsName(name))
                .fetch();

        return products;
    }

    @Override
    public ProductDetailsDto findDetailsByProduct(Product product) {
        List<ProductDetailVo> productDetails = jpaQueryFactory
                .select(Projections.bean(ProductDetailVo.class, detail.detailName, detail.detailContent))
                .from(detail)
                .innerJoin(productDetail)
                .on(productDetail.detail.eq(detail))
                .innerJoin(this.product)
                .on(productDetail.product.eq(this.product))
                .where(this.product.eq(product))
                .fetch();

        return new ProductDetailsDto(product.getId(), productDetails);
    }

    public BooleanExpression containsName(String name){
        if(name == null) return null;

        return product.productName.contains(name);
    }
    public BooleanBuilder eqFilter(List<BooleanExpression> productFilters){
        if(productFilters == null) return null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for(BooleanExpression filter : productFilters){
            booleanBuilder.or(filter);
        }

        return booleanBuilder;
    }
}
