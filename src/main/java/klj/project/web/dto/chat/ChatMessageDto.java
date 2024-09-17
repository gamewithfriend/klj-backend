package klj.project.web.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDto {
    // 채팅메세지 ID
    private Long chatMessageId;
    // 메세지 내용
    private String content;
    // 보내는 사람
    private Long senderId;
    // 생성일자
    private LocalDateTime createDate;
}
