package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.config.util.ProductFilter;
import ac.kr.tukorea.capstone.config.util.ProductFilterDetail;
import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.entity.*;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final QProduct product = QProduct.product;
    private final QProductDetail productDetail = QProductDetail.productDetail;
    private final QDetail detail = QDetail.detail;
    private final QProductImage productImage = QProductImage.productImage;
    private final QUsedProductPrice usedProductPrice = QUsedProductPrice.usedProductPrice;

    @Override
    public List<ProductVo> getProductList(long categoryId, List<BooleanExpression> productFilters, String name) {
        List<ProductVo> products = jpaQueryFactory.selectFrom(product)
                .innerJoin(productImage)
                .on(productImage.product.eq(product))
                .innerJoin(productDetail)
                .on(productDetail.product.eq(product))
                .innerJoin(detail)
                .on(productDetail.detail.eq(detail))
                .leftJoin(usedProductPrice)
                .on(usedProductPrice.product.eq(product))
                .where(product.category.id.eq(categoryId), eqFilter(productFilters), containsName(name))
                .distinct()
                .groupBy(product.id, productImage.path)
                .transform(groupBy(product.id).list(Projections.constructor(ProductVo.class,
                        product.id,
                        product.productName,
                        product.modelName,
                        product.companyName,
                        usedProductPrice.price.avg().intValue().as("averagePrice"),
                        usedProductPrice.id.count().intValue().as("transactionNum"),
                        list(Projections.constructor(String.class, productImage.path)))));

        return products;
    }

    @Override
    public List<ProductDetailVo> getProductDetailList(Product product) {
        List<ProductDetailVo> productDetails = jpaQueryFactory
                .select(Projections.bean(ProductDetailVo.class, detail.detailName, detail.detailContent))
                .from(detail)
                .innerJoin(productDetail)
                .on(productDetail.detail.eq(detail))
                .innerJoin(this.product)
                .on(productDetail.product.eq(this.product))
                .where(this.product.eq(product))
                .fetch();

        return productDetails;
    }

    @Override
    public List<ProductVo> getTopProductList(long categoryId) {
        List<ProductVo> products = jpaQueryFactory.selectFrom(product)
                .innerJoin(productImage)
                .on(productImage.product.eq(product))
                .innerJoin(usedProductPrice)
                .on(usedProductPrice.product.eq(product))
                .where(product.category.id.eq(categoryId))
                .distinct()
                .groupBy(product.id, productImage.path)
                .orderBy(usedProductPrice.id.count().desc())
                .limit(10)
                .transform(groupBy(product.id).list(Projections.constructor(ProductVo.class,
                        product.id,
                        product.productName,
                        product.modelName,
                        product.companyName,
                        usedProductPrice.price.avg().intValue().as("averagePrice"),
                        usedProductPrice.id.count().intValue().as("transactionNum"),
                        list(Projections.constructor(String.class, productImage.path)))));

        return products;
    }

    public List<UsedProductPriceVo> getUsedProductPriceList(Product product){
        List<UsedProductPriceVo> productPrices = jpaQueryFactory
                .select(Projections.bean(UsedProductPriceVo.class, usedProductPrice.time.as("time"), usedProductPrice.price.avg().intValue().as("price")))
                .from(usedProductPrice)
                .where(usedProductPrice.product.eq(product))
                .groupBy(usedProductPrice.time)
                .fetch();

        return productPrices;
    }

    public BooleanExpression containsName(String name){
        if(name == null || name.equals("")) return null;

        return product.productName.contains(name);
    }
    public BooleanBuilder eqFilter(List<BooleanExpression> productFilters){
        if(productFilters == null) return null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for(BooleanExpression filter : productFilters){
            booleanBuilder.and(filter);
        }

        return booleanBuilder;
    }
}
