/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.repositories;

import com.mcj.library.entities.Author;
import com.mcj.library.entities.Book;
import com.mcj.library.entities.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    /**
     *
     * @param isbn
     */
    @Modifying
    @Query("UPDATE Book b SET b.register = true WHERE b.isbn = :isbn")
    public void enable(@Param("isbn")Long isbn);

    /**
     *
     * @param isbn
     * @param title
     * @param year
     * @param copy
     * @param author
     * @param editorial
     */
    @Modifying
    @Query("UPDATE Book b SET b.title = :title, b.year = :year, b.copy = :copy, b.author = :author_id, b.editorial = :editorial_id"
            + " WHERE b.isbn = :isbn")
    public void update(@Param("isbn")Long isbn, @Param("title")String title, @Param("year")Integer year, @Param("copy")Integer copy,@Param("author_id") Author author,@Param("editorial_id") Editorial editorial);

   
    
}
