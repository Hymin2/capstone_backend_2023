package ac.kr.tukorea.capstone.market.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostVo {
    private long id;
    private String postTitle;
    private String postContent;
    private Timestamp createdTime;
    private String postImages;
}e
