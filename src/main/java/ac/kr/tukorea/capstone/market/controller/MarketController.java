package ac.kr.tukorea.capstone.market.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/market")
public class MarketController {
    @GetMapping("/{username}")
    public RequestEntity<MessageForm> existMarket(@PathVariable String username) {

    }

    @PostMapping
    public RequestEntity<MessageForm> createMarket(){

    }

    @PutMapping
    public RequestEntity<MessageForm> updateMarketName(){

    }

    @PostMapping("/post")
    public RequestEntity<MessageForm> createSalePost(){

    }

    @GetMapping("/post")
    public RequestEntity<MessageForm> getSalePostList(){

    }

    @GetMapping("/post/{id}")
    public RequestEntity<MessageForm> getSalePostDetails(@PathVariable(name = "id") Long postId){

    }

    @PutMapping("/post/{id}")
    public RequestEntity<MessageForm> updatePostDetails(@PathVariable(name = "id") Long postId){

    }

    @DeleteMapping("/post/{id}")
    public RequestEntity<MessageForm> deletePost(@PathVariable(name = "id") Long postId){

    }

    @PostMapping("/want/market")
    public RequestEntity<MessageForm> createWantMarket(){

    }

    @DeleteMapping("/want/market")
    public RequestEntity<MessageForm> deleteWantMarket(){

    }

    @PostMapping("/want/post")
    public RequestEntity<MessageForm> createWantPost(){

    }

    @DeleteMapping("/want/post")
    public RequestEntity<MessageForm> deleteWantPost(){

    }
}
