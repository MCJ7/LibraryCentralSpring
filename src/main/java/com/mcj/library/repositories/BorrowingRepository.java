/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.repositories;

import com.mcj.library.entities.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author maxco
 */
@Repository
public interface BorrowingRepository  extends JpaRepository<Borrowing, Long>{
}
