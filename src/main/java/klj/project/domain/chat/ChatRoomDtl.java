package klj.project.domain.chat;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoomDtl {

    @EmbeddedId
    private ChatRoomDtlId chatRoomDtlId;
}
