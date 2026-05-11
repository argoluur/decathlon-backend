package ee.argo.decathlon.controller;

import ee.argo.decathlon.entity.Person;
import ee.argo.decathlon.repositry.PersonRepositry;
import ee.argo.decathlon.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("persons")
@CrossOrigin(origins = "http://localhost:5173")
public class PersonController {

    private final PersonService personService;
    private final PersonRepositry personRepositry;

    @GetMapping
    public Page<Person> getPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "score") String sort,
            @RequestParam(defaultValue = "desc") String dir,
            @RequestParam(required = false) String country
    ) {
        Sort sorting = dir.equalsIgnoreCase("desc") ? Sort.by(sort).descending() : Sort.by(sort).ascending();
        Pageable pageable = PageRequest.of(page, size, sorting);

        return personService.getPersonsPage(country, pageable);
    }

    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personService.savePerson(person);
    }

    @PutMapping("{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        return personService.updatePerson(id, personDetails);
    }

    @DeleteMapping("{id}")
    public void deletePerson(@PathVariable Long id) {
        personRepositry.deleteById(id);
    }
}