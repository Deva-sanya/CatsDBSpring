package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.models.Cat;
import project.models.Person;
import project.services.CatsService;
import project.services.PeopleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cats")
public class CatController {

    private final CatsService catsService;
    private final PeopleService peopleService;

    @Autowired
    public CatController(CatsService catsService, PeopleService peopleService) {
        this.catsService = catsService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model) {
        List<Cat> cats = catsService.findAllCats();
        model.addAttribute("cats", cats);
        return "/cats/allCats";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("cat", catsService.findCatById(id));
        Optional<Person> catOwner = Optional.ofNullable(catsService.getCatOwner(id));

        if (catOwner.isPresent())
            model.addAttribute("owner", catOwner.get());
        else
            model.addAttribute("people", peopleService.findAllPeople());

        return "/cats/showCat";
    }

    @GetMapping("/new")
    public String newCat(@ModelAttribute("cat") Cat cat) {
        return "/cats/addCat";
    }

    @PostMapping("/createCat")
    public String create(@ModelAttribute("cat") @Valid Cat cat,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/cats/addCat";
        catsService.saveCat(cat);
        return "redirect:/cats";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("cat", catsService.findCatById(id));
        return "/cats/editCat";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("cat") @Valid Cat cat, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "/cats/editCat";
        catsService.updateCat(id, cat);
        return "redirect:/cats";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        catsService.delete(id);
        return "redirect:/cats";
    }


    @GetMapping("/search")
    public String searchCat() {
        return "/cats/search";
    }

    @PostMapping("/searchCat")
    public String search(@RequestParam(value = "name", required = false) String name, Model model) {
        List<Cat> cats = catsService.findCat(name);
        model.addAttribute("cats", cats);
        return "/cats/search";
    }
}
