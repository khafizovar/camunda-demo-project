package camunda.poc.engine.checker;

import org.camunda.bpm.engine.ProcessEngineException;

/**
 * Created by ai.khafizov
 * on 28.02.2022
 */
public class AssigneeUnknownException extends ProcessEngineException {

    public AssigneeUnknownException(String assignee) {
        super("Assignee '" + assignee + "' is unknown to the identity service.");
    }

    private static final long serialVersionUID = 1L;

}
