package gtm.caller.dto;

import lombok.Data;

/**
 * Created by ai.khafizov
 * on 11.02.2022
 */
@Data
public class UserDto {
    private String sAMAccountName;
    private String displayName;
    private String dn;
    private String userPrincipalName;
    private String email;
}
