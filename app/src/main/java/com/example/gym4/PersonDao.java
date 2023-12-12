package com.example.gym4;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PersonDao {
    @Insert
    void insertPerson(Person person);

    @Query("SELECT * FROM persons WHERE dni = :dni")
    Person getPersonByDNI(String dni);

    @Query("SELECT * FROM persons")
    List<Person> getAllPersons();

    @Delete
    void deletePerson(Person person);
}
