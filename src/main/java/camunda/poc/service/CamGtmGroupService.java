package camunda.poc.service;

import camunda.poc.domain.Group;
import gtm.caller.mapper.GroupFacade;
import gtm.caller.service.GtmRoleService;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
@Qualifier("GServiceGTM")
public class CamGtmGroupService implements GService {

    @Autowired
    private GtmRoleService gtmRoleService;

    @Autowired
    private GroupFacade groupFacade;


    public Optional<Group> findById(String id) {
        return this.getRoles().stream().filter(item -> item.getId().equals(id)).findFirst();
    }

    public Collection<Group> findAll() {
        return this.getRoles();
    }

    public Group save(Group group) {
        throw new NotImplementedException("read only mode!");
    }

    /**
     * Получить список групп
     * @return коллекция групп
     */
    private Collection<Group> getRoles() {
        return Arrays.stream(this.gtmRoleService.getRoles()).
                map(groupFacade::roleToGroup).
                collect(Collectors.toList());
    }
}
