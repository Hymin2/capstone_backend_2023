package ac.kr.tukorea.capstone.market.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.market.dto.MarketRegisterDto;
import ac.kr.tukorea.capstone.market.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;

    @PostMapping("/register")
    public ResponseEntity<MessageForm> registerMarket(@RequestBody MarketRegisterDto marketRegisterDto){
        MessageForm messageForm;
        marketService.registerMarket(marketRegisterDto);

        messageForm = new MessageForm(201, "Registration success", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);

    }

    /*
    @GetMapping("/{username}")
    public ResponseEntity<MessageForm> existMarket(@PathVariable String username) {

    }

    @PutMapping
    public ResponseEntity<MessageForm> updateMarketName(){

    }

    @PostMapping("/post")
    public ResponseEntity<MessageForm> createSalePost(){

    }

    @GetMapping("/post")
    public ResponseEntity<MessageForm> getSalePostList(){

    }

    @GetMapping("/post/{id}")
    public ResponseEntity<MessageForm> getSalePostDetails(@PathVariable(name = "id") Long postId){

    }

    @PutMapping("/post/{id}")
    public ResponseEntity<MessageForm> updatePostDetails(@PathVariable(name = "id") Long postId){

    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity<MessageForm> deletePost(@PathVariable(name = "id") Long postId){

    }

    @PostMapping("/want/market")
    public ResponseEntity<MessageForm> createWantMarket(){

    }

    @DeleteMapping("/want/market")
    public ResponseEntity<MessageForm> deleteWantMarket(){

    }

    @PostMapping("/want/post")
    public ResponseEntity<MessageForm> createWantPost(){

    }

    @DeleteMapping("/want/post")
    public ResponseEntity<MessageForm> deleteWantPost(){

    }

     */
}
