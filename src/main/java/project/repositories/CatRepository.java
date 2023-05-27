package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.models.Cat;

import java.util.List;

public interface CatRepository extends JpaRepository<Cat, Integer> {
    List<Cat> findCatByNameStartsWith(String name);
}
