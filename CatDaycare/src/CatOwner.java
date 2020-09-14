/**
 * Created by Julia Wigenstedt
 * Date: 2020-09-12
 * Time: 00:01
 * Project: CatDaycare
 * Copyright: MIT
 */

public class CatOwner extends Person {
    protected int numberOfCats=0;

    public CatOwner(String firstName, String lastName) {
        super(firstName, lastName);
        numberOfCats++;
    }

    public int getNumberOfCats() {
        return numberOfCats;
    }

    public void setNumberOfCats(int numberOfCats) {
        this.numberOfCats = numberOfCats;
    }
}
