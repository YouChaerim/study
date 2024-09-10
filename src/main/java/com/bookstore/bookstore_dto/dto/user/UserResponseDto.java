package com.bookstore.bookstore_dto.dto.user;

/**
 * DTO(Data Transfer Object)
 * : 계층 간 데이터 전달을 위해 사용되는 객체, 요청 데이터 전달
 * (client -> controller / controller -> service)
 * ResponseDto
 * : 서버가 클라이언트로 보낼 데이터를 포함
 *   서버에서 처리된 결과나 상태 정보를 담고 있음
 *   getter는 필수, setter는 필요하지 않음
 *   pk값 받기
 */

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDto {
    private String user_id;
    private String password;
}
