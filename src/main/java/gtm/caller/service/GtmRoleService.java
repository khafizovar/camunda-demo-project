package gtm.caller.service;

import gtm.caller.dto.RoleDto;
import gtm.caller.feign.GtmRoleFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Service
@Slf4j
public class GtmRoleService {

    @Autowired
    private GtmAuthService authService;

    @Autowired
    private GtmRoleFeignClient gtmRoleFeignClient;

    /**
     * Получить список ролей
     * @return
     */
    public RoleDto[] getRoles() {
        return this.gtmRoleFeignClient.getRoles(this.authService.getJwtToken());
    }

    /**
     * Получить список ролей пользователя
     * @return получить список ролей пользователя
     */
    public RoleDto[] getRolesByUserName(String userId) {
        return this.gtmRoleFeignClient.searchUserRoles(this.authService.getJwtToken(), userId);
    }
}
