package camunda.poc.service;

import camunda.poc.domain.User;
import gtm.caller.mapper.UserFacade;
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
@Qualifier("UServiceGTM")
public class CamGtmUserService implements UService {

    @Autowired
    private gtm.caller.service.GtmUserService gtmUserService;

    @Autowired
    private gtm.caller.service.GtmRoleService gtmRoleService;

    @Autowired
    private UserFacade userFacade;

    public Optional<User> findById(String id) {
        return Optional.of(userFacade.userDtoToUser(this.gtmUserService.getUser(id), gtmRoleService.getRolesByUserName(id)));
    }

    public Collection<User> findByGroupDn(String groupDn) {
        return Arrays.stream(this.gtmUserService.getUsersByGroupDn(groupDn)).map(userFacade::userDtoToUser).collect(Collectors.toList());
    }

    public Collection<User> findAll() {
        return this.getUsers();
    }

    public User save(User user) {
        throw new RuntimeException("You can not create users because this service implementation is in on read-only mode");
    }

    /**
     * Получить список групп
     * @return коллекция групп
     */
    private Collection<User> getUsers() {
        return Arrays.stream(this.gtmUserService.getUsers()).
                map(userFacade::userDtoToUser).
                collect(Collectors.toList());
    }
}