package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.chat.ChatMessage;
import klj.project.domain.chat.ChatRoom;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.repository.chat.ChatMessageQuerydslRepository;
import klj.project.repository.chat.ChatMessageRepository;
import klj.project.repository.chat.ChatRoomRepository;
import klj.project.service.ChatService;
import klj.project.util.chat.Message;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.chat.ChatMessageDto;
import klj.project.web.dto.chat.ChatRoomDto;
import klj.project.web.dto.chat.ChatRoomOpenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageQuerydslRepository chatMessageQuerydslRepository;
    private final ChatService chatService;

    @MessageMapping("/chat/{chatRoomId}/sendMessage")
    @SendTo("/topic/{chatRoomId}")
    public Message sendMessage(@DestinationVariable Long chatRoomId, @Payload Message message) {
        User sender = userRepository.findById(message.getSenderId()).get();
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();

        // db에 message 내용을 insert
        ChatMessage chatMessage = ChatMessage.createChatMessage(
                message.getContent(),
                sender,
                chatRoom,
                LocalDateTime.now()
        );
        chatMessageRepository.save(chatMessage);

        return message;
    }

    @Operation(summary = "채팅 메세지 리스트", description = "채팅 메세지 내용 조회")
    @GetMapping(path = "/chat/getChatMessageList", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<ChatMessageDto>> getChatMessageList(@RequestParam Long chatRoomId) {
        try {
            List<ChatMessageDto> chatMessageDtoList = chatMessageQuerydslRepository.findChatMessageListByChatRoomId(chatRoomId);
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

    @Operation(summary = "채팅방 개설", description = "채팅방 open")
    @PostMapping(path = "/chat/openChatRoom", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<ChatRoomDto> openChatRoom(@RequestBody ChatRoomOpenDto chatRoomOpenDto) {
        try {
            ChatRoomDto chatRoomDto = chatService.openChatRoom(chatRoomOpenDto);
            return KljResponse.create()
                    .succeed()
                    .buildWith(chatRoomDto);
        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "채팅방 개설에 실패하였습니다."))
                    .buildWith(null);
        }
    }
}
