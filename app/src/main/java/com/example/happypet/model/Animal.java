package com.example.happypet.model;

import androidx.annotation.StringDef;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Entity(tableName = "animal")
public class Animal {

    public static final String DOG = "DOG";
    public static final String CAT = "CAT";

    @PrimaryKey(autoGenerate = true)
    private long animalId;

    private String name;

    @StringDef({DOG, CAT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimalType {}

    private @AnimalType String type;

    private int age;

    private String photoPath;

    private Long ownerId;

    public Animal(long animalId, String name, String type, int age, String photoPath) {
        this.animalId = animalId;
        this.name = name;
        this.type = type;
        this.age = age;
        this.photoPath = photoPath;
    }

    public Animal(){}

    public long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(long animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
