package com.bookstore.bookstore_dto.dto.address;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AddressResponseDto {
    private int adress_id;
    private int postal_code;
    private String basic_address;
    private String detail_address;
    private String user_id;
}
