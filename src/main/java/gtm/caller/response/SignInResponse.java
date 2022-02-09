package gtm.caller.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Data
@AllArgsConstructor
public class SignInResponse {
        private boolean success;
        private String token;
        private String userName;
        private String lastActionDate;
}
