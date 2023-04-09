package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.product.dto.ProductDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product 리스트 및 상세 정보 출력 테스트")
@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductService productService;

    /*
    @DisplayName("상품 리스트 slice로 출력 테스트")
    @Test
    public void getProductList(){
        ProductListDto productListDto = productService.getProductList(1L, PageRequest.of(0, 10));

        List<ProductDto> list = productListDto.getProductList();
        for(ProductDto product : list) {
            System.out.println(product.getProductName() + " " + product.getModelName());
        }
    }

     */
}