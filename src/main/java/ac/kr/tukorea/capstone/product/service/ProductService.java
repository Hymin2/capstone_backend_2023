package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.CategoryRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepositoryImpl;
import ac.kr.tukorea.capstone.config.util.ProductFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryRepository categoryRepository;
    private final ProductRepositoryImpl productRepositoryImpl;
    private final ProductRepository productRepository;

    public ProductListDto getProductList(long categoryId, String filter, String name){
        Category category = categoryRepository.findById(categoryId).get();
        List<ProductDto> products = productRepositoryImpl.findByCategoryAndFilter(category, getFilterList(filter), name);

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName() , products);

        return productListDto;
    }

    public ProductDetailsDto getProductDetails(long productId){
        Product product = productRepository.findById(productId).get();


        ProductDetailsDto productDetailsDto = productRepositoryImpl.findDetailsByProduct(product);

        return productDetailsDto;
    }

    public List<ProductFilter> getFilterList(String filter){
        if(filter == null) return null;

        String[] filters = filter.split("[a-zA-Z0-9]{4}");

        List<ProductFilter> productFilters = Arrays.stream(filters)
                .map((str) -> ProductFilter.findFilterByCode(str))
                .collect(Collectors.toList());

        return productFilters;
    }
}
