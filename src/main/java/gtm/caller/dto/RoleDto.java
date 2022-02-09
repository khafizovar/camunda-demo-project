package gtm.caller.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by ai.khafizov
 * on 09.02.2022
 */
@Data
public class RoleDto {
    /**
     * Идентификатор записи
     */
    private Long id;
    /**
     * Наименование
     */
    private String name;
    /**
     * Описание
     */
    private String description;
    /**
     * ldap distinguished name
     */
    private String groupDn;
    /**
     * Дата создания
     */
    private LocalDateTime createdDate;
    /**
     * Идентификаторый прав
     */
    private Set<Long> rightsIds;
    /**
     * Идентификаторы взаимоисключающих ролей
     */
    private Set<Long> mutuallyExcludeRolesIds;
}
