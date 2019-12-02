package uk.ac.aber.cs31620.abercon2019.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Location.java - Represents a location. It is paired with the locations table in the database.
 *
 * @author Michael Male
 * @version 1.0 2019-11-27
 */
@Entity(tableName = "locations")
public class Location {

    @NonNull
    @PrimaryKey
    private String id;

    private String name;
    private double latitude;
    private double longitude;
    private String description;

    /**
     * Constructor for objects of type Location.
     *
     * @param id          a non-null primary key representing the location ID.
     * @param name        String representing the location's name.
     * @param latitude    double representing the latitude of the location.
     * @param longitude   double representing the longitude of the location.
     * @param description Sting representing a description of the location.
     */
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
