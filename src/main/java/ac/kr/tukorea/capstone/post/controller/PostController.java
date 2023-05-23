package ac.kr.tukorea.capstone.post.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.post.dto.*;
import ac.kr.tukorea.capstone.post.service.PostService;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    MessageForm messageForm;

    @PostMapping()
    public ResponseEntity<MessageForm> createSalePost(@RequestBody PostRegisterDto postRegisterDto,
                                                      @RequestParam List<MultipartFile> multipartFiles){
        postService.registerPost(postRegisterDto, multipartFiles);
        messageForm = new MessageForm(201, "Post registration is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }


    @GetMapping()
    public ResponseEntity<MessageForm> getSalePostList(@RequestParam long productId,
                                                       @RequestParam String postTitle,
                                                       @RequestParam String postContent,
                                                       @RequestParam String isOnSale){
        List<PostVo> postVos = postService.getSalePostList(productId, postTitle, postContent, isOnSale);
        messageForm = new MessageForm(200, postVos, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageForm> updatePostDetails(@PathVariable(name = "id") Long postId,
                                                         @RequestParam List<MultipartFile> multipartFiles,
                                                         @RequestBody PostUpdateDto postUpdateDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePost(@PathVariable(name = "id") Long postId){
        postService.deletePost(postId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("/post/want")
    public ResponseEntity<MessageForm> registerWantPost(@RequestBody WantPostRegisterDto wantPostRegisterDto){
        postService.registerWantPost(wantPostRegisterDto);
        messageForm = new MessageForm(201, "Wanted post register is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping("/post/want")
    public ResponseEntity<MessageForm> getWantPostList(@RequestParam String username){
        List<PostVo> posts = postService.getWantPostList(username);
        messageForm = new MessageForm(200, posts, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @DeleteMapping("/post/want/{wantId}")
    public ResponseEntity<MessageForm> deleteWantPost(@PathVariable long wantId){
        postService.deleteWantPost(wantId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
