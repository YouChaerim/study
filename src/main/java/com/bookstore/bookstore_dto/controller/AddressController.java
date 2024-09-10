package com.bookstore.bookstore_dto.controller;

import com.bookstore.bookstore_dto.domain.User;
import com.bookstore.bookstore_dto.dto.address.AddressRequestDto;
import com.bookstore.bookstore_dto.dto.address.AddressResponseDto;
import com.bookstore.bookstore_dto.service.AddressService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AddressController {
    private final AddressService addressService;

    /**
     * 주소 목록 페이지
     */
    @GetMapping(value = "/address/addresslist")
    public String addressList(Model model, HttpSession session) {
        try {
            User user = (User) session.getAttribute("userInfo");
            List<AddressResponseDto> addressList = addressService.findAllAddress(user.getUser_id());
            model.addAttribute("addressList", addressList);
        } catch (Exception e) {
            log.info("errorMessage: ", e);
            model.addAttribute("errorMessage", "주소 목록을 불러오는 데 실패했습니다.");
        }
        return "address/addresslist";
    }

    /**
     * 주소 등록 페이지
     */
    @GetMapping(value = "/address/addressregister")
    public String addressForm() { return "address/addressregister"; }

    @PostMapping(value = "/address/addressregister")
    public String saveAddress(@ModelAttribute AddressRequestDto addressRequestDto,
                              HttpSession session) {
        User user = (User) session.getAttribute("userInfo");
        try {
            addressService.registerAddress(addressRequestDto, user.getUser_id());
        } catch (SQLException e) {
            // SQLException 처리
            log.info("errormessage: ", e); // 로그에 출력
            return "redirect:/address/addresslist"; // 오류 발생 시 리다이렉트
        }
        return "redirect:/address/addresslist";
    }
}
