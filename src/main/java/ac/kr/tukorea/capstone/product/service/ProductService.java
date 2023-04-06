package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.DetailRepository;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final DetailRepository detailRepository;


    public Slice<Product> getProductList(long categoryId, Pageable pageable){
        Category category = Category.builder()
                .id(categoryId).build();

        Slice<Product> products = productRepository.findByCategory(category, pageable);

        return products;
    }
}
