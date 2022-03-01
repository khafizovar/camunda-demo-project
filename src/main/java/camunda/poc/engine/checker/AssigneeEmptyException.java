package camunda.poc.engine.checker;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateTask;
/**
 * Created by ai.khafizov
 * on 01.03.2022
 */
public class AssigneeEmptyException extends ProcessEngineException {

    public AssigneeEmptyException(DelegateTask task) {
        super("Assignee is empty for user task:"
                + " activityId=" + task.getTaskDefinitionKey()
                + ", name='" + task.getName() + "'"
                + ", taskId=" + task.getId()
                + ", candidates='" + task.getCandidates() + "'");
    }

    private static final long serialVersionUID = 1L;
}
