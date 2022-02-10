package gtm.caller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {
        private boolean success;
        private String token;
        private String userName;
        private String lastActionDate;
}
