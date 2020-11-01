package com.mohanty.PersonCrud.dao;

import com.mohanty.PersonCrud.model.Person;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgre-dao")
public class PersonDaoImpl implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @PostConstruct
    public static void postConstruct() {
        Person p1 = new Person(UUID.randomUUID(), "Ashutosh Mohanty");
        Person p2 = new Person(UUID.randomUUID(), "Anil Mohanty");
        Person p3 = new Person(UUID.randomUUID(), "Jayashree Mohanty");
        DB.add(p1);
        DB.add(p2);
        DB.add(p3);
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id, person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        return DB;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        return DB.stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> person = selectPersonById(id);
        if (!person.isPresent())
            return 0;
        DB.remove(person.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {

        /*if (selectPersonById(id).isPresent()) {
            Person person1 = selectPersonById(id).get();
            int indexToUpdate = DB.indexOf(person1);
            DB.set(indexToUpdate,new Person(id,person.getName()));
            return 1;
        } else {
            insertPerson(id, person);
            return 0;
        }*/

        return selectPersonById(id)
               .map(personObject -> {
                   int indexOfPersonToUpdate = DB.indexOf(personObject);
                   if(indexOfPersonToUpdate>=0) {
                       DB.set(indexOfPersonToUpdate, new Person(id,person.getName()));
                       return 1;
                   }
                   else
                       return 0;
               }).orElse(0);

    }


}
