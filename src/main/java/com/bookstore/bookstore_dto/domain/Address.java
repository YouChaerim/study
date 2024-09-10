package com.bookstore.bookstore_dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Address {
    private int adress_id;
    private int postal_code;
    private String basic_address;
    private String detail_address;
    private String user_id;
}
