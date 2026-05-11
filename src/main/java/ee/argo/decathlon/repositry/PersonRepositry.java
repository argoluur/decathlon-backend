package ee.argo.decathlon.repositry;

import ee.argo.decathlon.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepositry extends JpaRepository<Person, Long> {
    Page<Person> findByCountryContainingIgnoreCase(String country, Pageable pageable);
}
