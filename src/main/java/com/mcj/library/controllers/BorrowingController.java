/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.controllers;

import com.mcj.library.entities.Book;
import com.mcj.library.entities.Borrowing;
import com.mcj.library.entities.Editorial;
import com.mcj.library.services.BookService;
import com.mcj.library.services.BorrowingService;
import com.mcj.library.services.ClientService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("/borrowings")
public class BorrowingController {
    @Autowired
    private BorrowingService service;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BookService bookService;
    
    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ModelAndView editorialList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("borrow");
        List<Borrowing> borrows = service.borrowList();
        mav.addObject("borrows", borrows);
        mav.addObject("title", "List of Borrowings");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        
        return mav;
    }

    /**
     *
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("borrow-form");
        mav.addObject("borrow", new Borrowing());
        mav.addObject("books", bookService.bookList());
        mav.addObject("clients", clientService.clientList());
        mav.addObject("title", "Create borrowing");
        mav.addObject("action", "save");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editEditorial(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("borrow-form");
        mav.addObject("borrow", service.searchId(id));
        mav.addObject("books", bookService.bookList());
        mav.addObject("clients", clientService.clientList());
        mav.addObject("title", "Edit borrowing");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @param client
     * @param book
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saving(@RequestParam Long client,@RequestParam Long book, RedirectAttributes attributes) throws Exception {
        try {
            service.create(client, book);
            attributes.addFlashAttribute("exito-name", "Successful record");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/borrowings");
    }

    /**
     *
     * @param id
     * @param client
     * @param book
     * @param returnDate
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modify(@RequestParam Long id,@RequestParam Long client,@RequestParam Long book,@RequestParam LocalDate returnDate, RedirectAttributes attributes) throws Exception {
        try {
          
        service.modificar(id,client, book,returnDate);
         attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/borrowings");
    }

    /**
     *
     * @param id
     * @param book
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{id}/{book}")
    public RedirectView delete(@PathVariable Long id,@PathVariable Book book, RedirectAttributes attributes) {
        try {
            service.delete(id);
            bookService.modifyStock(book);
            attributes.addFlashAttribute("exito-name", "Successful return");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/borrowings");
    }
    
}
