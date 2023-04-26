package ac.kr.tukorea.capstone.product.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.product.dto.ProductDetailsDto;
import ac.kr.tukorea.capstone.product.dto.ProductListDto;
import ac.kr.tukorea.capstone.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    public ResponseEntity<MessageForm> productList(@RequestParam long category,
                                                   @RequestParam(required = false) String filter,
                                                   @RequestParam(required = false) String name){

        ProductListDto productListDto = productService.getProductList(category, filter, name);

        MessageForm messageForm = new MessageForm(200, productListDto, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @GetMapping("/{product}")
    public ResponseEntity<MessageForm> productDetails(@PathVariable long product){
        ProductDetailsDto productDetailsDto = productService.getProductDetails(product);
        MessageForm messageForm = new MessageForm();

        if(productDetailsDto == null){
            messageForm.setMessageForm(404, "data is not found.", "success");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageForm);
        }
         messageForm.setMessageForm(200, productDetailsDto, "success");
        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }
    @GetMapping("/img")
    public ResponseEntity<Resource> image(@RequestParam String name){
        String os = System.getProperty("os.name").toLowerCase();
        String imgPath = "";

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/product/img/" + name;
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/product/img/" + name;

        Resource resource = new FileSystemResource(imgPath);

        if(!resource.exists()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        HttpHeaders headers = new HttpHeaders();
        Path path = null;

        try{
            path = Paths.get(imgPath);
            headers.add("Content-Type", Files.probeContentType(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity(resource, headers, HttpStatus.OK);
    }
}
