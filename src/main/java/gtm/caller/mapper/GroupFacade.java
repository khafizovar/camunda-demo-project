package gtm.caller.mapper;

import camunda.poc.domain.Group;
import gtm.caller.dto.RoleDto;
import org.springframework.stereotype.Service;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Service
public class GroupFacade {
    /**
     * Маппинг
     * @param roleDto
     * @return
     */
    public Group roleToGroup(RoleDto roleDto) {
        Group gr = new Group();
        gr.setId(roleDto.getGroupDn());
        gr.setName(roleDto.getName());
        gr.setType("SYSTEM");
        gr.setDn(roleDto.getGroupDn());
        return gr;
    }
}
