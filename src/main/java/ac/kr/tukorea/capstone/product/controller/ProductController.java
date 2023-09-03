package ac.kr.tukorea.capstone.product.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.dto.UsedProductPriceDto;
import ac.kr.tukorea.capstone.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(value = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public ResponseEntity<MessageForm> getProductList(@RequestParam long category,
                                                      @RequestParam(required = false) String filter,
                                                      @RequestParam(required = false) String name){

        ProductListDto productListDto = productService.getProductList(category, filter, name);

        MessageForm messageForm = new MessageForm(200, productListDto, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping("/{product}")
    public ResponseEntity<MessageForm> getProductDetails(@PathVariable long product){
        ProductDetailsDto productDetailsDto = productService.getProductDetails(product);
        MessageForm messageForm = new MessageForm();

        if(productDetailsDto == null){
            messageForm.setMessageForm(404, "data is not found.", "success");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageForm);
        }
         messageForm.setMessageForm(200, productDetailsDto, "success");
        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping("/top")
    public ResponseEntity<MessageForm> getTopProduct(@RequestParam long category){
        ProductListDto products = productService.getTopProductList(category);
        MessageForm messageForm = new MessageForm(200, products, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping("/used-price")
    public ResponseEntity<MessageForm> getUsedPrice(@RequestParam long product, @RequestParam int month){
        UsedProductPriceDto usedProductPriceDto = productService.getUsedPriceList(product, month);
        MessageForm messageForm = new MessageForm(200, usedProductPriceDto, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping("/img")
    public ResponseEntity<Resource> image(@RequestParam String name){
        Resource resource = productService.getProductImage(name);

        HttpHeaders headers = new HttpHeaders();
        Path path = null;

        try{
            path = Paths.get(resource.getURI());
            headers.add("Content-Type", Files.probeContentType(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity(resource, headers, HttpStatus.OK);
    }
}
