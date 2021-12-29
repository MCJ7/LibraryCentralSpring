package com.mcj.library.services;

import com.mcj.library.entities.Author;
import com.mcj.library.repositories.AutorRepository;
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
public class AuthorService {

  
    @Autowired
    private AutorRepository repository;

    /**
     *
     * @param author
     * @throws Exception
     */
    @Transactional
    public void crearAutor(Author author) throws Exception {
        if (author.getName() == null || author.getName().trim().isEmpty()) {
            throw new Exception("Did not write name");
        }
        repository.save(author);
    }

    /**
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<Author> authorsList() {
        return repository.findAll();
    }

    /**
     *
     * @param id
     * @param name
     * @throws Exception
     */
    @Transactional
    public void modify(Integer id, String name) throws Exception {
        if (name == null || name.trim().isEmpty()) {
            throw new Exception("Did not write name");
        }
        repository.update(id, name);
    }

    /**
     *
     * @param id
     */
    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Author serchAuthor(Integer id) {
        Optional<Author> authorOptional = repository.findById(id);
        return authorOptional.orElse(null);
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
