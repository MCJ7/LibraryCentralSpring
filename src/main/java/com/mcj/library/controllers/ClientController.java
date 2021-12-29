/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.controllers;

import com.mcj.library.entities.Client;
import com.mcj.library.services.ClientService;
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
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService service;

    /**
     *
     * @param request
     * @return
     */
    @GetMapping("")
    public ModelAndView editorialList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("client");
        List<Client> clients = service.clientList();
        mav.addObject("clients", clients);
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("exito", flashMap.get("exito-name"));
            mav.addObject("error", flashMap.get("error-name"));
        }
        mav.addObject("emptyList", "Empty data base");
        mav.addObject("title", "List of clients");
        return mav;
    }

    /**
     *
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("client-form");
        mav.addObject("client", new Client());
        mav.addObject("title", "Successful cliente");
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
        ModelAndView mav = new ModelAndView("client-form");
        mav.addObject("client", service.searchId(id));
        mav.addObject("title", "Edit client");
        mav.addObject("action", "update");
        return mav;
    }

    /**
     *
     * @param dni
     * @param fullName
     * @param mobileNumber
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public RedirectView saving(@RequestParam Long dni, @RequestParam String 
            fullName, @RequestParam String mobileNumber, RedirectAttributes attributes) throws Exception {
        try {
            service.create(dni, fullName, mobileNumber);
            attributes.addFlashAttribute("exito-name", "Successful save");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/clients");
    }

    /**
     *
     * @param id
     * @param dni
     * @param fullName
     * @param mobileNumber
     * @param attributes
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    public RedirectView modify(@RequestParam Long id, @RequestParam Long dni,
            @RequestParam String fullName, @RequestParam String mobileNumber,
            RedirectAttributes attributes) throws Exception {
        try {

            service.modify(id, dni, fullName, mobileNumber);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/clients");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/enable/{id}")
    public RedirectView enable(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            service.enable(id);
            attributes.addFlashAttribute("exito-name", "Successful update");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }
        return new RedirectView("/clients");
    }

    /**
     *
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            service.delete(id);
            attributes.addFlashAttribute("exito-name", "Successful delete");
        } catch (Exception e) {
            attributes.addFlashAttribute("error-name", e.getMessage());
        }

        return new RedirectView("/clients");
    }

}