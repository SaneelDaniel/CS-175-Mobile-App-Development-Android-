package edu.sjsu.android.test_hw2;

/**
 * public class Animal that keeps the attributes of the object that needs to be vieweed
 */
public class Animal {

    //private variables storing various attriubtes of the object Animal
    private String animalName;
    private int imageResID;
    private String description;

    /**
     * constructor for the Animal object
     * @param name name of the animal
     * @param imageResID image reference if for the animal image
     * @param animalDesc animal description
     */
    public Animal(String name, int imageResID, String animalDesc){
        this.animalName = name;
        this.imageResID = imageResID;
        this.description = animalDesc;
    }

    /**
     * getter for animal name
     * @return animal name
     */
    public String getAnimalName() {
        return animalName;
    }

    /**
     * getter for animal image id
     * @return image id
     */
    public int getImageResID() {
        return imageResID;
    }

    /**
     * getter for animal description
     * @return description
     */
    public String getAnimalDesc() {
        return description;
    }
}
