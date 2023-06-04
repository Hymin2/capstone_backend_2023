package ac.kr.tukorea.capstone.post.service;

import ac.kr.tukorea.capstone.config.Exception.*;
import ac.kr.tukorea.capstone.config.util.ImageComponent;
import ac.kr.tukorea.capstone.post.dto.*;
import ac.kr.tukorea.capstone.post.entity.*;
import ac.kr.tukorea.capstone.post.repository.*;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostRepositoryImpl postRepositoryImpl;
    private final PostImageRepository postImageRepository;
    private final LikePostRepository likePostRepository;
    private final LikePostRepositoryCustom likePostRepositoryCustom;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImageComponent imageComponent;

    @Transactional
    public void registerPost(PostRegisterDto postRegisterDto, List<MultipartFile> multipartFiles){
        Product product = productRepository.findById(postRegisterDto.getProductId()).get();
        User user = userRepository.findByUsername(postRegisterDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException());
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
    public List<PostVo> getSalePostList(Optional<Long> productId, String username, String postTitle, String postContent, String isOnSale){
        Product product = null;
        if(!productId.isEmpty()) product = productRepository.findById(productId.get()).get();

        List<PostVo> posts = postRepositoryImpl.getSearchedPostList(product, username, postTitle, postContent, isOnSale);

        return posts;
    }

    @Transactional
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }


    public void registerLikePost(LikePostRegisterDto likePostRegisterDto){
        Post post = postRepository.findById(likePostRegisterDto.getPostId()).orElseThrow(() -> new PostNotFoundException());
        User user = userRepository.findByUsername(likePostRegisterDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException());

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
            User user = userRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
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
