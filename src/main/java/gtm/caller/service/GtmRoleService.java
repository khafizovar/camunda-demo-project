package gtm.caller.service;

import gtm.caller.dto.RoleDto;
import gtm.caller.request.SignInRequest;
import gtm.caller.response.SignInResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Service
@Scope("singleton")
public class GtmRoleService {
    /**
     * jwt token активной сессии
     */
    private String jwtToken;

    private String uname = "admin";
    private String pwd = "Qwerty123";

    private String authSignInUrl = "http://localhost:8080/api/auth/signin";
    private String rolesContainerUrl = "http://localhost:8080/api/v1/roles/";


    /**
     * Метод авторизации в rest контейнере
     */
    private void authorize() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SignInRequest> request = new HttpEntity<>(new SignInRequest(
                uname, pwd
        ));
        SignInResponse sir = restTemplate.postForObject(authSignInUrl, request, SignInResponse.class);
        this.jwtToken = sir.getToken().replace("Bearer ", "");
    }

    /**
     * Получить список ролей
     *
     * @return
     */
    public RoleDto[] getRoles() {
        return (RoleDto[]) this.makeRequest(rolesContainerUrl, HttpMethod.GET, RoleDto[].class).getBody();
    }

    /**
     * Метод формирования запроса.
     * TODO на момент реализации POC без дженериков
     *
     * @param url
     * @param method
     * @param clazz
     * @return
     */
    private ResponseEntity<?> makeRequest(String url, HttpMethod method, Class clazz) {
        if (this.jwtToken == null) {
            this.authorize();
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(this.jwtToken);

        HttpEntity<Map<String, String>> request = new HttpEntity(headers);

        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, clazz);

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            this.authorize();
            response = restTemplate.exchange(url, HttpMethod.GET, request, clazz);
        }
        return response;

    }
}
