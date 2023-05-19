package ac.kr.tukorea.capstone.market.service;

import ac.kr.tukorea.capstone.config.Exception.*;
import ac.kr.tukorea.capstone.config.util.ImageComponent;
import ac.kr.tukorea.capstone.market.dto.*;
import ac.kr.tukorea.capstone.market.entity.*;
import ac.kr.tukorea.capstone.market.repository.*;
import ac.kr.tukorea.capstone.market.vo.PostVo;
import ac.kr.tukorea.capstone.product.entity.Product;
import ac.kr.tukorea.capstone.product.repository.ProductRepository;
import ac.kr.tukorea.capstone.user.entity.Authority;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.AuthorityRepository;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final PostRepository postRepository;
    private final PostRepositoryImpl postRepositoryImpl;
    private final PostImageRepository postImageRepository;
    private final WantPostRepository wantPostRepository;
    private final WantPostRepositoryCustom wantPostRepositoryCustom;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final FollowMarketRepository followMarketRepository;
    private final FollowMarketRepositoryCustom followMarketRepositoryCustom;
    private final ImageComponent imageComponent;

    @Transactional
    public void registerMarket(MarketRegisterDto marketRegisterDto){
        User user = userRepository.findByUsername(marketRegisterDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException());

        if(isExistMarketOfUer(user)) throw new ExistingMarketOfUserException();

        try {
            Market market = Market.builder()
                    .marketName(marketRegisterDto.getMarketName())
                    .user(user)
                    .build();

            marketRepository.save(market);

            Authority authority = Authority.builder()
                    .name("ROLE_SELLER")
                    .user(user)
                    .build();

            authorityRepository.save(authority);
        }catch (RuntimeException e){
            throw new DuplicateMarketNameException();
        }
    }

    public boolean isExistMarketOfUer(User user){
        return marketRepository.existsByUser(user);
    }

    @Transactional
    public void updateMarketName(MarketRegisterDto marketRegisterDto, String oldMarketName){
        Market market = marketRepository.findByMarketName(oldMarketName).orElseThrow(() -> new MarketnameNotFoundException());

        market.setMarketName(marketRegisterDto.getMarketName());
    }

    @Transactional
    public void uploadImage(MultipartFile multipartFile, String marketName) {
        String os = System.getProperty("os.name").toLowerCase();
        String imgPath = null;

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/market/profile/img/";
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/market/profile/img/";

        imgPath = imageComponent.uploadImage(multipartFile, imgPath);

        Market market = marketRepository.findByMarketName(marketName).orElseThrow(() -> new MarketnameNotFoundException());
        market.setMarketImagePath(imgPath);
        market.setMarketImageSize(multipartFile.getSize());
    }

    @Transactional
    public MarketDto getMarket(String marketName){
        Market market = marketRepository.findByMarketName(marketName).orElseThrow(() -> new MarketnameNotFoundException());
        List<Post> posts = postRepository.findByMarket(market);
        List<PostVo> postVos = new ArrayList<>();

        int onSales = 0, soldOut = 0;

        for(Post post : posts){
            List<PostImage> postImage = postImageRepository.findByPost(post);
            List<String> imagePaths = postImage.stream().map(PostImage::getImagePath).collect(Collectors.toList());

            postVos.add(new PostVo(post.getId(), post.getPostTitle(), post.getPostContent(), post.getIsOnSales(), post.getPostCreatedTime() ,imagePaths));

            if(post.getIsOnSales().equals("Y")) onSales++;
            else soldOut++;
        }

        return new MarketDto(market.getId(), marketName, market.getMarketImagePath(), soldOut, onSales, postVos);
    }

    @Transactional
    public void registerPost(PostRegisterDto postRegisterDto, List<MultipartFile> multipartFiles){
        Product product = productRepository.findById(postRegisterDto.getProductId()).get();
        Market market = marketRepository.findByMarketName(postRegisterDto.getMarketName()).orElseThrow(() -> new MarketnameNotFoundException());
        List<PostImage> postImages = new ArrayList<>();

        Post post = Post.builder()
                .postTitle(postRegisterDto.getPostTitle())
                .postContent(postRegisterDto.getPostContent())
                .market(market)
                .isOnSales("Y")
                .product(product)
                .postImages(postImages)
                .build();

        for(MultipartFile multipartFile : multipartFiles){
            String os = System.getProperty("os.name").toLowerCase();
            String imgPath = null;

            if(os.contains("win"))
                imgPath = "c:/capstone/resource/market/post/img/";
            else if(os.contains("linux"))
                imgPath = "/capstone/resource/market/post/img/";

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
        List<Post> posts = postRepositoryImpl.getSalePostList(product, postTitle, postContent, isOnSale);
        List<PostVo> postVos = new ArrayList<>();

        for(Post post : posts){
            List<PostImage> postImages = postImageRepository.findByPost(post);
            PostVo postVo = new PostVo(post.getId(),
                    post.getPostTitle(),
                    post.getPostContent(),
                    post.getIsOnSales(),
                    post.getPostCreatedTime(),
                    postImages.stream()
                            .map(PostImage::getImagePath)
                            .collect(Collectors.toList()));

            postVos.add(postVo);
        }

        return postVos;
    }

    @Transactional
    public void deletePost(Long postId){
        postRepository.deleteById(postId);
    }

    public void registerFollowMarket(FollowMarketRegisterDto followMarketRegisterDto){
        Market market = marketRepository.findById(followMarketRegisterDto.getMarketId()).orElseThrow(() -> new MarketnameNotFoundException());
        User user = userRepository.findById(followMarketRegisterDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException());

        FollowMarket followMarket = FollowMarket
                .builder()
                .market(market)
                .user(user)
                .build();

        followMarketRepository.save(followMarket);
    }

    public void getFollowMarketList(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());

        List<Market> markets = followMarketRepositoryCustom.getFollowMarketList(user);
    }

    public void deleteFollowMarket(Long followId){
        try {
            followMarketRepository.deleteById(followId);
        } catch (RuntimeException e){
            throw new FollowNotFoundException();
        }
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

    public void getWantPostList(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());

        List<Post> posts = wantPostRepositoryCustom.getWantPostList(user);
    }

    public void deleteWantPost(long wantId){
        try {
            wantPostRepository.deleteById(wantId);
        } catch (RuntimeException e){
            throw new WantNotFoundException();
        }
    }
}
