package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.chat.ChatMessage;
import klj.project.domain.chat.QChatMessage;
import klj.project.domain.code.QCode;
import klj.project.web.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatQuerydslRepository {

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
