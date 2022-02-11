package gtm.caller.service;

import camunda.poc.domain.Group;
import gtm.caller.dto.RoleDto;
import gtm.caller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ai.khafizov
 * on 11.02.2022
 */
@Service
public class GtmUserService {
    @Autowired
    private GtmAuthService authService;

    private String usersContainerUrl = "http://localhost:8080/api/v1/ldap/users";

    /**
     * Получить список ролей
     *
     * @return
     */
    public UserDto[] getUsers() {
        return (UserDto[]) this.makeRequest(usersContainerUrl, HttpMethod.GET, UserDto[].class).getBody();
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
