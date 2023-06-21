package ac.kr.tukorea.capstone.user.service;

import ac.kr.tukorea.capstone.config.Exception.DuplicateNickNameException;
import ac.kr.tukorea.capstone.config.Exception.UsernameNotFoundException;
import ac.kr.tukorea.capstone.config.WebSecurityConfig;
import ac.kr.tukorea.capstone.config.util.ImageComponent;
import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.repository.PostImageRepository;
import ac.kr.tukorea.capstone.post.repository.PostRepository;
import ac.kr.tukorea.capstone.user.dto.FollowListDto;
import ac.kr.tukorea.capstone.user.dto.FollowRegisterDto;
import ac.kr.tukorea.capstone.user.dto.UserInfoDto;
import ac.kr.tukorea.capstone.user.dto.UserRegisterDto;
import ac.kr.tukorea.capstone.user.entity.Authority;
import ac.kr.tukorea.capstone.user.entity.Follow;
import ac.kr.tukorea.capstone.user.entity.User;
import ac.kr.tukorea.capstone.user.mapper.UserMapper;
import ac.kr.tukorea.capstone.user.repository.FollowRepository;
import ac.kr.tukorea.capstone.user.repository.FollowRepositoryCustom;
import ac.kr.tukorea.capstone.user.repository.UserRepository;
import ac.kr.tukorea.capstone.user.repository.UserRepositoryCustom;
import ac.kr.tukorea.capstone.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final UserMapper userMapper;
    private final ImageComponent imageComponent;
    private final FollowRepository followRepository;
    private final FollowRepositoryCustom followRepositoryCustom;
    private final UserRepositoryCustom userRepositoryCustom;

    public void registerUser(UserRegisterDto userRegisterDto){
        User user = userMapper.UserRegisterInfo(userRegisterDto);
        user.setEncodePassword(webSecurityConfig.getPasswordEncoder().encode(user.getUserPassword()));

        Authority authority = Authority.builder()
                .name("ROLE_USER")
                .user(user)
                .build();

        user.setAuthorities(Collections.singletonList(authority));
        userRepository.save(user);
    }

    @Transactional
    public void uploadImage(MultipartFile multipartFile, String username) {
        String os = System.getProperty("os.name").toLowerCase();
        String imgPath = null;

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/user/profile/img/";
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/user/profile/img/";

        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());

        imgPath = imageComponent.uploadImage(multipartFile, imgPath);

        user.setImagePath(imgPath);
        user.setImageSize(multipartFile.getSize());
    }

    @Transactional
    public UserInfoDto getUserInfo(String username, String otherUsername){
        UserInfoDto userInfoDto = userRepositoryCustom.getOtherUserInfo(username, otherUsername);

        return userInfoDto;
    }

    @Transactional
    public void registerFollow(FollowRegisterDto followRegisterDto){
        User followingUser = userRepository.findByUsername(followRegisterDto.getFollowingUsername()).orElseThrow(() -> new UsernameNotFoundException());
        User followedUser = userRepository.findByUsername(followRegisterDto.getFollowedUsername()).orElseThrow(() -> new UsernameNotFoundException());

        Follow follow = Follow.builder()
                .followingUser(followingUser)
                .followedUser(followedUser)
                .build();

        followRepository.save(follow);
    }

    @Transactional
    public FollowListDto getFollowingList(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
        List<UserVo> follows = followRepositoryCustom.getFollowingList(username);

        FollowListDto followList = new FollowListDto(user.getId(), user.getUsername(), follows);

        return followList;
    }

    @Transactional
    public FollowListDto getFollowerList(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
        List<UserVo> follows = followRepositoryCustom.getFollowerList(username);

        FollowListDto followList = new FollowListDto(user.getId(), user.getUsername(), follows);

        return followList;
    }

    @Transactional
    public void deleteFollow(String followingUsername, String followerUsername){
        User followingUser = userRepository.findByUsername(followingUsername).orElseThrow(UsernameNotFoundException::new);
        User followerUser = userRepository.findByUsername(followerUsername).orElseThrow(UsernameNotFoundException::new);

        followRepositoryCustom.deleteFollow(followingUser, followerUser);
    }

    public Boolean isDuplicateId(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean isDuplicateNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    public void deleteUser(String username){
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public void updateNickname(String username, String nickname) {
        User user = userRepository.findByUsername(username).orElseThrow(UsernameNotFoundException::new);
        if(isDuplicateNickname(nickname)) throw new DuplicateNickNameException();

        user.setNickname(nickname);
    }

    public Resource getUserImage(String name) {
        String imgPath = "";
        String os = System.getProperty("os.name").toLowerCase();

        if(os.contains("win"))
            imgPath = "c:/capstone/resource/user/profile/img/" + name;
        else if(os.contains("linux"))
            imgPath = "/capstone/resource/user/profile/img/" + name;

        return imageComponent.getImage(imgPath);
    }
}
