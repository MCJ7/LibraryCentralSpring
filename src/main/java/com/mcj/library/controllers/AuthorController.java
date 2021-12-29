package com.mcj.library.controllers;

import com.mcj.library.entities.Author;
import com.mcj.library.services.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

    /**
     *
     */
    @Autowired
    private AuthorService service;

    /**
     *
     * @param request
     * @return
     * @throws InterruptedException
     */
    @GetMapping("")
    public ModelAndView showAuhtors(HttpServletRequest request) throws InterruptedException {
        ModelAndView mav = new ModelAndView("author");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("authors", service.authorsList());
        mav.addObject("emptyList", "Empty data base");
        mav.addObject("title", "List of Authors");
        return mav;

    }

    /**
     *
     * @return
     */
    @GetMapping("/create")
    public ModelAndView createEditorial() {
        ModelAndView mav = new ModelAndView("author-form");
        mav.addObject("author", new Author());
        mav.addObject("title", "Create author");
        mav.addObject("action", "save");
        return mav;
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/edit/{id}")
    public ModelAndView editEditorial(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("author-form");
        mav.addObject("author", service.serchAuthor(id));
        mav.addObject("title", "Edit author");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @param name
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saving(@RequestParam String name, RedirectAttributes attributes) throws Exception {
        try {
            service.crearAutor(new Author(name));
            attributes.addFlashAttribute("exito-name", "Successful delete");
            return new RedirectView("/authors");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/authors");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            service.enable(id);
            attributes.addFlashAttribute("exito-name", "Successful updated");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/authors");
    }

    /**
     *
     * @param id
     * @param name
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modify(@RequestParam Integer id, @RequestParam String name,
            RedirectAttributes attributes) throws Exception {
        try {
            service.modify(id, name);
            attributes.addFlashAttribute("exito-name", "Successful updated");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/authors");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id, RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute("exito-name", "Successful updated");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/authors");
    }

}
