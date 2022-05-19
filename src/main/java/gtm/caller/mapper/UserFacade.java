package gtm.caller.mapper;

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
        gr.setId(userDto.getUsername());
        gr.setFirstName(userDto.getUsername());
        gr.setEmail(userDto.getEmail());
        gr.setPassword(userDto.getPassword());
        return gr;
    }
}

