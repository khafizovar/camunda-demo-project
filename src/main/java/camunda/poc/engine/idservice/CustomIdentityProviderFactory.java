package camunda.poc.engine.idservice;


import camunda.poc.service.GService;
import camunda.poc.service.UService;
import gtm.caller.service.GtmAuthService;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomIdentityProviderFactory implements SessionFactory {

    private final UService userService;
    private final GService groupService;

    private final GtmAuthService gtmAuthService;

    public CustomIdentityProviderFactory(UService userService,
                                         GService groupService,
                                         GtmAuthService authService) {
        this.userService = userService;
        this.groupService = groupService;
        this.gtmAuthService = authService;
    }

    @Override
    public Class<?> getSessionType() {
        return ReadOnlyIdentityProvider.class;
    }

    @Override
    public Session openSession() {
        return new CustomIdentityProvider(userService, groupService, gtmAuthService);
    }
}
