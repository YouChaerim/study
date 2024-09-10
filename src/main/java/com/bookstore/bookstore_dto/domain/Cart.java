package com.bookstore.bookstore_dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Cart {
    private int cart_id;
    private String user_id;
    private Date create_Date;
}
