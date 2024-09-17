package klj.project.service;

import klj.project.domain.chat.ChatMessage;
import klj.project.repository.ChatQuerydslRepository;
import klj.project.web.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

    private final ChatQuerydslRepository chatQuerydslRepository;

    public List<ChatMessageDto> getChatMessageList(Long chatRoomId) {
        List<ChatMessageDto> chatMessageList = chatQuerydslRepository.findChatMessageListByChatRoomId(chatRoomId);

        return chatMessageList;
    }
}
