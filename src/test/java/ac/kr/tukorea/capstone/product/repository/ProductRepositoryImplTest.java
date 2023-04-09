package ac.kr.tukorea.capstone.product.repository;

import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.entity.Category;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product 조건 검색이 되는지 테스트")
@SpringBootTest
class ProductRepositoryImplTest {
    @Autowired
    private ProductRepositoryImpl productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("상품 리스트 slice로 출력 테스트")
    @Test
    public void getProductList(){
        Category category = categoryRepository.findById(1L).get();
        String[][] filters = new String[2][2];

        filters[0][0] = "RAM";
        filters[0][1] = "12GB";
        filters[1][0] = "AP";
        filters[1][1] = "스냅드래곤8 Gen2";

        List<ProductDto> productList = productRepository.findByCategoryAndFilter(category, filters, PageRequest.of(10, 10));

        for (ProductDto product : productList){
            System.out.println(product.getProductName());
        }
    }

}