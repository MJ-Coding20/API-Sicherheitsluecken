package de.seminar.utils;

import de.seminar.objects.Person;

import java.util.Random;

public class RandomPeople {
    public static Person[] generate(int amount) {
        // Predefined names and surnames
        String[] names = {"John", "Alice", "Bob", "Eve", "Charlie", "David", "Grace", "Hannah",
                "Ivy", "Jack", "Kelly", "Liam", "Mia", "Noah", "Olivia", "Paul",
                "Quinn", "Riley", "Sophia", "Tyler"};

        String[] surnames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Miller", "Davis",
                "García", "Rodriguez", "Martínez", "Hernández", "Lopez", "González",
                "Pérez", "Sánchez", "Ramírez", "Torres", "Flores", "Rivera", "Gomez"};

        // Create an array to store 20 random Person objects
        Person[] people = new Person[amount];

        // Random number generator
        Random random = new Random();

        // Generate random people
        for (int i = 0; i < people.length; i++) {
            String name = names[random.nextInt(names.length)];
            String surname = surnames[random.nextInt(surnames.length)];
            String birthdate = generateRandomBirthdate();

            people[i] = new Person(name, surname, birthdate);
        }

        return people;
    }

    // Generate a random birthdate between 1980 and 2000
    public static String generateRandomBirthdate() {
        Random random = new Random();

        int year = random.nextInt(21) + 1980;  // Random year between 1980 and 2000
        int month = random.nextInt(12) + 1;    // Random month between 1 and 12
        int day = random.nextInt(28) + 1;      // Random day between 1 and 28 (to avoid invalid dates)

        // Format the date as YYYY-MM-DD
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}

