package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Market;
import ac.kr.tukorea.capstone.market.entity.QFollowMarket;
import ac.kr.tukorea.capstone.market.entity.QMarket;
import ac.kr.tukorea.capstone.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FollowMarketRepositoryImpl implements FollowMarketRepositoryCustom{
    private QMarket market = QMarket.market;
    private QFollowMarket followMarket = QFollowMarket.followMarket;
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Market> getFollowMarketList(User user) {
        List<Market> markets = jpaQueryFactory.selectFrom(market)
                .innerJoin(followMarket)
                .on(followMarket.market.eq(market))
                .where(followMarket.user.eq(user))
                .fetch();

        return markets;
    }
}
