package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.config.Exception.CategoryNotFoundException;
import ac.kr.tukorea.capstone.config.util.ImageComponent;
import ac.kr.tukorea.capstone.config.util.ProductFilterDetail;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.CategoryRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepositoryCustom;
import ac.kr.tukorea.capstone.product.vo.ProductDetailVo;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.vo.UsedProductPriceVo;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepositoryCustom productRepositoryCustom;
    private final ProductRepository productRepository;
    private final ImageComponent imageComponent;

    @Transactional
    public ProductListDto getTopProductList(long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        List<ProductVo> products = productRepositoryCustom.getTopProductList(categoryId);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName(), products);
        return productListDto;
    }

    @Transactional
    public ProductListDto getProductList(long categoryId, String filter, String name){
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        List<ProductVo> products = productRepositoryCustom.getProductList(categoryId, getFilterList(filter), name);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName() , products);

        return productListDto;
    }

    @Transactional
    public ProductDetailsDto getProductDetails(long productId){
        Product product = productRepository.findById(productId).get();
        List<UsedProductPriceVo> usedProductPrices = productRepositoryCustom.getUsedProductPriceList(product);

        List<ProductDetailVo> ProductDetails = productRepositoryCustom.getProductDetailList(product);

        ProductDetailsDto productDetailsDto = new ProductDetailsDto(productId, ProductDetails, usedProductPrices);

        return productDetailsDto;
    }

    public List<BooleanExpression> getFilterList(String filter){
        if(filter == null || filter.equals("")) return null;

        String[] filters = filter.split("(?<=\\G.{" + 4 + "})");

        List<BooleanExpression> productFilterDetails = Arrays.stream(filters)
                .map((str) -> ProductFilterDetail.getFilter(str))
                .collect(Collectors.toList());

        return productFilterDetails;
    }

    public Resource getProductImage(String name){
        String imgPath = "";
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/product/img/" + name;
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/product/img/" + name;

        return imageComponent.getImage(imgPath);
    }
}
