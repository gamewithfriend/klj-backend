package klj.project.domain.chat;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import klj.project.domain.user.User;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ChatRoomDtlId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;
}
