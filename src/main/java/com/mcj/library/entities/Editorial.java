/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author maxco
 */
@Entity(name = "Editorial")
@Getter
@Setter
@SQLDelete(sql = "UPDATE editorial SET register = false WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(updatable = false)
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
    public Editorial(String name) {
        this.id = 0;
        this.name = name;
        this.register = true;
    }
    public Editorial() {
    }
}
