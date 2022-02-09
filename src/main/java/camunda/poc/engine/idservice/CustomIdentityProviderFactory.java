package camunda.poc.engine.idservice;


import camunda.poc.service.GService;
import camunda.poc.service.GroupService;
import camunda.poc.service.UService;
import camunda.poc.service.UserService;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomIdentityProviderFactory implements SessionFactory {

    private final UService userService;

    private final GService groupService;

    @Autowired
    public CustomIdentityProviderFactory(UService userService, GService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

//    @Autowired
//    private CustomIdentityProvider customIdentityProvider;

    @Override
    public Class<?> getSessionType() {
        return ReadOnlyIdentityProvider.class;
    }

    @Override
    public Session openSession() {
        return new CustomIdentityProvider(userService, groupService);
    }
}
