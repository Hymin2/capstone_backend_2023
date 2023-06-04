package ac.kr.tukorea.capstone.post.controller;

import ac.kr.tukorea.capstone.config.util.MessageForm;
import ac.kr.tukorea.capstone.post.dto.*;
import ac.kr.tukorea.capstone.post.service.PostService;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    MessageForm messageForm;

    @PostMapping
    public ResponseEntity<MessageForm> createSalePost(@ModelAttribute PostRegisterDto postRegisterDto,
                                                      @RequestParam(value = "multipartFiles") List<MultipartFile> multipartFiles){
        postService.registerPost(postRegisterDto, multipartFiles);
        messageForm = new MessageForm(201, "Post registration is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }


    @GetMapping()
    public ResponseEntity<MessageForm> getSalePostList(@RequestParam(required = false) Optional<Long> productId,
                                                       @RequestParam(required = false) String username,
                                                       @RequestParam(required = false) String postTitle,
                                                       @RequestParam(required = false) String postContent,
                                                       @RequestParam(required = false) String isOnSale){
        List<PostVo> postVos = postService.getSalePostList(productId, username, postTitle, postContent, isOnSale);
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


    @PostMapping("/like")
    public ResponseEntity<MessageForm> registerLikePost(@RequestBody LikePostRegisterDto likePostRegisterDto){
        postService.registerLikePost(likePostRegisterDto);
        System.out.println(likePostRegisterDto.getUsername());
        messageForm = new MessageForm(201, "Wanted post register is success", "success");

        return ResponseEntity.status(HttpStatus.CREATED).body(messageForm);
    }

    @GetMapping("/like")
    public ResponseEntity<MessageForm> getLikePostList(@RequestParam String username){
        List<PostVo> posts = postService.getLikePostList(username);
        messageForm = new MessageForm(200, posts, "success");

        return ResponseEntity.status(HttpStatus.OK).body(messageForm);
    }

    @DeleteMapping("/like")
    public ResponseEntity<MessageForm> deleteLikePost(@RequestParam long postId, @RequestParam String username){
        postService.deleteLikePost(postId, username);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/img")
    public ResponseEntity<Resource> image(@RequestParam String name){
        Resource resource = postService.getPostImage(name);

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
