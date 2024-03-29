package camunda.poc.engine.idservice;

import camunda.poc.service.GService;
import camunda.poc.service.GroupService;
import camunda.poc.service.UService;
import camunda.poc.service.UserService;
import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.*;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomIdentityProvider implements ReadOnlyIdentityProvider {

    private UService userService;

    private GService groupService;

    @Autowired
    public CustomIdentityProvider(UService userService, GService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    // User ////////////////////////////////////////////

    @Override
    public User findUserById(String userId) {
        return userService.findById(userId).orElseGet(null);
    }

    @Override
    public UserQuery createUserQuery() {
        return new CustomUserQuery(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
    }

    @Override
    public UserQuery createUserQuery(CommandContext commandContext) {
        return new CustomUserQuery();
    }

    @Override
    public NativeUserQuery createNativeUserQuery() {
        throw new BadUserRequestException("not supported");
    }

    public long findUserCountByQueryCriteria(CustomUserQuery query) {
        return findUserByQueryCriteria(query).size();
    }

    public List<User> findUserByQueryCriteria(CustomUserQuery query) {

        Collection<camunda.poc.domain.User> users = userService.findAll();

        if (query.getId() != null)
            users.removeIf(user -> !user.getId().equals(query.getId()));
        if (query.getFirstName() != null)
            users.removeIf(user -> !user.getFirstName().equals(query.getFirstName()));
        if (query.getLastName() != null)
            users.removeIf(user -> !user.getLastName().equals(query.getLastName()));
        if (query.getEmail() != null)
            users.removeIf(user -> !user.getEmail().equals(query.getEmail()));
        if (query.getGroupId() != null)
            users.removeIf(user -> !user.getGroup().getId().equals(query.getGroupId()));

        return new ArrayList<>(users);

//        return userService.findAll().stream()
//                .filter(user -> user.getId().equals(query.getId()))
//                .filter(user -> user.getFirstName().equals(query.getFirstName()))
//                .filter(user -> user.getLastName().equals(query.getLastName()))
//                .filter(user -> user.getEmail().equals(query.getEmail()))
//                .filter(user -> user.getGroup().getId().equals(query.getGroupId()))
//                .collect(Collectors.toList());

//        return Collections.emptyList();
    }

    @Override
    public boolean checkPassword(String userId, String password) {

        if (userId == null || password == null || userId.isEmpty() || password.isEmpty())
            return false;

        User user = findUserById(userId);

        if (user == null)
            return false;

        return user.getPassword().equals(password);
    }

    // Group //////////////////////////////////////////

    @Override
    public Group findGroupById(String groupId) {
        return groupService.findById(groupId).orElse(null);
    }

    @Override
    public GroupQuery createGroupQuery() {
        return new CustomGroupQuery(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
    }

    @Override
    public GroupQuery createGroupQuery(CommandContext commandContext) {
        return new CustomGroupQuery();
    }

    public long findGroupCountByQueryCriteria(CustomGroupQuery query) {
        return findGroupByQueryCriteria(query).size();
    }

    public List<Group> findGroupByQueryCriteria(CustomGroupQuery query) {

        return groupService.findAll().stream()
                .filter(group -> query.getId() == null || group.getId().equals(query.getId()))
                .filter(group -> query.getName() == null || group.getName().equals(query.getName()))
                .filter(group -> query.getType() == null || group.getType().equals(query.getType()))
                .collect(Collectors.toList());

//        return Collections.emptyList();
    }

    // Tenant ////////////////////////////////////////

    @Override
    public Tenant findTenantById(String tenantId) {
        return null;
    }

    @Override
    public TenantQuery createTenantQuery() {
        return new CustomTenantQuery(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
    }

    @Override
    public TenantQuery createTenantQuery(CommandContext commandContext) {
        return new CustomTenantQuery();
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }
}
