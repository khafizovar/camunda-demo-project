package camunda.poc.service;

import camunda.poc.domain.User;
import camunda.poc.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Qualifier("UServiceDB")
public class UserService implements UService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> findById(String id) {
        return repository.findById(id);
    }

    public Collection<User> findAll() {
        return (Collection<User>) repository.findAll();
    }

    public User save(User user) {
        return repository.save(user);
    }
}
