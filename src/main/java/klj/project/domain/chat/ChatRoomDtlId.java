package klj.project.domain.chat;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import klj.project.domain.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
public class ChatRoomDtlId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Builder
    public ChatRoomDtlId (User user, ChatRoom chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public static ChatRoomDtlId createChatRoomDtlId(User user, ChatRoom chatRoom) {
        return ChatRoomDtlId.builder()
                .user(user)
                .chatRoom(chatRoom)
                .build();
    }
}
