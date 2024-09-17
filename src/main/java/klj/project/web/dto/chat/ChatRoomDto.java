package klj.project.web.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Long chatRoomSeq;
    private String chatRoomTitle;
}