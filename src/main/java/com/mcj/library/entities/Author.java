/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.entities;

import java.time.LocalDateTime;
import org.hibernate.annotations.SQLDelete;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Setter
@Getter
@SQLDelete(sql = "UPDATE author SET register = false WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String name;
    private boolean register;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;
    @LastModifiedDate
    private LocalDateTime moficationDate;

    /**
     *
     * @param name
     */
    public Author(String name) {
        this.id = 0;
        this.name = name;
        this.register = true;
    }

    /**
     *
     */
    public Author() {
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Author{id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", register=").append(register);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", moficationDate=").append(moficationDate);
        sb.append('}');
        return sb.toString();
    }
    

}
