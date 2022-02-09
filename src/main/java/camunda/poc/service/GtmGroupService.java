package camunda.poc.service;

import camunda.poc.domain.Group;
import camunda.poc.domain.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;


@Service
@Transactional
@Qualifier("GServiceGTM")
public class GtmGroupService implements GService {

    @Autowired
    private GroupRepository repository;

    public Optional<Group> findById(String id) {
        return repository.findById(id);
    }

    public Collection<Group> findAll() {
        return (Collection<Group>) repository.findAll();
    }

    public Group save(Group group) {
        return repository.save(group);
    }
}
