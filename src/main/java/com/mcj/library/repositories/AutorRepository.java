/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.repositories;

import com.mcj.library.entities.Author;
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
public interface AutorRepository extends JpaRepository<Author, Integer>{

    /**
     *
     * @param id
     */
    @Modifying
    @Query("UPDATE Author a SET a.register = true WHERE a.id = :id")
    public void enable(@Param("id")Integer id);
    
    /**
     *
     * @param id
     * @param nam
     */
    @Modifying
    @Query("UPDATE Author a SET a.name = :nam WHERE a.id = :id")
    public void update(@Param("id")Integer id,@Param("nam")String nam);
}