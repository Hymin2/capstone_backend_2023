package ac.kr.tukorea.capstone.chat.repository;

import ac.kr.tukorea.capstone.chat.entity.ChattingMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChattingContentRepository extends JpaRepository<ChattingMessage, Long> {
}
