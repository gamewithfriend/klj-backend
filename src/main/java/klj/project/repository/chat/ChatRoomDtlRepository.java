package klj.project.repository.chat;

import klj.project.domain.chat.ChatRoomDtl;
import klj.project.domain.chat.ChatRoomDtlId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomDtlRepository extends JpaRepository<ChatRoomDtl, ChatRoomDtlId> {
}
