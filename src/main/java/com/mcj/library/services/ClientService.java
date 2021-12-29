/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcj.library.services;

import com.mcj.library.entities.Client;
import com.mcj.library.exception.ExceptionPersonalized;
import com.mcj.library.repositories.ClientRepository;
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
public class ClientService {


    @Autowired
    private ClientRepository repository;

    /**
     *
     * @param dni
     * @param fullName
     * @param mobileNumber
     * @throws Exception
     */
    public void create(Long dni, String fullName, String mobileNumber) throws Exception {

        validate(dni, fullName, mobileNumber);
        Client client = new Client();
        client.setDni(dni);
        client.setFullName(fullName);
        client.setMobileNumber(mobileNumber);
        client.setRegister(true);

        repository.save(client);
    }

    /**
     *
     * @param id
     * @param dni
     * @param fullName
     * @param mobileNumber
     * @throws Exception
     */
    @Transactional
    public void modify(Long id,Long dni, String fullName, String mobileNumber) throws Exception {
        Client client = repository.findById(id).get();
        client.setDni(dni);
        client.setFullName(fullName);
        client.setMobileNumber(mobileNumber);
        client.setRegister(true);

        repository.save(client);        
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Client> clientList() {
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
    @Transactional(readOnly=true)
    public Client searchId(Long id) {
        Optional<Client> optionalClient = repository.findById(id);
        return optionalClient.orElse(null);
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void enable(Long id) {
        Client c = repository.findById(id).get();
        c.setRegister(!c.getRegister());
        repository.save(c);
    }

    /**
     *
     * @param dni
     * @param fullName
     * @param mobileNumber
     * @throws ExceptionPersonalized
     */
    public void validate(Long dni, String fullName, String mobileNumber) throws ExceptionPersonalized{
        if (dni>1000000) {
            throw new ExceptionPersonalized("Error personal id");
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new ExceptionPersonalized("Error fullName");
        }
        if (mobileNumber == null || mobileNumber.trim().isEmpty()) {
            throw new ExceptionPersonalized("Error dni");
        }
        
    }

}
