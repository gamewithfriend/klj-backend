package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.user.OauthType;
import klj.project.domain.user.QUser;
import klj.project.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public User findByOauthIdAndOauthType (String oauthId, OauthType oauthType){

        User user =  queryFactory
                .select(Projections.fields(User.class,
                        QUser.user.id,
                        QUser.user.oauthId,
                        QUser.user.oauthType,
                        QUser.user.nickName,
                        QUser.user.authority
                )).from(QUser.user)
                .where(QUser.user.oauthId.eq(oauthId),QUser.user.oauthType.eq(oauthType))
                .fetchOne();
        return user;
    }
}
