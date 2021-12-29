/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.repositories;

import com.mcj.library.entities.Client;
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
public interface ClientRepository extends JpaRepository<Client, Long>{

    /**
     *
     * @param id
     */
    @Modifying
    @Query("UPDATE Client c SET c.register = true WHERE c.id = :id")
    public void enable(@Param("id")Long id);

    /**
     *
     * @param id
     */
    @Modifying
    @Query("UPDATE Client c SET c.register = false WHERE c.id = :id")
    public void unable(@Param("id")Long id);

    /**
     *
     * @param id
     * @param dni
     * @param fullName
     * @param mobileNumber
     */
    @Modifying
    @Query("UPDATE Client c SET c.dni = :dni, c.fullName = :fullName, c.mobileNumber = :mobileNumber WHERE c.id = :id")
    public void update(@Param("id")Long id, @Param("dni")Long dni, @Param("fullName")String fullName, @Param("mobileNumber")String mobileNumber);

}
