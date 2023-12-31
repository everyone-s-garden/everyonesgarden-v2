package com.graden.back.service;

import com.graden.back.domain.ChatRoom;
import com.graden.back.domain.ChatRoomInfo;
import com.graden.back.repository.chatroom.ChatRoomRepository;
import com.graden.back.repository.chatroominfo.ChatRoomInfoRepository;
import com.graden.back.service.dto.request.ChatRoomCreateParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomInfoRepository chatRoomInfoRepository;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, ChatRoomInfoRepository chatRoomInfoRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomInfoRepository = chatRoomInfoRepository;
    }

    @Transactional
    public Long createChatRoom(ChatRoomCreateParam param) {
        if (chatRoomInfoRepository.existsByParams(param.toChatRoomCreateRepositoryParam())) {
            throw new IllegalArgumentException("해당 게시글과 유저에 관한 채팅방은 이미 존재하여 새롭게 생성할 수 없습니다.");
        }

        ChatRoom savedChatRoom = chatRoomRepository.save(param.toChatRoom());

        ChatRoomInfo chatRoomInfoToWriter = param.toChatRoomInfoToWriter();
        ChatRoomInfo chatRoomInfoToViewer = param.toChatRoomInfoToViewer();
        chatRoomInfoToWriter.create(savedChatRoom);
        chatRoomInfoToViewer.create(savedChatRoom);
        chatRoomInfoRepository.saveAll(List.of(chatRoomInfoToWriter, chatRoomInfoToViewer));

        return savedChatRoom.getChatRoomId();
    }

}
