package ac.kr.tukorea.capstone.market.service;

import ac.kr.tukorea.capstone.config.Exception.DuplicateMarketNameException;
import ac.kr.tukorea.capstone.config.Exception.ExistingMarketOfUserException;
import ac.kr.tukorea.capstone.config.Exception.MarketnameNotFoundException;
import ac.kr.tukorea.capstone.config.Exception.UsernameNotFoundException;
import ac.kr.tukorea.capstone.market.dto.MarketDto;
import ac.kr.tukorea.capstone.market.dto.MarketSaveDto;
import ac.kr.tukorea.capstone.market.entity.Market;
import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.market.entity.PostImage;
import ac.kr.tukorea.capstone.market.repository.MarketRepository;
import ac.kr.tukorea.capstone.market.repository.PostImageRepository;
import ac.kr.tukorea.capstone.market.repository.PostRepository;
import ac.kr.tukorea.capstone.market.vo.PostVo;
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
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Transactional
    public void registerMarket(MarketSaveDto marketSaveDto){
        User user = userRepository.findByUsername(marketSaveDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException());

        if(isExistMarketOfUer(user)) throw new ExistingMarketOfUserException();

        try {
            Market market = Market.builder()
                    .marketName(marketSaveDto.getMarketName())
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
    public void updateMarketName(MarketSaveDto marketSaveDto, String oldMarketName){
        Market market = marketRepository.findByMarketName(oldMarketName).orElseThrow(() -> new MarketnameNotFoundException());

        market.setMarketName(marketSaveDto.getMarketName());
    }

    @Transactional
    public void uploadImage(MultipartFile multipartFile, String marketName) {
        if(!multipartFile.isEmpty()){

        }
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
            List<String> imgPaths = new ArrayList<>();

            for(PostImage postImage : post.getPostImages()){
                imgPaths.add(postImage.getImagePath());
            }

            postVos.add(new PostVo(post.getId(), post.getPostTitle(), post.getPostContent(), imgPaths));

            if(post.getIsOnSales().equals("Y")) onSales++;
            else soldOut++;
        }

        return new MarketDto(market.getId(), marketName, market.getMarketImagePath(), soldOut, onSales, postVos);
    }
}
