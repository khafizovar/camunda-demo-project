package gtm.caller.service;

import camunda.poc.domain.Group;
import gtm.caller.dto.RoleDto;
import gtm.caller.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
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
     * Получить список пользователей
     * @return Список пользователей
     */
    public UserDto[] getUsers() {
        return (UserDto[]) this.makeRequest(usersContainerUrl, HttpMethod.GET, UserDto[].class).getBody();
    }

    /**
     * Получить пользователя по userName
     * @param userName логин пользователя
     * @return Объект пользователя
     */
    public UserDto getUser(String userName) {
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(usersContainerUrl)
                .queryParam("userName", userName)
                .encode()
                .toUriString();
        UserDto[] list = (UserDto[]) this.makeRequest(urlTemplate, HttpMethod.GET, UserDto[].class).getBody();
        return (list != null && list.length > 0) ? list[0] : null;
    }

    /**
     * Получить пользователя по userName
     * @param groupDn dn группы
     * @return Объект пользователя
     */
    public UserDto[] getUsersByGroupDn(String groupDn) {
        try {
//            String urlTemplate = UriComponentsBuilder.fromHttpUrl(usersContainerUrl)
//                    .queryParam("groupName", URLEncoder.encode(groupDn, "UTF-8"))
//                    .toUriString();
//            String urlTemplate = UriComponentsBuilder.fromHttpUrl(usersContainerUrl).queryParam("groupName", URLEncoder.encode(groupDn, "UTF-8")).build(true).toUri().toString();
            String urlTemplate = UriComponentsBuilder.
                    fromHttpUrl(usersContainerUrl).
                    queryParam("groupName", URLEncoder.encode(groupDn, "UTF-8")).
                    build(true).toUri().toString();
            return (UserDto[]) this.makeRequest(urlTemplate, HttpMethod.GET, UserDto[].class).getBody();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return new UserDto[0];
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
