package camunda.poc.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
public class Group implements org.camunda.bpm.engine.identity.Group {
    /**
     * Идентификатор пользователя
     */
    private String id;
    /**
     * Наименование группы
     */
    private String name;
    /**
     * Тип группы (н-р System)
     */
    private String type;
    /**
     * dn группы
     */
    private String dn;
}
