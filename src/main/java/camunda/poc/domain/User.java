package camunda.poc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter @Getter
public class User implements org.camunda.bpm.engine.identity.User {

    private String id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @ManyToOne
    private Group group;
}
