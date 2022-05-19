package gtm.caller.service;

import gtm.caller.dto.UserDto;
import gtm.caller.feign.GtmUsersFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

/**
 * Created by ai.khafizov
 * on 11.02.2022
 */
@Slf4j
@Service
public class GtmUserService {
    @Autowired
    private GtmAuthService authService;
    @Autowired
    private GtmUsersFeignClient gtmUsersFeignClient;

    /**
     * Получить список пользователей
     * @return Список пользователей
     */
    public UserDto[] getUsers() {
        return this.gtmUsersFeignClient.getUsers(authService.getJwtToken());
    }

    /**
     * Получить пользователя по userName
     * @param userName логин пользователя
     * @return Объект пользователя
     */
    public UserDto getUser(String userName) {
        UserDto[] list = this.gtmUsersFeignClient.searchUsersByName(authService.getJwtToken(), userName);
        return (list != null && list.length > 0) ? list[0] : null;
    }

    /**
     * Получить пользователя по userName
     * @param groupDn dn группы
     * @return Объект пользователя
     */
    public UserDto[] getUsersByGroupDn(String groupDn) {
        try {
            return this.gtmUsersFeignClient.searchUsersByGroupDn(authService.getJwtToken(), URLEncoder.encode(groupDn, "UTF-8"));
        } catch (Exception ex) {
            log.error("Ошибка вызова сервисов gtm-role по работе с пользователями", ex);
        }
        return new UserDto[0];
    }
}
