package klj.project.web.dto.notice;

import klj.project.domain.notice.NoticeType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeListDto {

    private Long id;
    private String receiverNickName;
    private String senderNickName;
    private LocalDateTime createdDate;
    private NoticeType noticeType;
    private String name;
    private String description;
    private Long receiverId;
    private Long senderId;
}
