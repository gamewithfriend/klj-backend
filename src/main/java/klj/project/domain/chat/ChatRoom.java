package klj.project.domain.chat;

import jakarta.persistence.*;
import klj.project.domain.user.User;
import klj.project.web.dto.chat.ChatRoomDto;
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
    public ChatRoom (String chatRoomTitle, LocalDateTime createDate) {
        this.chatRoomTitle = chatRoomTitle;
        this.createDate = LocalDateTime.now();
    }

    public static ChatRoom createChatRoom(String chatRoomTitle, LocalDateTime createDate) {
        return ChatRoom.builder()
                .chatRoomTitle(chatRoomTitle)
                .createDate(createDate)
                .build();
    }

    public ChatRoomDto convertToDto() {
        ChatRoomDto chatRoomDto = new ChatRoomDto();
        chatRoomDto.setChatRoomId(this.id);
        chatRoomDto.setChatRoomTitle(this.chatRoomTitle);

        return chatRoomDto;
    }
}
