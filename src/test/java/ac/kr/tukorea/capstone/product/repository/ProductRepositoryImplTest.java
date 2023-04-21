package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.config.util.ProductFilter;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product 조건 검색이 되는지 테스트")
@SpringBootTest
class ProductRepositoryImplTest {
    @Autowired
    private ProductRepositoryImpl productRepository;
    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("상품 리스트 slice로 출력 테스트")
    @Test
    public void getProductList(){
        Category category = categoryRepository.findById(1L).get();


        List<ProductDto> productList = productRepository.findByCategoryAndFilter(category, Arrays.asList(ProductFilter.RamEqual4GB), null);

        for (ProductDto product : productList){
            System.out.println(product.getProductName() + " " + product.getProductName());
        }
    }

    @DisplayName("상품 디테일 정보 출력 테스트")
    @Test
    public void getProductDetails(){
        Product product = repository.findById(1L).get();

        ProductDetailsDto productDetailsDto = productRepository.findDetailsByProduct(product);

        productDetailsDto.getProductDetails().stream().forEach((p) -> System.out.println(p.getDetailName() + " " + p.getDetailContent()));
    }
}