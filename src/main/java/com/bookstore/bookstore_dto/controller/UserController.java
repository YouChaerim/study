package com.bookstore.bookstore_dto.controller;

import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.user.UserRequestDto;
import com.bookstore.bookstore_dto.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
/**
 * @Autowired: Spring이 자동으로 의존성을 주입해주는 어노테이션
 * - 필드 주입, 생성자 주입, 세터 주입
 * @RequireArgsConstructor: Lombok 라이브러리에서 제공, 클래스의 'final'필드와 '@NonNull'이 붙은 필드를 파라미터로 받는 생성자를 자동으로 생성
 * - 생성자 주입
 */
@RequiredArgsConstructor //생성자를 자동으로 생성
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 페이지 이동
     */
    @GetMapping(value = "/users/signup")
    public String signupForm() { return "users/signup"; }

    /**
     * 로그인 페이지 이동
     */
    @GetMapping(value = "/users/signin")
    public String signinForm() { return "users/signin"; }

    /**
     * 회원가입
     */
    @PostMapping(value = "/users/signup")
    public String signup(@ModelAttribute UserRequestDto userRequestDto) {
        try {
            userService.join(userRequestDto);
        } catch (Exception e) {
            return "redirect:/";
        }
        return "redirect:/";
    }

    /**
     * @ModelAttribute - form 형식일때
     * @RequestBody - 그냥
     */

    /**
     * 로그인
     */
    @PostMapping(value = "/users/signin")
//    @ResponseBody //컨트롤러 메서드가 반환하는 값을 HTTP 응답 본문으로 직접 전송
    public String signin(@ModelAttribute UserRequestDto userRequestDto, HttpSession session) {
        log.info("user: {}", userRequestDto.toString());

        User requestDto = null;
        try {
            requestDto = userService.validUserId(userRequestDto);
            if (requestDto != null){
                session.setAttribute("userInfo", requestDto);
                log.info("로그인 성공");
            }
        } catch (Exception e) {
            return "redirect:/users/signin";
        }
        return "redirect:/book/list";
    }

    /**
     * 로그인
     * json 형태로 보여주는 방법
     */
//    @PostMapping(value = "/users/signin")
//    @ResponseBody
//    public ResponseEntity<?> signin(@ModelAttribute UserRequestDto userRequestDto, HttpSession session) {
//        log.info("user: {}", userRequestDto.toString());
//        User requestDto = null;
//        try {
//            requestDto = userService.validUserId(userRequestDto);
//            if (requestDto != null){
//                session.setAttribute("userInfo", requestDto);
//                log.info("로그인 성공");
//            }
//        } catch (Exception ezz) {
//            return null;
//        }
//        return ResponseEntity.ok().body(requestDto);
//    }

    /**
     * 로그아웃
     */
    @GetMapping(value = "/users/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userInfo"); //세션무효화
        return "redirect:/";
    }
}
