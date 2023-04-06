package ac.kr.tukorea.capstone.product.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    @Transactional
    public ResponseEntity<MessageForm> productList(@RequestParam long category, @PageableDefault(page = 0, size = 10) Pageable pageable){
        Slice<Product> products = productService.getProductList(category, pageable);
        List<Product> productList = products.getContent();

        ProductListDto productListDto = new ProductListDto(productList, products.isLast());

        MessageForm messageForm = new MessageForm(200, productListDto, "success");

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(messageForm);
    }
}
