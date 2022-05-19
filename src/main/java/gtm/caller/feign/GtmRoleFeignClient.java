package gtm.caller.feign;

import gtm.caller.dto.RoleDto;
import gtm.caller.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ai.khafizov
 * on 19.05.2022
 */

@FeignClient(name = "roleFeignClient", url = "${gtm.auth-url}", configuration = FeignConfig.class)
public interface GtmRoleFeignClient {

    @GetMapping("/api/v1/roles/")
    RoleDto[] getRoles(@RequestHeader("Authorization") String bearerToken);

    @GetMapping(value = "/api/v1/roles/")
    RoleDto[] searchUserRoles(@RequestHeader("Authorization") String bearerToken, @RequestParam("userName") String userName);
}
