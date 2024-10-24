package klj.project.util.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    // 메세지의 타입을 정의하는 enum
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    private MessageType type;
    private String content;
    private Long senderId;
    private Long chatRoomId;
    private String timestamp;

    // 기본 생성자
    public Message() {}

    // 초기화 생성자
    public Message(MessageType type, String content, Long senderId, Long chatRoomId, String timestamp) {
        this.type = type;
        this.content = content;
        this.senderId = senderId;
        this.chatRoomId = chatRoomId;
        this.timestamp = timestamp;
    }
}
