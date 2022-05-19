package gtm.caller.feign;

import gtm.caller.request.SignInRequest;
import gtm.caller.response.SignInResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by ai.khafizov
 * on 19.05.2022
 */
@FeignClient(name = "signIn", url = "${gtm.auth-url}")
public interface GtmSignInFeignClient {

    @PostMapping(value = "/api/auth/signin")
    SignInResponse auth(@RequestBody SignInRequest example);
}
