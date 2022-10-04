package gtm.caller.feign;

import gtm.caller.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.ws.rs.QueryParam;

/**
 * Created by ai.khafizov
 * on 19.05.2022
 */
@FeignClient(name = "gtmUsersFeignClient", url = "${gtm.auth-url}")
public interface GtmUsersFeignClient {

    @GetMapping("/api/v1/ldap/users")
    UserDto[] getUsers(@RequestHeader("Authorization") String bearerToken);

    @GetMapping("/api/v1/ldap/users")
    UserDto[] searchUsersByName(@RequestHeader("Authorization") String bearerToken, @org.springframework.web.bind.annotation.RequestParam("userName") String userName);

    @GetMapping("/api/v1/ldap/users")
    UserDto[] searchUsersByGroupDn(@RequestHeader("Authorization") String bearerToken, @org.springframework.web.bind.annotation.RequestParam("groupName") String groupName);
}
