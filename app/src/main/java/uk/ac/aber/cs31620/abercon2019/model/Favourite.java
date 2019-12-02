package uk.ac.aber.cs31620.abercon2019.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
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
 * @version 1.0     2019-12-03
 * @see uk.ac.aber.cs31620.abercon2019.ui.favourites.FavouritesFragment
 * @see androidx.room.Room
 */
@Entity(tableName = "favourites")
public class Favourite {

    @NonNull
    @PrimaryKey
    private String id;

    public Favourite(@NonNull String id) {
        this.id = id;
    }

    /**
     * Gets the session's ID
     *
     * @return String representing the session ID
     */
    @NonNull
    public String getId() {
        return id;
    }

    /**
     * Sets the session's ID
     *
     * @param id String representing the session ID
     */
    public void setId(@NonNull String id) {
        this.id = id;
    }
}
