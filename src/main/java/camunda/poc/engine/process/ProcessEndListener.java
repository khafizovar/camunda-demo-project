package camunda.poc.engine.process;

import org.camunda.bpm.engine.delegate.BaseDelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateListener;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.camunda.bpm.engine.impl.util.xml.Element;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Плагин обрабатывающий событие окончания бизнес-процесса
 * Created by ai.khafizov
 * on 28.07.2022
 */
@Component
public class ProcessEndListener extends AbstractBpmnParseListener implements BpmnParseListener {
    /** Единый список слушателей окончания выполнения процесса */
    private static Map<String, ProcessEndListenerCallback> listeners = Collections.synchronizedMap(new HashMap<>());
    /** Метод регистрации слушателя */
    public static void registerCallBack(String processId, ProcessEndListenerCallback listener) {
        listeners.put(processId, listener);
    }

    @Override
    public void parseProcess(Element processElement, ProcessDefinitionEntity processDefinition) {
        processDefinition.addListener("end", new DelegateListener<BaseDelegateExecution>() {
            @Override
            public void notify(BaseDelegateExecution baseDelegateExecution) throws Exception {
                if(listeners.get(baseDelegateExecution.getId()) != null) {
                    listeners.get(baseDelegateExecution.getId()).onActionSuccess(baseDelegateExecution.getVariables());
                    listeners.remove(processDefinition.getId());
                }
            }
        });
        super.parseProcess(processElement, processDefinition);
    }
}