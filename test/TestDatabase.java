package test;

import model.*;

import java.sql.SQLException;

public class TestDatabase {

    public static void main(String[] args) {
        System.out.println("Running database test");

        Database db =  new Database();

        db.connect();

        db.addPerson(new Person("Joe", "Porno star", AgeCategory.adult, EmploymentCategory.employed,"777",true, Gender.male));
        db.addPerson(new Person("Sue", "Porno star", AgeCategory.senior, EmploymentCategory.selfemployed,null,false, Gender.female));

        try {
            db.save();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.disconnect();
    }

}
