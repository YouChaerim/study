package com.bookstore.bookstore_dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Book {
    private int book_id;

    private String book_name;

    private int stock;

    private int price;


}
