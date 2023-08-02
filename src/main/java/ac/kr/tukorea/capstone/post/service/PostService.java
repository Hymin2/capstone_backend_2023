package ac.kr.tukorea.capstone.post.service;

import ac.kr.tukorea.capstone.config.Exception.PostNotFoundException;
import ac.kr.tukorea.capstone.config.Exception.UsernameNotFoundException;
import ac.kr.tukorea.capstone.config.Exception.WantNotFoundException;
import ac.kr.tukorea.capstone.config.util.ImageComponent;
import ac.kr.tukorea.capstone.post.dto.LikePostRegisterDto;
import ac.kr.tukorea.capstone.post.dto.PostRegisterDto;
import ac.kr.tukorea.capstone.post.entity.LikePost;
import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.entity.PostImage;
import ac.kr.tukorea.capstone.post.repository.*;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.product.service.ProductService;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import ac.kr.tukorea.capstone.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostRepositoryImpl postRepositoryImpl;
    private final LikePostRepository likePostRepository;
    private final LikePostRepositoryCustom likePostRepositoryCustom;
    private final ImageComponent imageComponent;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public void registerPost(PostRegisterDto postRegisterDto, List<MultipartFile> multipartFiles){
        Product product = productService.getProduct(postRegisterDto.getProductId());
        User user = userService.getUserByUsername(postRegisterDto.getUsername());
        List<PostImage> postImages = new ArrayList<>();

        Post post = Post.builder()
                .postTitle(postRegisterDto.getPostTitle())
                .postContent(postRegisterDto.getPostContent())
                .user(user)
                .isOnSales("Y")
                .product(product)
                .postImages(postImages)
                .price(postRegisterDto.getPrice())
                .build();

        for(MultipartFile multipartFile : multipartFiles){
            String os = System.getProperty("os.name").toLowerCase();
            String imgPath = null;

            if(os.contains("win"))
                imgPath = "c:/capstone/resource/user/post/img/";
            else if(os.contains("linux"))
                imgPath = "/capstone/resource/user/post/img/";

            imgPath = imageComponent.uploadImage(multipartFile, imgPath);

            PostImage postImage = PostImage
                    .builder()
                    .post(post)
                    .imagePath(imgPath)
                    .imageSize(multipartFile.getSize())
                    .build();

            postImages.add(postImage);
        }
        postRepository.save(post);
    }


    @Transactional
    public List<PostVo> getSalePostList(long productId, String username, String postTitle, String postContent, String isOnSale){
        Product product = productService.getProduct(productId);

        List<PostVo> posts = postRepositoryImpl.getSearchedPostList(product, username, postTitle, postContent, isOnSale);

        return posts;
    }

    @Transactional
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }


    public void registerLikePost(LikePostRegisterDto likePostRegisterDto){
        Post post = postRepository.findById(likePostRegisterDto.getPostId()).orElseThrow(() -> new PostNotFoundException());
        User user = userService.getUserByUsername(likePostRegisterDto.getUsername());

        LikePost likePost = LikePost
                .builder()
                .post(post)
                .user(user)
                .build();

        likePostRepository.save(likePost);
    }

    public List<PostVo> getLikePostList(String username){
        List<PostVo> posts = likePostRepositoryCustom.getLikePostList(username);

        return posts;
    }

    @Transactional
    public void deleteLikePost(long postId, String username){
        try {
            User user = userService.getUserByUsername(username);
            likePostRepositoryCustom.deleteLikePost(postId, user);
        } catch (RuntimeException e){
            System.out.println(e.getCause());
            throw new WantNotFoundException();
        }
    }

    @Transactional
    public Resource getPostImage(String name) {
        String imgPath = "";
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/user/post/img/" + name;
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/user/post/img/" + name;

        return imageComponent.getImage(imgPath);
    }
}
