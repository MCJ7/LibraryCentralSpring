/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.entities;

import java.time.LocalDate;
import java.time.Period;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author maxco
 */
@Entity
@Setter @Getter
@EntityListeners(AuditingEntityListener.class)
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean register;
    @Basic
    private LocalDate borrowDate;
    @Basic
    private LocalDate returnDate;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_isbn", referencedColumnName = "isbn")
    private Book Book;
    @Basic
    private Period period;
}
