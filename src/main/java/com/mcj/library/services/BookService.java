/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.services;

import com.mcj.library.entities.Author;
import com.mcj.library.entities.Book;
import com.mcj.library.entities.Editorial;
import com.mcj.library.exception.ExceptionPersonalized;
import com.mcj.library.repositories.AutorRepository;
import com.mcj.library.repositories.EditorialRepository;
import com.mcj.library.repositories.BookRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author maxco
 */
@Service
public class BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    private AutorRepository authorRepository;
    @Autowired
    private EditorialRepository editorialRepository;

    /**
     *
     * @param book
     * @param author
     * @param editorial
     * @throws Exception
     */
    public void create(Book book, Integer author, Integer editorial) throws Exception {
        validate(book, author, editorial);

        book.setAuthor(authorRepository.findById(author).orElse(null));
        book.setEditorial(editorialRepository.findById(editorial).orElse(null));
        repository.save(book);
        
    }

    /**
     *
     * @param isbn
     * @param title
     * @param year
     * @param copy
     * @param author
     * @param editorial
     * @param borrowedCopies
     * @param remainingCopies
     * @throws Exception
     */
    @Transactional
    public void modify(Long isbn, String title,
        Integer year, Integer copy, Integer author, Integer editorial,Integer borrowingCopies,Integer remainingCopies) throws Exception {
        Author a = authorRepository.findById(author).get();
        Editorial e = editorialRepository.findById(editorial).get();
        Book b = repository.findById(isbn).get();
        b.setAuthor(a);
        if(copy<(borrowingCopies+remainingCopies)){
            throw new ExceptionPersonalized("El números de copias es erroneo");
        }
        b.setBorrowingCopies(borrowingCopies);
        b.setCopy(copy);
        b.setEditorial(e);
        b.setRemainingCopies(remainingCopies);
        b.setTitle(title);
        b.setYear(year);
        validate(b, author, editorial);
        repository.save(b);
        
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Book> bookList() {
        return repository.findAll();
    }

    /**
     *
     * @param isbn
     */
    @Transactional
    public void delete(Long isbn) {
        repository.deleteById(isbn);
    }

    /**
     *
     * @param isbn
     * @return
     */
    @Transactional(readOnly = false)
    public Book searchId(Long isbn) {
        Optional<Book> optionalBook = repository.findById(isbn);
        return optionalBook.orElse(null);
    }

    /**
     *
     * @param isbn
     */
    @Transactional
    public void enable(Long isbn) {
        
        repository.enable(isbn);
    }

    /**
     *
     * @param book
     * @param author
     * @param editorial
     * @throws Exception
     */
    public void validate(Book book, Integer author, Integer editorial) throws Exception{
        if (book.getIsbn() == null || book.getIsbn() < 1) {
            throw new Exception("Error ISBN");
        }
        if (book.getCopy() < 0) {
            throw new Exception("Error copy");
        }
        if (book.getYear() == null || book.getYear() < 1) {
            throw new Exception("Error year");
        }
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new Exception("Error title");
        }
    }

    /**
     *
     * @param book
     */
    public void modifyStock(Book book) {
        book.setBorrowingCopies(book.getBorrowingCopies()-1);
        book.setRemainingCopies(book.getRemainingCopies()+1);
        repository.save(book);
    }
}
