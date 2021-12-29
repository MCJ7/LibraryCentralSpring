package com.mcj.library.controllers;

import com.mcj.library.entities.Book;
import com.mcj.library.services.AuthorService;
import com.mcj.library.services.BookService;
import com.mcj.library.services.EditorialService;
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

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService service;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private EditorialService editorialService;

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ModelAndView bookList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("book");
        mav.addObject("title", "List of Books");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("emptyList", "Empty data base");
        mav.addObject("books", service.bookList());
        return mav;
    }

    /**
     *
     * @param isbn
     * @return
     */
    @GetMapping("/edit/{isbn}")
    public ModelAndView editBook(@PathVariable Long isbn) {
        ModelAndView mav = new ModelAndView("book-form");
        mav.addObject("book", service.searchId(isbn));
        mav.addObject("editorials", editorialService.editorialList());
        mav.addObject("authors", authorService.authorsList());
        mav.addObject("title", "Edit book");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @return
     */
    @GetMapping("/create")
    public ModelAndView createBook() {
        ModelAndView mav = new ModelAndView("book");
        mav.addObject("book", new Book());
        mav.addObject("editorials", editorialService.editorialList());
        mav.addObject("authors", authorService.authorsList());
        mav.addObject("title", "Create book");
        mav.addObject("action", "save");
        return mav;
    }

    /**
     *
     * @param isbn
     * @param title
     * @param year
     * @param copy
     * @param author
     * @param editorial
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saving(@RequestParam Long isbn, @RequestParam String title,
            @RequestParam Integer year, @RequestParam Integer copy,
            @RequestParam Integer author, @RequestParam Integer editorial,
            
            RedirectAttributes attributes) throws Exception {
        try {
            service.create(new Book(isbn, title, year, copy), author, editorial);
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/books");
    }

    /**
     *
     * @param isbn
     * @param title
     * @param year
     * @param copy
     * @param author
     * @param editorial
     * @param borrowedCopy
     * @param remainingCopy
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modify(@RequestParam Long isbn, @RequestParam String title,
            @RequestParam Integer year, @RequestParam Integer copy, @RequestParam Integer author,
            @RequestParam Integer editorial,@RequestParam Integer borrowingCopies,@RequestParam Integer remainingCopies, RedirectAttributes attributes) throws Exception {
        try {
            service.modify(isbn, title, year, copy, author, editorial,borrowingCopies,remainingCopies);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/books");
    }

    /**
     *
     * @param isbn
     * @param attributes
     * @return
     */
    @PostMapping("/enable/{isbn}")
    public RedirectView enable(@PathVariable Long isbn, RedirectAttributes attributes) {
        try {
            service.enable(isbn);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/books");
    }

    /**
     *
     * @param isbn
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{isbn}")
    public RedirectView delete(@PathVariable Long isbn, RedirectAttributes attributes) {
        try {
            service.delete(isbn);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/books");
    }
}
