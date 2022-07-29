package camunda.poc.engine.process;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс-плагин для регистрации слушателя окончания выполнения процесса
 * Created by ai.khafizov
 * on 28.07.2022
 */
@Component
public class ProcessEndListenerPlugin implements ProcessEnginePlugin {

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        List<BpmnParseListener> postParseListeners = processEngineConfiguration.getCustomPostBPMNParseListeners();
        if (postParseListeners == null) {
            postParseListeners = new ArrayList<>();
            processEngineConfiguration.setCustomPostBPMNParseListeners(postParseListeners);
        }
        postParseListeners.add(new ProcessEndListener());
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {
    }
}