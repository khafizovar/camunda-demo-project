package gtm.caller.service;

import gtm.caller.request.SignInRequest;
import gtm.caller.response.SignInResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import spinjar.com.fasterxml.jackson.databind.JsonNode;
import spinjar.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Service
@Scope("singleton")
public class GtmRoleService {
    /** jwt token активной сессии */
    private String jwtToken;

    private String uname = "admin";
    private String pwd = "Qwerty123";

    private String authSignInUrl = "http://localhost:8080/api/auth/signin";

    private void authorize() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<SignInRequest> request = new HttpEntity<>(new SignInRequest(
          uname, pwd
        ));
        SignInResponse sir = restTemplate.postForObject(authSignInUrl, request, SignInResponse.class);
        this.jwtToken = sir.getToken();
    }
}
