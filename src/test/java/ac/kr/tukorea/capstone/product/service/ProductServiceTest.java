package ac.kr.tukorea.capstone.product.service;

import ac.kr.tukorea.capstone.config.util.ProductFilterDetail;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("Product 리스트 및 상세 정보 출력 테스트")
@SpringBootTest
class ProductServiceTest {
    @Autowired
    ProductService productService;

    /*
    @DisplayName("상품 리스트 출력 테스트")
    @Test
    public void getProductList(){
        ProductListDto productListDto = productService.getProductList(1L, PageRequest.of(0, 10));

        List<ProductVo> list = productListDto.getProductList();
        for(ProductVo product : list) {
            System.out.println(product.getProductName() + " " + product.getModelName());
        }
    }

     */

    @DisplayName("Filter 코드 나누기 테스트")
    @Test
    public void splitFilterCode(){
        String filter = "1001100210031004";
        String[] filters = filter.split("(?<=\\G.{" + 4 + "})");
        Arrays.stream(filters).forEach(System.out::println);

        Object[] priceFilters = Arrays.stream(ProductFilterDetail.values()).filter((i) -> i.toString().startsWith("PRICE")).map(ProductFilterDetail::getCode).toArray();

        System.out.println(ProductFilterDetail.getValue("1004"));

    }

    @DisplayName("Filter 코드로 BooleanExpression 받기")
    @Test
    public void getFilter(){
        String filter = "1001100210031004";
        String[] filters = filter.split("(?<=\\G.{" + 4 + "})");

        List<ProductFilterDetail> productFilterDetails = Arrays.stream(ProductFilterDetail.values()).collect(Collectors.toList());
        System.out.println(filters[0]);
        System.out.println(filters[0].equals("1001"));
        System.out.println(productFilterDetails.stream().filter( (i) -> i.getCode().equals(filters[0])).findFirst().get().getCode());
    }

    @DisplayName("Date List 생성 테스트")
    @Test
    public void getDateList(){
        productService.getUsedPriceList(68L, 3);

    }
}