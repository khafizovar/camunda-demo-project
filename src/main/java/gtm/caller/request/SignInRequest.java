package gtm.caller.request;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Структура Payload запроса авторизации пользователя
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Data
@AllArgsConstructor
public class SignInRequest {
    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
