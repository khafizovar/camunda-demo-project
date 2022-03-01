package camunda.poc.engine.checker;


import java.util.logging.Logger;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.identity.UserQuery;
import org.camunda.bpm.engine.task.IdentityLink;
import org.springframework.stereotype.Component;

/**
 * Created by ai.khafizov
 * on 18.02.2022
 */
@Component
public class AssigneeCheckerTaskListener implements TaskListener {

    private final Logger log = Logger.getLogger(AssigneeCheckerTaskListener.class.getName());

    @Override
    public void notify(DelegateTask task) {
        log.info("Event '" + task.getEventName() + "' received by Task Listener for Task:"
                + " activityId=" + task.getTaskDefinitionKey()
                + ", name='" + task.getName() + "'"
                + ", taskId=" + task.getId()
                + ", assignee='" + task.getAssignee() + "'"
                + ", candidateGroups='" + task.getCandidates() + "'");
        String assignee = task.getAssignee();
        //Если кандидаты не назначены - то разрешаем клаймить
        if (task.getCandidates().stream().count() == 0 ) {
            throw new AssigneeEmptyException(task);
        }
        if (assignee != null) {
            IdentityService identityService = task.getProcessEngineServices().getIdentityService();
            UserQuery userQuery = identityService.createUserQuery().userId(assignee);
            if (userQuery.count() == 0) {
                throw new AssigneeUnknownException(assignee);
            }
            for (IdentityLink candidate : task.getCandidates()) {
                if (assignee.equals(candidate.getUserId())) {
                    return;
                } else if (candidate.getGroupId() != null
                        && userQuery.memberOfGroup(candidate.getGroupId()).count() > 0) {
                    return;
                }
            }
            throw new AssigneeNotInCandidatesException(task);
        }
    }

}