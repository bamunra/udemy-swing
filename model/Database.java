package model;

import java.io.*;
import java.sql.*;
import java.util.*;

public class Database {

    private LinkedList<Person> people;

    private Connection conn;

    public Database() {
        people = new LinkedList<>();
    }

    public void addPerson(Person person){
        people.add(person);
    }

    public List<Person> getPeople(){
        return Collections.unmodifiableList(people);
    }

    public void saveToFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream ous = new ObjectOutputStream(fos);

        Person[] persons = people.toArray(new Person[people.size()]);

        ous.writeObject(persons);

        ous.close();
    }

    public void connect(){
        String user = "hdag_test";
        String pass = "bredniak4";
        String dbClass = "com.mysql.jdbc.Driver";
        String dbDriver = "jdbc:mysql://db4free.net:3306/hdag_test_db";

        if (conn != null){
            return;
        }


        try {
            Class.forName(dbClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Exception("Driver not found");
        }
        try {
            conn = DriverManager.getConnection(dbDriver, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void disconnect(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() throws SQLException {

        String checkSQL = "select count(*) as count from people where id = ?";
        PreparedStatement chkStatment = conn.prepareStatement(checkSQL);

        String insertSQL = "insert into people (id,name,age,employment_status,tax_id,us_citizen,gender,occupation) values(?, ? ,? ,?, ?, ? ,? ,?)";
        PreparedStatement insertStatment = conn.prepareStatement(insertSQL);

        String insertSQL = "insert into people (id,name,age,employment_status,tax_id,us_citizen,gender,occupation) values(?, ? ,? ,?, ?, ? ,? ,?)";
        PreparedStatement insertStatment = conn.prepareStatement(insertSQL);


        for (Person pers: people
             ) {
            int id = pers.getId();
            String name = pers.getName();
            String occupation = pers.getOccupation();
            AgeCategory ageCatId = pers.getAgeCategory();
            EmploymentCategory empCat = pers.getEmpCategory();
            boolean isCitizen = pers.isUsCitizen();
            Gender gender = pers.getGender();
            String taxId = pers.getTaxId();


            chkStatment.setInt(1,id);
            ResultSet checkResult = chkStatment.executeQuery();
            checkResult.next();

            int count = checkResult.getInt(1);

            if(count == 0){
                System.out.println("Inserting person with ID");
                int column = 1;
                insertStatment.setInt(column++, id);
                insertStatment.setString(column++, name);
                insertStatment.setString(column++, ageCatId.name());
                insertStatment.setString(column++, empCat.name());
                insertStatment.setString(column++, taxId);
                insertStatment.setBoolean(column++, isCitizen);
                insertStatment.setString(column++, gender.name());
                insertStatment.setString(column++, occupation);

                insertStatment.executeUpdate();
            } else {
                System.out.println("Updating person with ID");
            }

            System.out.println("Count for person with ID " + id + " " + count);
        }
        insertStatment.close();
        chkStatment.close();
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

    public void removePerson(int index) {

        people.remove(index);

    }
}
