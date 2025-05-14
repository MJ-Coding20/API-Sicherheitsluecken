package de.seminar.objects;

public class Person {
    private String name;
    private String surname;
    private String birthdate;

    // Constructor
    public Person(String name, String surname, String birthdate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthdate() {
        return birthdate;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Surname: " + surname + ", Birthdate: " + birthdate;
    }
}

