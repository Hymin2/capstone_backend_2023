package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.config.util.ProductFilterDetail;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.entity.UsedProductPrice;
import ac.kr.tukorea.capstone.product.repository.UsedProductPriceRepository;
import ac.kr.tukorea.capstone.product.vo.ProductVo;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.CategoryRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepositoryImpl;
import ac.kr.tukorea.capstone.config.util.ProductFilter;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ProductRepository productRepository;
    private final UsedProductPriceRepository usedProductPriceRepository;

    public ProductListDto getProductList(long categoryId, String filter, String name){
        Category category = categoryRepository.findById(categoryId).get();
        List<ProductVo> products = productRepositoryImpl.findByCategoryAndFilter(category, getFilterList(filter), name);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName() , products);

        return productListDto;
    }

    public ProductDetailsDto getProductDetails(long productId){
        Product product = productRepository.findById(productId).get();

        ProductDetailsDto productDetailsDto = productRepositoryImpl.findDetailsByProduct(product);

        return productDetailsDto;
    }

    public List<BooleanExpression> getFilterList(String filter){
        if(filter == null) return null;

        String[] filters = filter.split("(?<=\\G.{" + 4 + "})");

        List<BooleanExpression> productFilterDetails = Arrays.stream(filters)
                .map((str) -> ProductFilterDetail.getFilter(str))
                .collect(Collectors.toList());

        return productFilterDetails;
    }
}
