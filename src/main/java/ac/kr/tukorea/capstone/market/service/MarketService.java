package ac.kr.tukorea.capstone.market.service;

import ac.kr.tukorea.capstone.config.Exception.DuplicateMarketNameException;
import ac.kr.tukorea.capstone.config.Exception.ExistingMarketOfUserException;
import ac.kr.tukorea.capstone.config.Exception.UsernameNotFoundException;
import ac.kr.tukorea.capstone.market.dto.MarketSaveDto;
import ac.kr.tukorea.capstone.market.entity.Market;
import ac.kr.tukorea.capstone.market.repository.MarketRepository;
import ac.kr.tukorea.capstone.user.entity.Authority;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.repository.AuthorityRepository;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
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
        Market market = marketRepository.findByMarketName(oldMarketName).orElseThrow();

        market.setMarketName(marketSaveDto.getMarketName());
    }

    @Transactional
    public void uploadImage(){

    }
}
