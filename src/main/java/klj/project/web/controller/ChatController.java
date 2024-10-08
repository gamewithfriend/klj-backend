package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.chat.ChatMessage;
import klj.project.domain.chat.ChatRoom;
import klj.project.domain.user.User;
import klj.project.repository.ChatQuerydslRepository;
import klj.project.repository.ChatRepository;
import klj.project.repository.ChatRoomRepository;
import klj.project.repository.UserRepository;
import klj.project.util.chat.Message;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatQuerydslRepository chatQuerydslRepository;

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        User sender = userRepository.findById(message.getSenderId()).get();
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).get();

        // db에 message 내용을 insert
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                message.getContent(),
                sender,
                chatRoom,
                LocalDateTime.now()
        );
        chatRepository.save(chatMessage);

        return message;
    }

    @Operation(summary = "채팅창 메세지 리스트", description = "todo: implementation")
    @GetMapping(path = "/chat/getChatMessageList", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<ChatMessageDto>> getChatMessageList(@RequestParam Long chatRoomId) {
        try {

            List<ChatMessageDto> chatMessageDtoList = chatQuerydslRepository.findChatMessageListByChatRoomId(chatRoomId);
            return KljResponse.create()
                    .succeed()
                    .buildWith(chatMessageDtoList);
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "채팅내용을 조회할 수 없습니다."))
                    .buildWith(null);
        }
    }
}
