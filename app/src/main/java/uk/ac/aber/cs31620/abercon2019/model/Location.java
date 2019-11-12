package uk.ac.aber.cs31620.abercon2019.model;

class Location {
    private String id;
    private String name;
    private float latitude;
    private float longitude;
    private String description;

    public Location(String id, String name, float latitude, float longitude, String description) {
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getDescription() {
        return description;
    }
}
