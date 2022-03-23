package gtm.caller.service;

import gtm.caller.request.SignInRequest;
import gtm.caller.response.SignInResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by ai.khafizov
 * on 11.02.2022
 */
@Service
@Scope("singleton")
public class GtmAuthService {
    /*
     * jwt token активной сессии
     */
    private String jwtToken;

    private String uname = "admin";
    private String pwd = "Qwerty123";

    @Value("${gtm.auth.url}")
    private String authSignInUrl;

    private Long beginDate;
    private Long dateLive = Long.valueOf(25 * 60 * 1000);

    /**
     * Метод авторизации в rest контейнере
     */
    public void authorize() {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<SignInRequest> request = new HttpEntity<>(new SignInRequest(
            uname, pwd
        ));
        SignInResponse sir = restTemplate.postForObject(authSignInUrl, request, SignInResponse.class);
        this.jwtToken = sir.getToken().replace("Bearer ", "");
        this.beginDate =  System.currentTimeMillis();
    }

    /**
     * Получить jwt токен
     * @return
     */
    public String getJwtToken() {
        if(jwtToken == null) {
            this.authorize();
        } else if((System.currentTimeMillis() - beginDate) > dateLive) { //TODO POC просто зашили время жизни токена и презапрашиваем
            this.authorize();
        }
        return this.jwtToken;
    }
}
