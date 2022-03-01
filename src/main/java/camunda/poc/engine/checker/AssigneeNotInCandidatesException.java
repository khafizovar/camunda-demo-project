package camunda.poc.engine.checker;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateTask;

/**
 * Created by ai.khafizov
 * on 28.02.2022
 */
public class AssigneeNotInCandidatesException extends ProcessEngineException {

    public AssigneeNotInCandidatesException(DelegateTask task) {
        super("Assignee '" + task.getAssignee() + "' is neither a candidate user nor member of any candidate group for user task:"
                + " activityId=" + task.getTaskDefinitionKey()
                + ", name='" + task.getName() + "'"
                + ", taskId=" + task.getId()
                + ", candidates='" + task.getCandidates() + "'");
    }

    private static final long serialVersionUID = 1L;
}
