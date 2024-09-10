package com.bookstore.bookstore_dto.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO(Data Transfer Object)
 * : 계층 간 데이터 전달을 위해 사용되는 객체, 응답 데이터 전달
 * (client <- controller / controller <- service)
 * RequestDto
 * : 클라이언트가 서버로 보낼 데이터를 포함
 *   일반적으로 사용자가 입력한 데이터나 요청과 관련된 정보를 담고 있음
 *   getter 메서드 필수, setter는 보통의 경우 필요
 */

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String user_id;
    private String password;
    private String name;
}
