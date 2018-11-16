package controller;

import gui.FormEvent;
import model.*;

public class Controller {

    Database db = new Database();

    public void addPerson(FormEvent event) {
        String name = event.getName();
        String occupation = event.getOccupation();
        int ageCatId = event.getAgeCategory();
        String empCat = event.getEmpCategory();
        boolean isCitizen = event.isUsCitizen();
        String gender = event.getGender();
        String taxId = event.getTaxId();
        AgeCategory ageCat = null;

        switch (ageCatId) {
            case 0:
                ageCat = AgeCategory.child;
                break;
            case 1:
                ageCat = AgeCategory.adult;
                break;
            case 2:
                ageCat = AgeCategory.senior;
                break;
        }

        EmploymentCategory empCategory;
        if (empCat.equals("employed")) {
            empCategory = EmploymentCategory.employed;
        } else if (empCat.equals("selfemployed")) {
            empCategory = EmploymentCategory.selfemployed;
        } else if (empCat.equals("unemployed")) {
            empCategory = EmploymentCategory.unemployed;
        } else {
            empCategory = EmploymentCategory.other;
            System.out.println(empCat);
        }

        Gender genderCat = null;
        if (gender.equals("male")) {
            genderCat = Gender.male;
        } else if (gender.equals("female")){
            genderCat = Gender.female;
        }

        Person person = new Person(name, occupation, ageCat, empCategory, taxId, isCitizen, genderCat);

        db.addPerson(person);

    }

}
