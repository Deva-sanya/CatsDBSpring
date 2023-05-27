package project.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.models.Cat;
import project.models.Person;
import project.repositories.CatRepository;
import project.repositories.PersonRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class CatsService {

    private final CatRepository catRepository;
    private final PersonRepository personRepository;

    @Autowired
    public CatsService(CatRepository catRepository, PersonRepository personRepository) {
        this.catRepository = catRepository;
        this.personRepository = personRepository;
    }

    public List<Cat> findAllCats() {
        return catRepository.findAll(PageRequest.of(0, 10, Sort.by("name"))).getContent();
    }

    public Cat findCatById(int id) {
        Optional<Cat> foundCat = catRepository.findById(id);
        return foundCat.orElse(null);
    }

    @Transactional
    public void saveCat(Cat cat) {
        catRepository.save(cat);
    }

    @Transactional
    public void updateCat(int id, Cat updatedCat) {
        updatedCat.setId(id);
        catRepository.save(updatedCat);
    }

    @Transactional
    public void delete(int id) {
        catRepository.deleteById(id);
    }

    public Person getCatOwner(int id) {
        Optional<Cat> cat = catRepository.findById(id);
        if (cat.isPresent()) {
            Hibernate.initialize(cat.get().getOwner());
            return cat.get().getOwner();
        } else {
            return (Person) Collections.emptyList();
        }
    }

    public List<Cat> findCat(String name) {
        return catRepository.findCatByNameStartsWith(name);
    }

}
