package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.code.QCode;
import klj.project.domain.notice.Notice;
import klj.project.domain.notice.QNotice;
import klj.project.domain.user.OauthType;
import klj.project.domain.user.QUser;
import klj.project.domain.user.User;
import klj.project.web.dto.notice.NoticeListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static klj.project.domain.code.QCode.*;
import static klj.project.domain.notice.QNotice.notice;

@Repository
@RequiredArgsConstructor
public class NoticeQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<NoticeListDto> findByUserIdGetNoticeList (Long userId){

        List<NoticeListDto> noticeList =  queryFactory
                .select(Projections.fields(NoticeListDto.class,
                        notice.id,
                        notice.receiveUser.nickName.as("receiverNickName"),
                        notice.sendUser.nickName.as("senderNickName"),
                        notice.createdDate,
                        notice.noticeType,
                        code.name,
                        code.description,
                        notice.receiveUser.id.as("receiverId"),
                        notice.sendUser.id.as("senderId")
                )).from(notice, code)
                .where(notice.receiveUser.id.eq(userId)
                        , notice.deleteFlag.eq(false)
                        , code.id.eq(notice.noticeType.stringValue()))
                .orderBy(notice.id.desc())
                .fetch();
        return noticeList;
    }

    public Long findByUserIdGetUnReadNoticeCount (Long userId){

        Long unReadNoticeCount =  queryFactory
                .select(notice.count()
                ).from(notice)
                .where(notice.receiveUser.id.eq(userId)
                        , notice.deleteFlag.eq(false)
                        , notice.readFlag.eq(false))
                .fetchOne();
        return unReadNoticeCount;
    }

    public void deleteNotice (List<Long> noticeList){
        queryFactory
                .update(notice)
                .set(notice.deleteFlag,true)
                .where(notice.id.in(noticeList))
                .execute();
    }

    public void readNotice (List<Long> noticeList){
        queryFactory
                .update(notice)
                .set(notice.readFlag,true)
                .where(notice.id.in(noticeList))
                .execute();
    }
}
