package com.bookstore.bookstore_dto.dto.book;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
/**
 * @AllArgsConstructor
 * : 객체를 생성하면서 모든 필드에 값을 할당해야할 때 사용
 * 필요하지 않는 경우: DTO가 빈 객체로 생성된 후, 각 필드를 개별적으로 설정하는 방식으로 사용되는 경우
 * @NoArgsConstructor
 * : 기본 생성자가 필요할 떄 사용
 * 필요하지 않은 경우: DTO를 항상 특정 필드와 함께 생성하고, 기본 생성자를 사용하지 않는 경우
 */
public class BookResponseDto {
    private int book_id;
    private String book_name;
    private int stock;
    private int price;
}
