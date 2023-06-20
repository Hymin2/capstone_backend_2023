package ac.kr.tukorea.capstone.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatRoomManager {
    private Map<String, ChatRoom> chatRooms;
    public void createChatRoom(String roomId) {
        ChatRoom chatRoom = new ChatRoom();
        chatRooms.put(roomId, chatRoom);
    }

    public void removeChatRoom(String roomId) {
        chatRooms.remove(roomId);
    }

    public ChatRoom getChatRoom(String roomId) {
        return chatRooms.get(roomId);
    }
    public int roomSize(){return chatRooms.size();}
}
