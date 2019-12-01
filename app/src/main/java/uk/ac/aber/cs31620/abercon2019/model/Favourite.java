package uk.ac.aber.cs31620.abercon2019.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Favourite.java - A class to hold a representation of a user's 'favcurite' sessions. These are
 * sessions that the user has explicitly marked as wanting quick access to. This class has been
 * set up specifically for use with the Room persistence API and creates a new table within the
 * schema. The table includes one primary key and three foreign keys and any tuples in the table
 * will be related to the other tables in the schema. Creating this as a persistent data model
 * makes it very easy to store data within the app storage.
 *
 * @author Michael Male
 * @version 1.0     PRODUCTION
 * @see uk.ac.aber.cs31620.abercon2019.ui.favourites.FavouritesFragment
 * @see androidx.room.Room
 */
@Entity(indices = {@Index("speakerId"),
        @Index("locationId")},
        tableName = "favourites",
        foreignKeys = {@ForeignKey(entity = Session.class,
                parentColumns = "id",
                childColumns = "sessionId"),
                @ForeignKey(entity = Speaker.class,
                        parentColumns = "id",
                        childColumns = "speakerId"),
                @ForeignKey(entity = Location.class,
                        parentColumns = "id",
                        childColumns = "locationId")
        })
public class Favourite {

    @NonNull
    @PrimaryKey
    private String sessionId;

    private String speakerId;
    private String locationId;

    /**
     * Gets the session's ID
     *
     * @return String representing the session ID
     */
    @NonNull
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the session's ID
     *
     * @param sessionId String representing the session ID
     */
    public void setSessionId(@NonNull String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Gets the speaker's ID
     *
     * @return String representing the speaker ID
     */
    public String getSpeakerId() {
        return speakerId;
    }

    /**
     * Sets the speaker's ID
     *
     * @param speakerId String representing the speaker ID
     */
    public void setSpeakerId(String speakerId) {
        this.speakerId = speakerId;
    }

    /**
     * Gets the location's ID
     *
     * @return String representing the location ID
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Sets the location's ID
     *
     * @param locationId String representing the location ID
     */
    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
