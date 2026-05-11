package ee.argo.decathlon.service;

import ee.argo.decathlon.entity.Person;
import ee.argo.decathlon.repositry.PersonRepositry;
import ee.argo.decathlon.strategy.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class PersonService {

    private final PersonRepositry personRepositry;

    // OOP: Strategy Pattern Map - seome nime ja klassi
    private static final Map<String, ScoringStrategy> strategies = Map.of(
            "100m", new HundredMetersScoring(),
            "Long Jump", new LongJumpScoring()
    );

    public Page<Person> getPersonsPage(String country, Pageable pageable) {
        Page<Person> result;
        if (country != null && !country.isEmpty()) {
            result = personRepositry.findByCountryContainingIgnoreCase(country, pageable);
        } else {
            result = personRepositry.findAll(pageable);
        }

        // Arvutame punktid igale isikule enne väljastamist
        result.forEach(this::enrichWithPoints);
        return result;
    }

    public Person savePerson(Person person) {
        validatePerson(person);
        enrichWithPoints(person);
        return personRepositry.save(person);
    }

    public Person updatePerson(Long id, Person details) {
        Person person = personRepositry.findById(id)
                .orElseThrow(() -> new RuntimeException("Isikut ei leitud: " + id));

        person.setFirstName(details.getFirstName());
        person.setLastName(details.getLastName());
        person.setCountry(details.getCountry());
        person.setSportField(details.getSportField());
        person.setScore(details.getScore());

        enrichWithPoints(person);
        return personRepositry.save(person);
    }

    // Abimeetod, mis täidab transientse 'points' välja
    private void enrichWithPoints(Person person) {
        ScoringStrategy strategy = strategies.get(person.getSportField());
        if (strategy != null && person.getScore() != null) {
            double pts = strategy.calculate(person.getScore());
            person.setPoints((int) Math.round(pts));
        } else {
            person.setPoints(0);
        }
    }

    private void validatePerson(Person person) {
        if (person.getFirstName() == null || person.getLastName() == null) {
            throw new RuntimeException("Nimi on kohustuslik!");
        }
        if (person.getScore() == null || person.getScore() < 0) {
            throw new RuntimeException("Skoor peab olema positiivne arv!");
        }
    }
}