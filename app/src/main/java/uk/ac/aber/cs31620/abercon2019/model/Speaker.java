package uk.ac.aber.cs31620.abercon2019.model;

public class Speaker {
    private String id;
    private String name;
    private String biography;
    private String twitter;

    public Speaker(String id, String name, String biography, String twitter) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.twitter = twitter;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getTwitter() {
        return twitter;
    }
}
