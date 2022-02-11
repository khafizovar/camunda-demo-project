package gtm.caller.service;

import gtm.caller.dto.RoleDto;
import gtm.caller.request.SignInRequest;
import gtm.caller.response.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
public class GtmRoleService {

    @Autowired
    private GtmAuthService authService;

    private String rolesContainerUrl = "http://localhost:8080/api/v1/roles/";

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
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(authService.getJwtToken());
        HttpEntity<Map<String, String>> request = new HttpEntity(headers);
        ResponseEntity<?> response = restTemplate.exchange(url, HttpMethod.GET, request, clazz);
        return response;
    }
}
