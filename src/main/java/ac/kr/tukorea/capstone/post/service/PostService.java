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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostRepositoryImpl postRepositoryImpl;
    private final PostImageRepository postImageRepository;
    private final WantPostRepository wantPostRepository;
    private final WantPostRepositoryCustom wantPostRepositoryCustom;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ImageComponent imageComponent;

    @Transactional
    public void registerPost(PostRegisterDto postRegisterDto, List<MultipartFile> multipartFiles){
        Product product = productRepository.findById(postRegisterDto.getProductId()).get();
        User user = userRepository.findByUsername(postRegisterDto.getUserName()).orElseThrow(() -> new UsernameNotFoundException());
        List<PostImage> postImages = new ArrayList<>();

        Post post = Post.builder()
                .postTitle(postRegisterDto.getPostTitle())
                .postContent(postRegisterDto.getPostContent())
                .user(user)
                .isOnSales("Y")
                .product(product)
                .postImages(postImages)
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
    public List<PostVo> getSalePostList(long productId, String postTitle, String postContent, String isOnSale){
        Product product = productRepository.findById(productId).get();
        List<PostVo> posts = postRepositoryImpl.getSearchedPostList(product, postTitle, postContent, isOnSale);

        for(PostVo post : posts){
            List<PostImage> postImages = postImageRepository.findByPost_Id(post.getId());
            post.setPostImages(postImages.stream().map(PostImage::getImagePath).collect(Collectors.toList()));
        }

        return posts;
    }

    @Transactional
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }


    public void registerWantPost(WantPostRegisterDto wantPostRegisterDto){
        Post post = postRepository.findById(wantPostRegisterDto.getPostId()).orElseThrow(() -> new PostNotFoundException());
        User user = userRepository.findByUsername(wantPostRegisterDto.getUserName()).orElseThrow(() -> new UsernameNotFoundException());

        WantPost wantPost = WantPost
                .builder()
                .post(post)
                .user(user)
                .build();

        wantPostRepository.save(wantPost);
    }

    public List<PostVo> getWantPostList(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
        List<PostVo> posts = wantPostRepositoryCustom.getWantPostList(user);

        for(PostVo post : posts){
            List<PostImage> postImages = postImageRepository.findByPost_Id(post.getId());
            post.setPostImages(postImages.stream().map(PostImage::getImagePath).collect(Collectors.toList()));
        }

        return posts;
    }

    public void deleteWantPost(long wantId){
        try {
            wantPostRepository.deleteById(wantId);
        } catch (RuntimeException e){
            throw new WantNotFoundException();
        }
    }
}
