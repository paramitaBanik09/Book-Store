package com.paramita.bookStore.user.TO;

import java.util.Date;

import com.paramita.bookStore.user.entity.User;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserTo {
	private Integer id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;
}
