package ac.kr.tukorea.capstone.user.repository;

import ac.kr.tukorea.capstone.user.dto.UserInfoDto;

public interface UserRepositoryCustom {
    UserInfoDto getOtherUserInfo(String username, String otherUsername);
}
