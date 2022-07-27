package gtm.caller.service;

import gtm.caller.feign.GtmSignInFeignClient;
import gtm.caller.request.SignInRequest;
import gtm.caller.response.SignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by ai.khafizov
 * on 11.02.2022
 */
@Service
@Scope("singleton")
@Qualifier("GTMAuthService")
public class GtmAuthService {
    /*
     * jwt token активной сессии
     */
    private String jwtToken;
    /**
     * Имя пользователя технической учетной записи для
     */
    @Value("${gtm.user}")
    private String uname;
    /**
     * Пароль технической учетной записи
     */
    @Value("${gtm.pwd}")
    private String pwd;

    @Autowired
    private GtmSignInFeignClient gtmSignInFeignClient;

    /**
     * дата начала действия токена при авторизации
     */
    private Long beginDate;
    /**
     * время использования токена до обновления
     */
    private Long dateLive = Long.valueOf(25 * 60 * 1000);

    /**
     * Метод авторизации в rest контейнере
     */
    public synchronized void authorize() {
        this.jwtToken = this.authorize(uname, pwd).orElseThrow(() ->
                        new RuntimeException("failed to authorize in 'gtm-role' when requested"))
                .getToken();
        this.beginDate = System.currentTimeMillis();
    }

    public synchronized  Optional<SignInResponse> authorize(String username, String password) {
        return Optional.of(gtmSignInFeignClient.auth(new SignInRequest(username, password)));
    }

    /**
     * Получить jwt токен
     *
     * @return
     */
    public String getJwtToken() {
        if (jwtToken == null || (System.currentTimeMillis() - beginDate) > dateLive) {
            this.authorize();
        }
        return this.jwtToken;
    }
}
