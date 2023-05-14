package ac.kr.tukorea.capstone.market.service;

import ac.kr.tukorea.capstone.config.Exception.*;
import ac.kr.tukorea.capstone.market.dto.MarketDto;
import ac.kr.tukorea.capstone.market.dto.MarketRegisterDto;
import ac.kr.tukorea.capstone.market.dto.PostRegisterDto;
import ac.kr.tukorea.capstone.market.entity.Market;
import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.market.entity.PostImage;
import ac.kr.tukorea.capstone.market.repository.MarketRepository;
import ac.kr.tukorea.capstone.market.repository.PostImageRepository;
import ac.kr.tukorea.capstone.market.repository.PostRepository;
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

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

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
        if(!multipartFile.isEmpty())
            throw new FileIsEmptyException();

        if(!multipartFile.getContentType().startsWith("image"))
            throw new InvalidFileFormatException();

        String os = System.getProperty("os.name").toLowerCase();
        String imgPath = null;

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/market/profile/img/";
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/market/profile/img/";

        UUID uuid = UUID.randomUUID();

        File file = new File(imgPath + uuid + "_" + multipartFile.getOriginalFilename());

        try {
            multipartFile.transferTo(file);
        } catch (IOException e){
            throw new RuntimeException();
        }

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
            PostImage postImage = postImageRepository.findFirstByPost(post).get();

            postVos.add(new PostVo(post.getId(), post.getPostTitle(), post.getPostContent(), post.getPostCreatedTime() ,postImage.getImagePath()));

            if(post.getIsOnSales().equals("Y")) onSales++;
            else soldOut++;
        }

        return new MarketDto(market.getId(), marketName, market.getMarketImagePath(), soldOut, onSales, postVos);
    }

    @Transactional
    public void registerPost(PostRegisterDto postRegisterDto, List<MultipartFile> multipartFiles){
        Product product = productRepository.findById(postRegisterDto.getProductId()).get();
        Market market = marketRepository.findByMarketName(postRegisterDto.getMarketName()).orElseThrow(() -> new MarketnameNotFoundException());

        /*
        Post post = Post.builder()
                .postTitle(postRegisterDto.getPostTitle())
                .postContent(postRegisterDto.getPostContent())
                .market(market)
                .isOnSales("Y")
                .product(product)
                .postImages()
                .build();

        postRepository.save(post);

         */
    }

    @Transactional
    public void getSalePostList(long productId, String postTitle, String postContent, String isOnSale){
        Product product = productRepository.findById(productId).get();

    }
}
