package klj.project.repository.chat;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatRoomQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Long findChatRoomByUserId(List<Long> chatters) {
        Long chatRoomId = queryFactory
                .select(QChatRoomDtl.chatRoomDtl.chatRoomDtlId.chatRoom.id.as("chatRoomId")
                ).from(QChatRoomDtl.chatRoomDtl)
                .where(QChatRoomDtl.chatRoomDtl.chatRoomDtlId.user.id.in(chatters))
                .groupBy(QChatRoomDtl.chatRoomDtl.chatRoomDtlId.chatRoom)
                .having(QChatRoomDtl.chatRoomDtl.chatRoomDtlId.user.id.count().eq((long) chatters.size()))
                .fetchOne();
        return chatRoomId;
    }

}
