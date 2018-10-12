package com.ogm.demo.gql.node;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

/**
 * Neo4j node representing a person.
 */
@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * person's year of birth
     */
    private int birthYear;

    /**
     * Person name
     */
    private String name;

    @Relationship(type = "PARENT")
    private List<Person> children = null;

    /**
     * Constructor
     */
    public Person() {}

    /**
     * Constructor
     * @param name person's name
     * @param birthYear person's year of birth
     */
    public Person (String name,
                   int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<Person> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }

        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (birthYear != person.birthYear) return false;
        return name != null ? name.equals(person.name) : person.name == null;

    }

    @Override
    public int hashCode() {
        int result = birthYear;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}