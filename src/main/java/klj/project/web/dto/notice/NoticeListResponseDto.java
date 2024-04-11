package klj.project.web.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class NoticeListResponseDto {

    private Long id;
    private String receiverNickName;
    private String senderNickName;
    private String title;
    private String content;
    private String createdDate;
    private Long receiverId;
    private Long senderId;

}
