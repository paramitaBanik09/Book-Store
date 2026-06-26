package com.paramita.bookStore.user.TO;



import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginReqTo {
	@NotBlank
    private String username;
	@NotBlank
    private String password;
}
