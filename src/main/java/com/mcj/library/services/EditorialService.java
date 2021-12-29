/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.services;

import com.mcj.library.entities.Editorial;
import com.mcj.library.repositories.EditorialRepository;
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
public class EditorialService {
    
    @Autowired
    private EditorialRepository repository;
    
    /**
     *
     * @param editorial
     * @throws Exception
     */
    @Transactional
    public void crearEditorial(Editorial editorial) throws Exception{
        
        
        if (editorial.getName() == null || editorial.getName().trim().isEmpty()) {
            throw new Exception("Did not write name");
        }
        editorial.setRegister(true);
        
        repository.save(editorial);
    }
    
    /**
     *
     * @param id
     * @param name
     * @throws Exception
     */
    @Transactional
    public void modificarEditorial(Integer id, String name) throws Exception{
         if (name== null || name.trim().isEmpty()) {
            throw new Exception("Did not write name");
        }
        
        repository.update(id, name);
    }
    
    /**
     *
     * @return
     */
    @Transactional (readOnly = true)
    public  List<Editorial> editorialList(){
        return repository.findAll();
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void delete(Integer id){
        repository.deleteById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public Editorial searchEditorial(Integer id){
        Optional<Editorial> editorialOpcional = repository.findById(id);
        return editorialOpcional.orElse(null);
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void enable(Integer id) {
        repository.enable(id);
    }
    
}
