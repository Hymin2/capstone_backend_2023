package ac.kr.tukorea.capstone.market.repository;

import ac.kr.tukorea.capstone.market.entity.Post;
import ac.kr.tukorea.capstone.market.entity.QPost;
import ac.kr.tukorea.capstone.market.entity.QWantPost;
import ac.kr.tukorea.capstone.market.entity.WantPost;
import ac.kr.tukorea.capstone.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WantPostRepositoryImpl implements WantPostRepositoryCustom{
    private QWantPost wantPost = QWantPost.wantPost;
    private QPost post = QPost.post;
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Post> getWantPostList(User user) {
        List<Post> posts = jpaQueryFactory.selectFrom(post)
                .innerJoin(wantPost)
                .on(wantPost.post.eq(post))
                .where(wantPost.user.eq(user))
                .fetch();

        return posts;
    }
}
