package com.mcj.library.entities;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.NumberFormat;

/**
 *
 * @author maxco
 */

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE book SET register = false WHERE isbn = ?")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    private Long isbn;
    @Column(unique = true, nullable = false)
    @NotEmpty
    private String title;
    private Integer year;
    private Integer copy;
    private Integer borrowingCopies;
    private Integer remainingCopies;
    private boolean register;
        @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate;
    @LastModifiedDate
    private LocalDateTime moficationDate;
    @OneToOne(cascade = CascadeType.PERSIST)
    @NotEmpty
    private Author author;
    @OneToOne(cascade = CascadeType.PERSIST)
    @NotEmpty
    private Editorial editorial;

    /**
     *
     * @param isbn
     * @param title
     * @param year
     * @param copy
     */
    public Book(Long isbn, String title, Integer year, Integer copy
            ) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.copy = copy;
        this.borrowingCopies = 0;
        this.remainingCopies = copy;
        this.register = true;
    }

    /**
     *
     * @param isbn
     * @param title
     * @param year
     * @param copy
     * @param register
     */
    public Book(Long isbn, String title, Integer year, Integer copy,
            boolean register) {
        this.isbn = isbn;
        this.title = title;
        this.year = year;
        this.copy = copy;
        this.borrowingCopies = 0;
        this.remainingCopies = 0;
        this.register = register;
    }

    /**
     *
     */
    public Book() {
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Book{isbn=").append(isbn);
        sb.append(", title=").append(title);
        sb.append(", year=").append(year);
        sb.append(", copy=").append(copy);
        sb.append(", borrowedCopy=").append(borrowingCopies);
        sb.append(", remainingCopy=").append(remainingCopies);
        sb.append(", register=").append(register);
        sb.append(", author=").append(author.getName());
        sb.append(", editorial=").append(editorial.getName());
        sb.append('}');
        return sb.toString();
    }
    

}
