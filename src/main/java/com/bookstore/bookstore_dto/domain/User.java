package com.bookstore.bookstore_dto.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class User {
    private String user_id;

    private String password;

    private String name;


}
