package uk.ac.aber.cs31620.abercon2019.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

import uk.ac.aber.cs31620.abercon2019.model.util.DateTimeConverter;
import uk.ac.aber.cs31620.abercon2019.model.util.SessionTypeConverter;

/**
 * This is a temporary implementation of Session that transfers the database 'as-is', that is,
 * without converting location id's to their respective locations. This will be changed hopefully
 * but I just wanted to check if I could correctly migrate an SQLite database.
 *
 * @author Michael Male
 * @version 0.1 ALPHA
 */
@Entity(tableName = "sessions")
@TypeConverters({DateTimeConverter.class, SessionTypeConverter.class})
public class Session implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String content;
    private String locationId;
    private Date sessionDate;
    private int sessionOrder;
    private String timeStart;
    private String timeEnd;
    private SessionType sessionType;
    private String speakerId;

    /**
     * Constructor for objects of class Session.
     *
     * @param id           The session ID
     * @param title        The title of the session
     * @param content      The session's content
     * @param locationId   The location ID
     * @param sessionDate  The date that the session is to be held
     * @param sessionOrder The order that the session should be in
     * @param timeStart    The time that the session is beginning
     * @param timeEnd      The time that the session is concluding
     * @param sessionType  The type of session that is being held
     * @param speakerId    The session's speaker ID
     */
    public Session(String id, String title, String content, String locationId, Date sessionDate,
                   int sessionOrder, String timeStart, String timeEnd, SessionType sessionType,
                   String speakerId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.locationId = locationId;
        this.sessionDate = sessionDate;
        this.sessionOrder = sessionOrder;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sessionType = sessionType;
        this.speakerId = speakerId;
    }

    /**
     * Gets the session ID
     *
     * @return String containing Session ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the session's title
     *
     * @return String containing Session title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the session's content
     *
     * @return String containing Session content
     */
    public String getContent() {
        return content;
    }

    /**
     * Gets the location ID
     *
     * @return String containing Session's location ID
     */
    public String getLocationId() {
        return locationId;
    }

    /**
     * Gets the session's date
     *
     * @return Date containing session's date
     */
    public Date getSessionDate() {
        return sessionDate;
    }

    /**
     * Gets the session's order
     *
     * @return Integer containing the session's order
     */
    public int getSessionOrder() {
        return sessionOrder;
    }

    /**
     * Gets the start time of the session
     *
     * @return String containing start time of the session
     */
    public String getTimeStart() {
        return timeStart;
    }

    /**
     * Gets the end time of the session
     *
     * @return String containing the end time of the session
     */
    public String getTimeEnd() {
        return timeEnd;
    }

    /**
     * Gets the session type
     *
     * @return Enum corresponding to the session's type
     */
    public SessionType getSessionType() {
        return sessionType;
    }

    /**
     * Gets the speaker ID
     *
     * @return String containing the speaker ID
     */
    public String getSpeakerId() {
        return speakerId;
    }


}