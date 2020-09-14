/**
 * Created by Julia Wigenstedt
 * Date: 2020-09-12
 * Time: 00:00
 * Project: CatDaycare
 * Copyright: MIT
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Except for the main method, we also have declared a couple of static lists, since we want to use them in different methods.
 */
public class Main {

    public static List<Cat> catList = new ArrayList<>();
    public static List<CatOwner> owners = new ArrayList<>();
    public static List<Person> personList = new ArrayList<>();


    public static void main(String[] args) {
        JOptionPane.showMessageDialog(null, "Hej och välkommen till Julias kattdagis!");
        boolean running = true;
        while (running) {
            running = welcomeMenu();
        }
    }

    /**
     * We use this method to ask the user what it wants to do.
     * We keep running (true) if the user don't want to exit.
     * @return True or False
     */

    public static boolean welcomeMenu() {

        String selection = JOptionPane.showInputDialog(null, "Välj vad du vill göra:"
                + "\r\n\r\n1. Lämna katt på dagis"
                + "\r\n2. Lista alla katter på dagis"
                + "\r\n3. Hämta en katt från dagis"
                + "\r\n4. Avsluta");

    if (selection==null) {
        JOptionPane.showMessageDialog(null, "Du måste göra ett val. (1-4)");
        return true;
    }
         else if (selection.equals("1")) {
            catList.add(createCat());
            return true;
        } else if (selection.equals("2")) {
            listCats();
            if (!catList.isEmpty()) {
                petCat();
            }
            return true;
        } else if (selection.equals("3")) {
            removeCat();
            return true;
        } else if (selection.equals("4")) {
            return !exitMenu();

        }  else {
            JOptionPane.showMessageDialog(null, "Mata in ett giltigt alternativ! (1-4)");
            return true;

        }

    }

    /**
     * We create a person.
     * @return The person.
     */
    public static Person createPerson() {
        {
            String firstName = null;
            String lastName = null;
            while (firstName == null || firstName.isEmpty()) {
                firstName = JOptionPane.showInputDialog(null, "Skriv in ditt förnamn: ", "Förnamn", JOptionPane.QUESTION_MESSAGE);
                if (firstName == null || firstName.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Du måste skriva in ditt förnamn!");
                }
            }
            while (lastName == null || lastName.isEmpty()) {
                lastName = JOptionPane.showInputDialog(null, "Skriv in ditt efternamn: ", "Efternamn", JOptionPane.QUESTION_MESSAGE);
                if (lastName == null || lastName.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Du måste skriva in ditt efternamn!");
                }
            }
            return new Person(capitalize(firstName), capitalize(lastName));
        }
    }

    /**
     * We use a created person and greets the user differently depending on if they've written their name before.
     * Then we create a CatOwner with the same properties.
     * @return The CatOwner
     */
    public static CatOwner createOwner() {
        Person person = createPerson();
        if (findPersonInList(person.getFirstName(), person.getLastName(), personList) == null) {
            personList.add(person);
            JOptionPane.showMessageDialog(null, "Välkommen, " + person.getFirstName() + "!");

        } else
            JOptionPane.showMessageDialog(null, "Hej igen, " + person.getFirstName() + "!");

        return new CatOwner(person.getFirstName(), person.getLastName());
    }

    /**
     * We set the cat's different properties.
     * @return new Cat object.
     */

    public static Cat createCat() {
        String name = null;
        String breed = null;
        String ageString = null;
        int age;

        CatOwner owner = createOwner();
        if (findCatOwnerInList(owner.getFirstName(), owner.lastName, owners) == null) {
            owners.add(owner);
        }
        while (name == null || name.isEmpty())
            name = capitalize(JOptionPane.showInputDialog(null, "Skriv in kattens namn: ", "Kattens namn", JOptionPane.QUESTION_MESSAGE));
        while (breed == null || breed.isEmpty())
            breed = capitalize(JOptionPane.showInputDialog(null, "Skriv in " + name + "s ras: ", "Kattens ras", JOptionPane.QUESTION_MESSAGE));
        while (ageString == null || ageString.isEmpty() || !tryAge(ageString)) {
            ageString = JOptionPane.showInputDialog(null, "Skriv in " + name + "s ålder: ", "Kattens ålder", JOptionPane.QUESTION_MESSAGE);
        }
        age = Integer.parseInt(ageString);
        return new Cat(name, breed, age, owner);
    }

    /**
     * Prints the catList if it contains objects.
     * The user can choose a cat to pet as well.
     */
    public static void listCats() {
        if (catList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Det finns ingen katt på dagiset just nu. Lämna en katt först!");
        } else {
            String stringOfCats = "";

            for (Cat cat : catList) {
                stringOfCats = stringOfCats + cat.getName() + " " + cat.getBreed() + ", " + cat.getAge() + " år. Ägare: " + cat.owner.firstName + "\n";
            }
            JOptionPane.showMessageDialog(null, stringOfCats);
        }
    }

    /**
     * Removes cat if it's in the list and the correct owner is picking it up.
     */

    public static void removeCat() {
        if (catList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Det finns ingen katt på dagiset just nu. Lämna en katt först!");
        } else {
            String name = capitalize(JOptionPane.showInputDialog("Ditt förnamn?"));
            String selection = capitalize(JOptionPane.showInputDialog("Skriv in namnet på katten du vill hämta: "));

            if (findCatInList(selection, catList) != null) {
                Cat cat = findCatInList(selection, catList);

                if (!cat.getOwner().getFirstName().equals(name)) {
                    cat.belongsTo();
                } else if (cat.getOwner().getFirstName().equals(name)) {
                    JOptionPane.showMessageDialog(null, cat.getOwner().getFirstName() + ", du har hämtat " + cat.getName() + "!");
                    cat.speak();
                    catList.remove(cat);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Det finns ingen katt med det namnet. (" + selection + ")");
            }
        }
    }

    /**
     * Returns a string with the first letter to Upper Case.
     * @param input the string we want to capitalize
     * @return the String with Upper Case first letter.
     */
    public static String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * Checks if a string can be parsed and checks if the number is valid.
     * @param value The String.
     * @return true or false
     */
    public static boolean tryAge(String value) {
        try {
            int parsed = Integer.parseInt(value);
            if (parsed >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Åldern måste vara en siffra från 0 och uppåt.");
            return false;
        }
    }

    /**
     * Check if a person with the same name is in the list
     * @param firstName The first name of person
     * @param lastName The last name of person
     * @param personList The list we're searching
     * @return The person if it exists, otherwise null.
     */
    public static Person findPersonInList(String firstName, String lastName, List<Person> personList) {

        for (Person person : personList) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }

    /**
     * Checks if a catOwner with the same name is in the list.
     * @param firstName The firstname of catOwner
     * @param lastName lastName of catOwner
     * @param owners The list we're searching
     * @return The owner if it's in the list, otherwise null.
     */

    public static CatOwner findCatOwnerInList(String firstName, String lastName, List<CatOwner> owners) {

        for (CatOwner owner : owners) {
            if (owner.getFirstName().equals(firstName) && owner.getLastName().equals(lastName)) {
                return owner;
            }
        }
        return null;
    }

    /**
     * Checks if a cat with the same name is in the list.
     * @param name The name we're looking for
     * @param catList The list we're searching
     * @return the cat if it's in there, otherwise null.
     */
    public static Cat findCatInList(String name, List<Cat> catList) {

        for (Cat cat : catList) {
            if (cat.getName().equals(name)) {
                return cat;
            }
        }
        return null;
    }

    /**
     * User chooses a cat to pet.
     * If the cat doesn't exist, they get a message.
     * Oterwise they pet the chosen cat.
     */
    public static void petCat() {
        int selection;
        String chosenCat;
        selection = JOptionPane.showConfirmDialog(null, "Vill du klappa en katt?", "Klappa?", JOptionPane.YES_NO_OPTION);
        if (selection == 0) {
            chosenCat = capitalize(JOptionPane.showInputDialog("Vem vill du klappa?"));
            if (findCatInList(chosenCat, catList) == null) {
                JOptionPane.showMessageDialog(null, "Det fanns ingen katt med det namnet. Alla katter gömde sig för dig.");
            } else {
                Cat cat = findCatInList(chosenCat, catList);
                cat.purr();
            }
        }
    }

    /**
     * Checks if user wants to quit.
     * @return true or false
     */

    public static boolean exitMenu() {
        int selection = JOptionPane.showConfirmDialog(null, "Är du säker på att du vill gå hem?", "Gå hem?", JOptionPane.YES_NO_OPTION);
        if (selection == 0) {
            JOptionPane.showMessageDialog(null, "Tack för besöket! Välkommen åter.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Stanna så länge du vill!");
            return false;
        }
    }
}