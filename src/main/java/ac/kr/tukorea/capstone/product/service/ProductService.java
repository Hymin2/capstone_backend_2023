package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.CategoryRepository;
import ac.kr.tukorea.capstone.product.repository.DetailRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final DetailRepository detailRepository;
    private final CategoryRepository categoryRepository;


    public ProductListDto getProductList(long categoryId, String filter, Pageable pageable){


        Category category = categoryRepository.findById(categoryId).get();
        Slice<Product> products = productRepository.findByCategory(category, pageable);

        List<ProductDto> productDtoList = products
                                            .getContent()
                                            .stream()
                                            .map(m -> new ProductDto(m.getId(), m.getProductName(), m.getModelName(), m.getCompanyName(), m.getProductImages().get(0).getPath()))
                                            .collect(Collectors.toList());

        ProductListDto productListDto = new ProductListDto(categoryId, category.getCategoryName() , productDtoList, products.isLast());

        return productListDto;
    }

    public String[] getFilterList(String filter){
        String[] filters = filter.split("|");

        return filters;
    }
}
