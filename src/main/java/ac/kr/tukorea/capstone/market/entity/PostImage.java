package ac.kr.tukorea.capstone.market.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "post_image_table")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostImage {
    @Id
    private long id;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "image_size")
    private int imageSize;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
