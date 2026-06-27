package com.paramita.bookStore.user.TO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseTO {
    private String username;
    private String token;
}
