/**
 * Created by Julia Wigenstedt
 * Date: 2020-09-12
 * Time: 00:01
 * Project: CatDaycare
 * Copyright: MIT
 */

public class Person {
    protected String firstName;
    protected String lastName;
    protected int age;

    public Person(String firstName, String lastName){
        if (!firstName.isEmpty() || firstName!=null){
            this.firstName = firstName;
        }
        if (!lastName.isEmpty() || lastName!=null) {
            this.lastName = lastName;
        }

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
