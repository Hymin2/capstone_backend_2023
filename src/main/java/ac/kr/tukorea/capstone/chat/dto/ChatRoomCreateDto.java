package ac.kr.tukorea.capstone.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomCreateDto {
    private long salePostId;
    private long sellerId;
    private long buyerId;
}
