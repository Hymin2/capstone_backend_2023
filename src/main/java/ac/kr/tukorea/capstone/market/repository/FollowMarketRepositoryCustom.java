package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Market;
import ac.kr.tukorea.capstone.user.entity.User;

import java.util.List;

public interface FollowMarketRepositoryCustom {
    List<Market> getFollowMarketList(User user);
}
