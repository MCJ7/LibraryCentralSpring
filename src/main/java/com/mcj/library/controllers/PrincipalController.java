/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author maxco
 */
@Controller
@RequestMapping("")
public class PrincipalController {
    

    @GetMapping("/")
    public ModelAndView start(){
        return new ModelAndView("index");
    }
    

 
    
}
