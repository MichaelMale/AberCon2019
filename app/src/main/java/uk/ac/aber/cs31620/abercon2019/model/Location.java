package uk.ac.aber.cs31620.abercon2019.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "locations")
public class Location {

    @NonNull
    @PrimaryKey
    private String id;

    private String name;
    private double latitude;
    private double longitude;
    private String description;

    public Location(@NonNull String id, String name, double latitude, double longitude,
                    String description) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }
}
