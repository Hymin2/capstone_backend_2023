package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.entity.*;
import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.vo.RecommendProductVo;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

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
    public List<ProductVo> getFilterProductList(long categoryId, Map<String, List<BooleanExpression>> productFilters, String name) {
        List<ProductVo> products = null;

        if(productFilters.getOrDefault("price", null) == null) {
            products = jpaQueryFactory.selectFrom(product)
                    .innerJoin(productImage)
                    .on(productImage.product.eq(product))
                    .leftJoin(usedProductPrice)
                    .on(usedProductPrice.product.eq(product))
                    .where(product.category.id.eq(categoryId),
                            searchFilters(productFilters.getOrDefault("company", null)),
                            searchFilters(productFilters.getOrDefault("size", null)),
                            searchFilters(productFilters.getOrDefault("processor", null)),
                            searchFilters(productFilters.getOrDefault("ram", null)),
                            searchFilters(productFilters.getOrDefault("memory", null)),
                            containsName(name))
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
        } else{
            products = jpaQueryFactory.selectFrom(product)
                    .innerJoin(productImage)
                    .on(productImage.product.eq(product))
                    .leftJoin(usedProductPrice)
                    .on(usedProductPrice.product.eq(product))
                    .where(product.category.id.eq(categoryId),
                            searchFilters(productFilters.getOrDefault("company", null)),
                            searchFilters(productFilters.getOrDefault("size", null)),
                            searchFilters(productFilters.getOrDefault("processor", null)),
                            searchFilters(productFilters.getOrDefault("ram", null)),
                            searchFilters(productFilters.getOrDefault("memory", null)),
                            containsName(name))
                    .distinct()
                    .groupBy(product.id, productImage.path)
                    .having(havingPrice(productFilters.get("price")))
                    .transform(groupBy(product.id).list(Projections.constructor(ProductVo.class,
                            product.id,
                            product.productName,
                            product.modelName,
                            product.companyName,
                            usedProductPrice.price.avg().intValue().as("averagePrice"),
                            usedProductPrice.id.count().intValue().as("transactionNum"),
                            list(Projections.constructor(String.class, productImage.path)))));
        }

        return products;
    }

    @Override
    public List<ProductDetailVo> getProductDetailList(long productId) {
        List<ProductDetailVo> productDetails = jpaQueryFactory
                .select(Projections.bean(ProductDetailVo.class, detail.detailName, detail.detailContent))
                .from(detail)
                .innerJoin(productDetail)
                .on(productDetail.detail.eq(detail))
                .innerJoin(this.product)
                .on(productDetail.product.eq(this.product))
                .where(this.product.id.eq(productId))
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

    @Override
    public List<ProductVo> getProductListByProductId(List<Long> productIdList) {
        List<ProductVo> products = jpaQueryFactory.selectFrom(product)
                .innerJoin(productImage)
                .on(productImage.product.eq(product))
                .innerJoin(usedProductPrice)
                .on(usedProductPrice.product.eq(product))
                .where(product.id.in(productIdList))
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

    @Override
    public List<UsedProductPriceVo> getUsedProductPriceList(long productId){
        List<UsedProductPriceVo> productPrices = jpaQueryFactory
                .select(Projections.bean(UsedProductPriceVo.class, usedProductPrice.time.as("time"), usedProductPrice.price.avg().intValue().as("price")))
                .from(usedProductPrice)
                .where(usedProductPrice.product.id.eq(productId))
                .orderBy(usedProductPrice.time.asc())
                .groupBy(usedProductPrice.time)
                .fetch();

        return productPrices;
    }

    @Override
    public List<RecommendProductVo> getRecommendProductList(long productId) {
        List<RecommendProductVo> recommendProducts = jpaQueryFactory
                .select(Projections.constructor(RecommendProductVo.class,
                        productDetail.product.id,
                        productDetail.product.id.count().intValue(),
                        JPAExpressions.select(usedProductPrice.id.count().intValue()).from(usedProductPrice).where(usedProductPrice.product.id.eq(productId)),
                        JPAExpressions.select(usedProductPrice.price.avg().intValue()).from(usedProductPrice).where(usedProductPrice.product.id.eq(productId))))
                .from(productDetail)
                .where(productDetail.detail.id.in(JPAExpressions.select(detail.id).from(productDetail).where(productDetail.product.id.eq(productId))))
                .groupBy(productDetail.product.id)
                .fetch();

        return recommendProducts;
    }

    public BooleanBuilder havingPrice(List<BooleanExpression> productFilters){
        return eqFilter(productFilters);
    }

    public BooleanExpression searchFilters(List<BooleanExpression> productFilters){
        if(productFilters == null || productFilters.isEmpty()) return null;

        return product.id.in(jpaQueryFactory.select(product.id).from(product)
                .innerJoin(productDetail)
                .on(productDetail.product.eq(product))
                .innerJoin(detail)
                .on(productDetail.detail.eq(detail))
                .where(eqFilter(productFilters)));
    }
    public BooleanExpression containsName(String name){
        if(name == null || name.equals("")) return null;

        return product.productName.contains(name);
    }
    public BooleanBuilder eqFilter(List<BooleanExpression> productFilters){
        if(productFilters == null || productFilters.isEmpty()) return null;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        for(BooleanExpression filter : productFilters){
            booleanBuilder.or(filter);
        }

        return booleanBuilder;
    }
}
