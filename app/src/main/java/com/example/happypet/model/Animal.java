package com.example.happypet.model;

import androidx.annotation.StringDef;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Entity(tableName = "animal", foreignKeys = {
        @ForeignKey(
                entity = Client.class,
                parentColumns = "clientId",
                childColumns = "ownerId"
        )
})
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

    @ColumnInfo(index = true)
    private long ownerId;

//    public Animal(long animalId, String name, @AnimalType String type, int age, long ownerId) {
//        this.animalId = animalId;
//        this.name = name;
//        this.type = type;
//        this.age = age;
//        this.ownerId = ownerId;
//    }

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

    public @AnimalType String getType() {
        return type;
    }

    public void setType(@AnimalType String type) {
        this.type = type;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }
}
