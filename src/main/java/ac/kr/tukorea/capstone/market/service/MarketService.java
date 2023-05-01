package ac.kr.tukorea.capstone.market.service;

import ac.kr.tukorea.capstone.config.Exception.UsernameNotFoundException;
import ac.kr.tukorea.capstone.market.dto.MarketRegisterDto;
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
    public void registerMarket(MarketRegisterDto marketRegisterDto) throws RuntimeException{
        User user = userRepository.findByUsername(marketRegisterDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException());
        Market market = Market.builder()
                .market_name(marketRegisterDto.getMarketName())
                .user(user)
                .build();

        marketRepository.save(market);

        Authority authority = Authority.builder()
                .name("ROLE_SELLER")
                .user(user)
                .build();
        authorityRepository.save(authority);

    }
}
