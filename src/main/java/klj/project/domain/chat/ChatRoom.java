package klj.project.domain.chat;

import jakarta.persistence.*;
import klj.project.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    private String chatRoomTitle;

    private LocalDateTime createDate;

    @Builder
    public ChatRoom (Long id, String chatRoomTitle, LocalDateTime createDate) {
        this.id = id;
        this.chatRoomTitle = chatRoomTitle;
        this.createDate = LocalDateTime.now();
    }

    public static ChatRoom createChatRoom (Long id, String chatRoomTitle, LocalDateTime createDate) {
        return ChatRoom.builder()
                .id(id)
                .chatRoomTitle(chatRoomTitle)
                .createDate(LocalDateTime.now())
                .build();
    }
}
