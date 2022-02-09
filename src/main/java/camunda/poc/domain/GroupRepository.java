package camunda.poc.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface GroupRepository extends CrudRepository<Group, String> {
    Optional<Group> findById(String id);
}
