package com.bookstore.bookstore_dto.service;

import com.bookstore.bookstore_dto.domain.Address;
import com.bookstore.bookstore_dto.dto.address.AddressRequestDto;
import com.bookstore.bookstore_dto.dto.address.AddressResponseDto;
import com.bookstore.bookstore_dto.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
    private final AddressRepository addressRepository;

    /**
     * 주소 목록 조회
     */
    public List<AddressResponseDto> findAllAddress(String user_id) throws SQLException {
        List<Address> addresses = addressRepository.findByUserId(user_id);
        List<AddressResponseDto> addressResponseDtos = new ArrayList<>();
        for (int i = 0; i < addresses.size(); i++) {
            AddressResponseDto addressResponseDto = new AddressResponseDto();
            addressResponseDto.setAdress_id(addresses.get(i).getAdress_id());
            addressResponseDto.setPostal_code(addresses.get(i).getPostal_code());
            addressResponseDto.setBasic_address(addresses.get(i).getBasic_address());
            addressResponseDto.setDetail_address(addresses.get(i).getDetail_address());
            addressResponseDto.setUser_id(user_id);
            addressResponseDtos.add(addressResponseDto);
        }
        return addressResponseDtos;
    }

    /**
     * 주소 등록하기
     */
    public void registerAddress(AddressRequestDto addressRequestDto, String user_id) throws SQLException {
        Address address = new Address();
        address.setPostal_code(addressRequestDto.getPostal_code());
        address.setBasic_address(addressRequestDto.getBasic_address());
        address.setDetail_address(addressRequestDto.getDetail_address());

        addressRepository.saveAddress(address, user_id);
        log.info("주소가 성공적으로 등록되었습니다: {}", addressRequestDto);
    }
}
