package camunda.poc.service;

import camunda.poc.domain.Group;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
public interface GService {
    Optional<Group> findById(String id);

    Collection<Group> findAll();

    Group save(Group group);
}
