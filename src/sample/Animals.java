package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Animals {

    private SimpleIntegerProperty id;
    private SimpleStringProperty animal;
    private SimpleStringProperty name;
    private SimpleStringProperty breed;
    private SimpleIntegerProperty years;
    private SimpleStringProperty owner;


    public Animals(){
        this.id = new SimpleIntegerProperty();
        this.animal = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.breed = new SimpleStringProperty();
        this.years = new SimpleIntegerProperty();
        this.owner = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getAnimal() {
        return animal.get();
    }

    public void setAnimal(String animal) {
        this.animal.set(animal);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBreed() {
        return breed.get();
    }

    public void setBreed(String breed) {
        this.breed.set(breed);
    }

    public int getYears() {
        return years.get();
    }

    public void setYears(int years) {
        this.years.set(years);
    }

    public String getOwner() {
        return owner.get();
    }

    public void setOwner(String owner) {
        this.owner.set(owner);
    }
}
