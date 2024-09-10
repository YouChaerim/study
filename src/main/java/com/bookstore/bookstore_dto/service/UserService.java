package com.bookstore.bookstore_dto.service;

import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.user.UserRequestDto;
import com.bookstore.bookstore_dto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    public void join(UserRequestDto userRequestDto) throws Exception {
        String user_id = userRequestDto.getUser_id();

        Optional<User> findUser = userRepository.findByUserId(user_id);

        if (findUser == null) {
            User user = new User();
            user.setUser_id(userRequestDto.getUser_id());
            user.setPassword(userRequestDto.getPassword());
            user.setName(userRequestDto.getName());

            userRepository.save(user);
            log.info("user_id = {}", user);
        } else {
            throw new IllegalStateException("이미 존재하는 아이디 입니다.");
        }
    }

    /**
     * 로그인
     */
//    public User validUserId(UserRequestDto userRequestDto) throws  Exception {
//
//        User findUser = userRepository.findByUserId(userRequestDto.getUser_id());
//        // "ffff" -> null?
//        // "" -> null?
//        // 0 -> null?
//        // null -> null
//        // 인스턴스 -> null
//
////        User user = new User(); -> null
//
//        if (findUser == null) {
//            throw new IllegalStateException("존재하지 않는 회원입니다.");
//        } else if (findUser.getPassword().equals(userRequestDto.getPassword())) {
//            log.info("findUser = {}", findUser.getUser_id());
//            return findUser;
//        }
//        return null;
//    }

    public User validUserId(UserRequestDto userRequestDto) throws  Exception {

        Optional<User> findUser = userRepository.findByUserId(userRequestDto.getUser_id());

        if (findUser == null) {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
        User user = findUser.get();
        if (user.getPassword().equals(userRequestDto.getPassword())) {
            log.info("findUser = {}", user.getUser_id());
            return user;
        }
        return null;
    }
}
