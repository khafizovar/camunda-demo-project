package camunda.poc.engine;

import camunda.poc.engine.idservice.CustomIdentityProviderFactory;
import camunda.poc.service.GService;
import camunda.poc.service.UService;
import gtm.caller.service.GtmAuthService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CustomIdentityProviderPlugin implements ProcessEnginePlugin {

    private final UService userService;

    private final GService groupService;

    private final GtmAuthService authService;

    @Autowired
    public CustomIdentityProviderPlugin(@Qualifier("UServiceGTM") UService userService,
                                        @Qualifier("GServiceGTM") GService groupService,
                                        @Qualifier("GTMAuthService") GtmAuthService authService) {
        this.userService = userService;
        this.groupService = groupService;
        this.authService = authService;
    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        CustomIdentityProviderFactory identityProviderFactory = new CustomIdentityProviderFactory(userService, groupService, authService);
        processEngineConfiguration.setIdentityProviderSessionFactory(identityProviderFactory);
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
