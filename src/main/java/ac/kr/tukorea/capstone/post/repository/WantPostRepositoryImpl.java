package ac.kr.tukorea.capstone.post.repository;

import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.entity.QPost;
import ac.kr.tukorea.capstone.post.entity.QWantPost;
import ac.kr.tukorea.capstone.post.vo.PostVo;
import ac.kr.tukorea.capstone.user.entity.QUser;
import ac.kr.tukorea.capstone.user.entity.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class WantPostRepositoryImpl implements WantPostRepositoryCustom{
    private QWantPost wantPost = QWantPost.wantPost;
    private QPost post = QPost.post;
    private QUser user = QUser.user;
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<PostVo> getWantPostList(User user) {
        List<PostVo> posts = jpaQueryFactory
                .select(Projections.bean(PostVo.class, post.id, this.user.username, this.user.imagePath, post.postTitle, post.postContent, post.isOnSales, post.postCreatedTime))
                .innerJoin(wantPost)
                .on(wantPost.post.eq(post))
                .innerJoin(this.user)
                .on(wantPost.user.eq(user))
                .where(this.user.eq(user))
                .fetch();

        return posts;
    }
}
