package klj.project.web.dto.chat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomOpenDto {
    Long userId;
    List<Long> receivers;
    String chatRoomTitle;
}
