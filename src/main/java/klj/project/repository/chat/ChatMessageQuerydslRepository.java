package klj.project.repository.chat;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.chat.QChatMessage;
import klj.project.web.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatMessageQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<ChatMessageDto> findChatMessageListByChatRoomId (Long chatRoomId) {
        List<ChatMessageDto> chatMessageDtoList = queryFactory
                .select(Projections.fields(ChatMessageDto.class,
                        QChatMessage.chatMessage.id.as("chatMessageId"),
                        QChatMessage.chatMessage.content,
                        QChatMessage.chatMessage.sender.id.as("senderId"),
                        QChatMessage.chatMessage.createDate
                )).from(QChatMessage.chatMessage)
                .where(QChatMessage.chatMessage.chatRoom.id.eq(chatRoomId))
                .orderBy(QChatMessage.chatMessage.createDate.asc())
                .fetch();
        return chatMessageDtoList;
    }
}
