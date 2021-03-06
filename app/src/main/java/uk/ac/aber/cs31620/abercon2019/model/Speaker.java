package uk.ac.aber.cs31620.abercon2019.model;


import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import uk.ac.aber.cs31620.abercon2019.model.datasource.util.ResourceUtil;

/**
 * Speaker.java - An object that represents a speaker. Paired with the speaker table in the
 * database.
 *
 * @author Michael Male
 * @version 1.0 2019-12-01
 */
@Entity(tableName = "speakers")
public class Speaker {

    @NonNull
    @PrimaryKey
    private String id;

    private String name;
    private String biography;
    private String twitter;

    /**
     * Constructor for objects of type Speaker.
     *
     * @param id        Primary key for the speakers record.
     * @param name      Name of the speaker.
     * @param biography Details about the speaker.
     * @param twitter   Twitter handle for the speaker.
     */
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

    /**
     * A method to get the image relative to a Speaker. This has been written to work with both
     * PNG and JPG images, as the assets folder only contains those two.
     *
     * @param context Context of the application
     * @return A Bitmap containing a representation of the image. Returns null if no image could
     * be found.
     */
    public Bitmap getImagePath(Context context) {
        String start = this.getId();
        start = start.trim(); // Trims to ensure there are no leading spaces

        if (ResourceUtil.getAssetImageAsBitmap(context, (start + ".jpg")) != null) {
            return ResourceUtil.getAssetImageAsBitmap(context, (start + ".jpg"));
        } else if (ResourceUtil.getAssetImageAsBitmap(context, (start + ".png")) != null) {
            return ResourceUtil.getAssetImageAsBitmap(context, (start + ".png"));
        } else return null;
    }
}
