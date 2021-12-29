
package com.mcj.library.entities;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author maxco
 */
@Entity
@Setter @Getter
@EntityListeners(AuditingEntityListener.class)
public class Client{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long dni;
    private String fullName;
    private String mobileNumber;
    private Boolean register;

}
