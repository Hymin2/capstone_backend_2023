package ac.kr.tukorea.capstone.market.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.market.dto.*;
import ac.kr.tukorea.capstone.market.service.MarketService;
import ac.kr.tukorea.capstone.market.vo.PostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/market")
@RequiredArgsConstructor
public class MarketController {
    private final MarketService marketService;
    MessageForm messageForm;
    @PostMapping("/register")
    public ResponseEntity<MessageForm> registerMarket(@RequestBody MarketRegisterDto marketRegisterDto){
        marketService.registerMarket(marketRegisterDto);

        messageForm = new MessageForm(201, "Registration success", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }


    @GetMapping("/check")
    public ResponseEntity<MessageForm> checkExistMarket() {
        messageForm = new MessageForm(200, "Access success", "success");
        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }


    @PutMapping(value = "{marketName}")
    public ResponseEntity<MessageForm> updateMarketName(@PathVariable String marketName,
                                                        @RequestBody MarketRegisterDto marketRegisterDto){
        marketService.updateMarketName(marketRegisterDto, marketName);

        messageForm = new MessageForm(201, "Market name change success", "success");
        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping(value = "{marketName}")
    public ResponseEntity<MessageForm> getMarket(@PathVariable String marketName){
        MarketDto marketDto = marketService.getMarket(marketName);
        messageForm = new MessageForm(200, marketDto, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }


    @PostMapping(value = "{marketName}")
    public ResponseEntity<MessageForm> uploadMarketProfileImage(@PathVariable String marketName,
                                                                @RequestParam MultipartFile multipartFile){
        marketService.uploadImage(multipartFile, marketName);
        messageForm = new MessageForm(201, "Uploading market image is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }


    @PostMapping("/post")
    public ResponseEntity<MessageForm> createSalePost(@RequestBody PostRegisterDto postRegisterDto,
                                                      @RequestParam List<MultipartFile> multipartFiles){
        marketService.registerPost(postRegisterDto, multipartFiles);
        messageForm = new MessageForm(201, "Post registration is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }


    @GetMapping("/post")
    public ResponseEntity<MessageForm> getSalePostList(@RequestParam long productId,
                                                       @RequestParam String postTitle,
                                                       @RequestParam String postContent,
                                                       @RequestParam String isOnSale){
        List<PostVo> postVos = marketService.getSalePostList(productId, postTitle, postContent, isOnSale);
        messageForm = new MessageForm(200, postVos, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @PutMapping("/post/{id}")
    public ResponseEntity<MessageForm> updatePostDetails(@PathVariable(name = "id") Long postId,
                                                         @RequestParam List<MultipartFile> multipartFiles,
                                                         @RequestBody PostUpdateDto postUpdateDto) {

    }

    @DeleteMapping("/post/{id}")
    public ResponseEntity deletePost(@PathVariable(name = "id") Long postId){
        marketService.deletePost(postId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/follow")
    public ResponseEntity<MessageForm> registerFollowMarket(@RequestBody FollowMarketRegisterDto followMarketRegisterDto){
        marketService.registerFollowMarket(followMarketRegisterDto);
        messageForm = new MessageForm(201, "Following market is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping("/follow")
    public ResponseEntity<MessageForm> getFollowMarketList(@RequestParam String username){

    }

    @DeleteMapping("/follow/{followId}")
    public ResponseEntity deleteWantMarket(@PathVariable Long followId){
        marketService.deleteFollowMarket(followId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/post/want")
    public ResponseEntity<MessageForm> registerWantPost(@RequestBody WantPostRegisterDto wantPostRegisterDto){
        marketService.registerWantPost(wantPostRegisterDto);
        messageForm = new MessageForm(201, "Wanted post register is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping("/post/want")
    public ResponseEntity<MessageForm> getWantPostList(@RequestParam String username){

    }

    @DeleteMapping("/post/want/{wantId}")
    public ResponseEntity<MessageForm> deleteWantPost(@PathVariable long wantId){
        marketService.deleteWantPost(wantId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
