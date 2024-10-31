package klj.project.service;


import klj.project.domain.chat.ChatRoom;
import klj.project.domain.chat.ChatRoomDtl;
import klj.project.domain.chat.ChatRoomDtlId;
import klj.project.domain.user.Trainer;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.repository.UserTrainerRepository;
import klj.project.repository.chat.ChatMessageQuerydslRepository;
import klj.project.repository.chat.ChatRoomDtlRepository;
import klj.project.repository.chat.ChatRoomQuerydslRepository;
import klj.project.repository.chat.ChatRoomRepository;
import klj.project.web.dto.chat.ChatMessageDto;
import klj.project.web.dto.chat.ChatRoomDto;
import klj.project.web.dto.chat.ChatRoomOpenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChatService {

    private final ChatMessageQuerydslRepository chatMessageQuerydslRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomDtlRepository chatRoomDtlRepository;
    private final UserRepository userRepository;
    private final UserTrainerRepository userTrainerRepository;
    private final ChatRoomQuerydslRepository chatRoomQuerydslRepository;

    public List<ChatMessageDto> getChatMessageList(Long chatRoomId) {
        List<ChatMessageDto> chatMessageList = chatMessageQuerydslRepository.findChatMessageListByChatRoomId(chatRoomId);

        return chatMessageList;
    }

    public ChatRoomDto openChatRoom(ChatRoomOpenDto chatRoomOpenDto) {
        ChatRoomDto chatRoomDto = null;

        String chatRoomTitle = chatRoomOpenDto.getChatRoomTitle();
        List<Long> receivers = chatRoomOpenDto.getReceivers();

        // 1. 해당 참여자들의 채팅방이 존재하는지 확인
        receivers.add(chatRoomOpenDto.getUserId());

        Long chatRoomId = chatRoomQuerydslRepository.findChatRoomByUserId(receivers);
        if (chatRoomId != null && chatRoomId != 0) {
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
            chatRoomDto = chatRoom.convertToDto();

            return chatRoomDto;
        }

        // 2. 채팅방 등록
        ChatRoom chatRoom = ChatRoom.createChatRoom(chatRoomTitle, LocalDateTime.now());
        chatRoom = chatRoomRepository.save(chatRoom);

        // 3. 채팅방 구성원 등록
        for (Long receiver : receivers) {
            User user = userRepository.findById(receiver).get();
            ChatRoomDtlId chatRoomDtlId = ChatRoomDtlId.createChatRoomDtlId(user, chatRoom);
            ChatRoomDtl chatRoomDtl = new ChatRoomDtl(chatRoomDtlId);

            chatRoomDtlRepository.save(chatRoomDtl);
        }

        chatRoomDto = chatRoom.convertToDto();

        return chatRoomDto;
    }
}
