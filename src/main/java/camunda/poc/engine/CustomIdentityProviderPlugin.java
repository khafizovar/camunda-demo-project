package camunda.poc.engine;

import camunda.poc.engine.idservice.CustomIdentityProviderFactory;
import camunda.poc.service.GtmGroupService;
import camunda.poc.service.GtmUserService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class CustomIdentityProviderPlugin implements ProcessEnginePlugin {

    private final GtmUserService userService;

    private final GtmGroupService groupService;

    @Autowired
    public CustomIdentityProviderPlugin(@Qualifier("UServiceGTM") GtmUserService userService,
                                        @Qualifier("GServiceGTM") GtmGroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

        CustomIdentityProviderFactory identityProviderFactory = new CustomIdentityProviderFactory(userService, groupService);
        processEngineConfiguration.setIdentityProviderSessionFactory(identityProviderFactory);
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
