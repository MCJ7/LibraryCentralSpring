package com.mcj.library.controllers;

import com.mcj.library.entities.Editorial;
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
@RequestMapping("/editorials")
public class EditorialController {


    @Autowired
    private EditorialService service;

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ModelAndView editorialList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("editorial");
        mav.addObject("editorials", service.editorialList());
        mav.addObject("title", "List of Editorials");
        mav.addObject("emptyList", "Empty data base");
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
    public ModelAndView createEditorial() {
        ModelAndView mav = new ModelAndView("editorial-form");
        mav.addObject("editorial", new Editorial());
        mav.addObject("title", "Create editorial");
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
        ModelAndView mav = new ModelAndView("editorial-form");
        mav.addObject("editorial", service.searchEditorial(id));
        mav.addObject("title", "Edit editorial");
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
            service.crearEditorial(new Editorial(name));
            attributes.addFlashAttribute("exito-name", "Successful record");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/editorials");
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
    public RedirectView modify(@RequestParam Integer id, @RequestParam String name, RedirectAttributes attributes) throws Exception {
        try {
          
        service.modificarEditorial(id, name);
                    attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/editorials");
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
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/editorials");
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
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/editorials");
    }
}
