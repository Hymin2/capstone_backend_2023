package ac.kr.tukorea.capstone.chat.service;

import ac.kr.tukorea.capstone.chat.dto.ChattingCreateDto;
import ac.kr.tukorea.capstone.chat.dto.ChattingMessageDto;
import ac.kr.tukorea.capstone.chat.entity.ChattingContent;
import ac.kr.tukorea.capstone.chat.entity.ChattingRoom;
import ac.kr.tukorea.capstone.chat.mapper.ChattingContentMapper;
import ac.kr.tukorea.capstone.chat.mapper.ChattingRoomMapper;
import ac.kr.tukorea.capstone.chat.repository.ChattingContentRepository;
import ac.kr.tukorea.capstone.chat.repository.ChattingRoomRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    private final ObjectMapper objectMapper;
    //private List<ChattingRoom> chattingRooms;
    private final ChattingRoomMapper chattingRoomMapper;
    private final ChattingContentMapper chattingContentMapper;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingContentRepository chattingContentRepository;

    public ChattingRoom createRoom(ChattingCreateDto chattingCreateDto) {

        ChattingRoom chattingRoom = chattingRoomMapper.ChattingRoomCreateInfo(chattingCreateDto);

        return chattingRoomRepository.save(chattingRoom);
    }

    public ChattingContent createContent(ChattingMessageDto chattingMessageDto) {

        ChattingContent chattingContent = chattingContentMapper.ChattingContentCreateInfo(chattingMessageDto);

        return chattingContentRepository.save(chattingContent);
    }

    public <T> void sendMessage(WebSocketSession session, T message) {
        try{
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
