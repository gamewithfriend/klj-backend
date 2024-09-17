package klj.project.domain.chat;

import jakarta.persistence.*;
import klj.project.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private LocalDateTime createDate;

    @Builder
    public ChatMessage (String content, User sender, ChatRoom chatRoom, LocalDateTime createDate) {
        this.content = content;
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.createDate = createDate;
    }

    public static ChatMessage createChatMessage (String content, User sender, ChatRoom chatRoom, LocalDateTime createDate) {
        return ChatMessage.builder()
                .content(content)
                .sender(sender)
                .chatRoom(chatRoom)
                .createDate(createDate)
                .build();
    }
}
