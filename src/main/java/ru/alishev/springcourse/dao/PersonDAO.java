package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;
    private List<Person> people;

    {
        people = new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Ruslan"));
        people.add(new Person(++PEOPLE_COUNT, "Alfia"));
        people.add(new Person(++PEOPLE_COUNT, "Zabida"));
        people.add(new Person(++PEOPLE_COUNT, "Yasina"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(a -> a.getId() == id).findAny().orElse(null);
    }
}
