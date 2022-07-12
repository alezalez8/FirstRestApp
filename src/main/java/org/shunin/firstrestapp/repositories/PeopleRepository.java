package org.shunin.firstrestapp.repositories;

import org.shunin.firstrestapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeopleRepository  extends JpaRepository<Person, Integer> {
}
