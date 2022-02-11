package gtm.caller.mapper;

import camunda.poc.domain.Group;
import camunda.poc.domain.User;
import gtm.caller.dto.RoleDto;
import gtm.caller.dto.UserDto;
import org.springframework.stereotype.Service;

/**
 * Created by ai.khafizov
 * on 11.02.2022
 */
@Service
public class UserFacade {
    /**
     * Маппинг
     * @param userDto
     * @return
     */
    public User userDtoToUser(UserDto userDto) {
        User gr = new User();
        gr.setId(userDto.getSAMAccountName());
        gr.setEmail(userDto.getEmail());
        gr.setPassword("Qwerty123");
        return gr;
    }
}
