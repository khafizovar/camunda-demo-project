package camunda.poc.service;

import camunda.poc.domain.User;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
public interface UService {
    Optional<User> findById(String id);

    Collection<User> findAll();

    User save(User user);
}