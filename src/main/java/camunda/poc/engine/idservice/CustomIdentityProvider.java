package camunda.poc.engine.idservice;

import camunda.poc.service.GService;
import camunda.poc.service.UService;
import gtm.caller.response.SignInResponse;
import gtm.caller.service.GtmAuthService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomIdentityProvider implements ReadOnlyIdentityProvider {

    private UService userService;

    private GService groupService;

    @Autowired
    private GtmAuthService authService;

    public CustomIdentityProvider(UService userService, GService groupService, GtmAuthService authService) {
        this.userService = userService;
        this.groupService = groupService;
        this.authService = authService;
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
        if (query.getId() != null) {
            return new ArrayList<>(this.userService.findById(query.getId()).map(user -> {
                List<User> list = new ArrayList<>();
                list.add(user);
                return list;
            }).orElseGet(ArrayList::new));
        } else {
            Collection<camunda.poc.domain.User> users = this.userService.findAll();
            if (query.getGroupId() != null) {
                Optional<camunda.poc.domain.Group> gr = this.groupService.findById(query.getGroupId());
                if (gr.isPresent()) {
                    List<User> us = new ArrayList<>(this.userService.findByGroupDn(gr.get().getDn()));
                    if (query.getId() != null) {
                        us.removeIf(user -> !user.getId().equals(query.getId()));
                    }
                    return us;
                } else {
                    return new ArrayList<>();
                }
            }
            if (query.getId() != null)
                users.removeIf(user -> !user.getId().equals(query.getId()));
            if (query.getFirstName() != null)
                users.removeIf(user -> !user.getFirstName().equals(query.getFirstName()));
            if (query.getLastName() != null)
                users.removeIf(user -> !user.getLastName().equals(query.getLastName()));
            if (query.getEmail() != null)
                users.removeIf(user -> !user.getEmail().equals(query.getEmail()));

            return new ArrayList<>(users);
        }
    }

    @Override
    public boolean checkPassword(String userId, String password) {

        if (userId == null || password == null || userId.isEmpty() || password.isEmpty())
            return false;

        Optional<SignInResponse> resp =  this.authService.authorize(userId, password);
        return resp.isPresent() && resp.get().getToken() != null;
    }

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

        if (query.getUserId() != null) {
            return new ArrayList<>(groupService.findAllByUserId(query.getUserId()));
        }
        return groupService.findAll().stream()
                .filter(group -> query.getId() == null || group.getId().equals(query.getId()))
                .filter(group -> query.getName() == null || group.getName().equals(query.getName()))
                .filter(group -> query.getType() == null || group.getType().equals(query.getType()))
                .collect(Collectors.toList());
    }

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
        //Работы с БД нет, поставщик gtm-role
    }

    @Override
    public void close() {
        //Работы с БД нет, поставщик gtm-role
    }
}
