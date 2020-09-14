import javax.swing.*;

/**
 * Created by Julia Wigenstedt
 * Date: 2020-09-12
 * Time: 00:01
 * Project: CatDaycare
 * Copyright: MIT
 *
 * This class contains the properties for our Cat objects.
 */

public class Cat {
   private String name;
   private String breed;
   private int age;
   CatOwner owner;

    /**
     * The constructor for the Cat.
     * @param name
     * @param breed
     * @param age
     * @param owner
     */

    public Cat(String name, String breed, int age, CatOwner owner){
        this.name = name;
        this.breed = breed;
        this.age=age;
        this.owner=owner;
    }

    /**
     * Prints a message.
     */
    public void speak(){
        JOptionPane.showMessageDialog(null, name + " säger mjau!");
    }

    public void purr(){
        JOptionPane.showMessageDialog(null, "Prr prr prr... " + name +  " spinner.");
    }

    /**
     * Prints the cats name an who it belongs to.
     */
    public void belongsTo(){
        JOptionPane.showMessageDialog(null, name + " tillhör " + owner.getFirstName() + "." );
    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getAge() {
        return age;
    }

    public CatOwner getOwner() {
        return owner;
    }
}
