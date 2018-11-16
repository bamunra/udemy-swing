package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Database {

    private ArrayList<Person> people;

    public Database() {
        people = new ArrayList<Person>();
    }

    public void addPerson(Person person){
        people.add(person);
    }

    public List<Person> getPeople(){
        return people;
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream ous = new ObjectOutputStream(fos);

        Person[] persons = people.toArray(new Person[people.size()]);

        ous.writeObject(persons);

        ous.close();
    }


    public void loadFromFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ius = new ObjectInputStream(fis);

        try {
            Person[] persons = (Person[]) ius.readObject();
            people.clear();
            people.addAll(Arrays.asList(persons));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        ius.close();
    }
}
