package camunda.poc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "`user1`")
@Setter @Getter
public class User implements org.camunda.bpm.engine.identity.User {

    @Id
//    @Column(columnDefinition = "char(36)")
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @ManyToOne
    private Group group;
}
