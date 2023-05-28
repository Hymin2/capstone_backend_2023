package ac.kr.tukorea.capstone.user.service;

import ac.kr.tukorea.capstone.config.Exception.DuplicateNickNameException;
import ac.kr.tukorea.capstone.config.Exception.UsernameNotFoundException;
import ac.kr.tukorea.capstone.config.WebSecurityConfig;
import ac.kr.tukorea.capstone.config.util.ImageComponent;
import ac.kr.tukorea.capstone.post.entity.Post;
import ac.kr.tukorea.capstone.post.entity.PostImage;
import ac.kr.tukorea.capstone.post.repository.PostImageRepository;
import ac.kr.tukorea.capstone.post.repository.PostRepository;
import ac.kr.tukorea.capstone.post.vo.PostVo;
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
import ac.kr.tukorea.capstone.user.vo.FollowVo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WebSecurityConfig webSecurityConfig;
    private final UserMapper userMapper;
    private final ImageComponent imageComponent;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final FollowRepository followRepository;
    private final FollowRepositoryCustom followRepositoryCustom;

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

        user.setImagePath("http://localhost:8080/api/v1/user/img?name=" + imgPath);
        user.setImageSize(multipartFile.getSize());
    }

    @Transactional
    public UserInfoDto getUserInfo(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
        List<Post> posts = postRepository.findByUser(user);
        List<PostVo> postVos = new ArrayList<>();

        int followNum = followRepository.countByFollowedUser(user);
        int followingNum = followRepository.countByFollowingUser(user);
        int onSales = 0, soldOut = 0;

        /*
        for(Post post : posts){
            List<PostImage> postImage = postImageRepository.findByPost(post);
            List<String> imagePaths = postImage.stream().map(PostImage::getImagePath).collect(Collectors.toList());

            postVos.add(new PostVo(post.getId(), user.getNickname(), user.getImagePath(), post.getPostTitle(), post.getPostContent(), post.getIsOnSales(), post.getPostCreatedTime() ,imagePaths));

            if(post.getIsOnSales().equals("Y")) onSales++;
            else soldOut++;
        }
         */

        return new UserInfoDto(user.getId(), username, user.getNickname(), user.getImagePath(), soldOut, onSales, followNum, followingNum);
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
    public FollowListDto getFollowList(String username){
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException());
        List<FollowVo> follows = followRepositoryCustom.getFollowList(user);

        FollowListDto followList = new FollowListDto(user.getId(), user.getUsername(), follows);

        return followList;
    }
    public void deleteFollow(long followId){ followRepository.deleteById(followId);}

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
