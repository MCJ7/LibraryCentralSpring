/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.services;

import com.mcj.library.entities.Book;
import com.mcj.library.entities.Borrowing;
import com.mcj.library.entities.Client;
import com.mcj.library.exception.ExceptionPersonalized;
import com.mcj.library.repositories.BookRepository;
import com.mcj.library.repositories.ClientRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mcj.library.repositories.BorrowingRepository;

/**
 *
 * @author maxco
 */
@Service
public class BorrowingService {


    @Autowired
    private BorrowingRepository repository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ClientRepository clientRepository;    

    /**
     *
     * @param clientId
     * @param bookIsbn
     * @throws Exception
     */
    public void create( Long clientId, Long bookIsbn) throws Exception {
        Book book = bookRepository.findById(bookIsbn).get();
       if (book.getRemainingCopies()<=0) {
            throw new ExceptionPersonalized("Did not find the book to borrow");
        }
       
        Borrowing borrow = new Borrowing();
        book.setBorrowingCopies(book.getBorrowingCopies()+1);
        book.setRemainingCopies(book.getRemainingCopies()-1);
        bookRepository.save(book);
        borrow.setBook(book);
        borrow.setClient(clientRepository.findById(clientId).get());
        borrow.setBorrowDate(LocalDate.now());
        borrow.setReturnDate(LocalDate.now().plusDays(15));
        borrow.setPeriod(Period.between(LocalDate.now(), LocalDate.now().plusDays(15)));
        borrow.setRegister(Boolean.TRUE);
        System.out.println(borrow);
        repository.save(borrow);
    }
    
    /**
     *
     * @param id
     * @param clientId
     * @param bookIsbn
     * @param returnDate
     * @throws Exception
     */
    @Transactional
    public void modificar(Long id, Long clientId, Long bookIsbn, LocalDate returnDate) throws Exception {
       Borrowing borrow = repository.getById(id);
        validate(returnDate,borrow.getBorrowDate());
        Book b = bookRepository.findById(bookIsbn).get();
        if (b.getRemainingCopies()<=0) {
            throw new ExceptionPersonalized("Did not find the book to borrow");
        }
        Client c = clientRepository.findById(clientId).get();
        borrow.setBook(b);
        borrow.setClient(c);
        borrow.setReturnDate(returnDate);
        repository.save(borrow);
    }
    
    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Borrowing> borrowList() {
        return repository.findAll();
    }
    
    /**
     *
     * @param id
     */
    @Transactional
    public void delete(Long id) {

        repository.deleteById(id);
    }
    
    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public Borrowing searchId(Long id) {
        Optional<Borrowing> optionalBorrow = repository.findById(id);
        return optionalBorrow.orElse(null);
    }

    /**
     *
     * @param returnDate
     * @param borrowedDate
     * @throws ExceptionPersonalized
     */
    private void validate( LocalDate returnDate,LocalDate borrowedDate) throws ExceptionPersonalized {
        if (returnDate.isBefore(borrowedDate)) {
            throw new ExceptionPersonalized("Error Date");
        }
    }


    
}
