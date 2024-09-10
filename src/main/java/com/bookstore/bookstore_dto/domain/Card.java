package com.bookstore.bookstore_dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Card {
    private String card_num;
    private String card_valid;
    private String card_company;
}
