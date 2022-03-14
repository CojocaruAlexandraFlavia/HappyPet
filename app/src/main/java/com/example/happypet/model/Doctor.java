package com.example.happypet.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "doctor", foreignKeys = {
        @ForeignKey(
                entity = Location.class,
                parentColumns = "locationId",
                childColumns = "locationId"
        )
})
public class Doctor extends User{

    @PrimaryKey(autoGenerate = true)
    private long doctorId;

    private String token;

    @ColumnInfo(index = true)
    private long locationId;

    public long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }
}
