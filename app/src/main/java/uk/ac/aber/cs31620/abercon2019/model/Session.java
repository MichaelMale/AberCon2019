package uk.ac.aber.cs31620.abercon2019.model;

import java.util.Calendar;
import java.util.Date;

public class Session {
    private String id;
    private String title;
    private String content;
    private Location locationId;
    private String sessionDate;
    private int sessionOrder;
    private String timeStart;
    private String timeEnd;
    private SessionType sessionType;
    private Speaker speaker;

    public Session(String id, String title, String content, Location locationId, String sessionDate,
                   int sessionOrder, String timeStart, String timeEnd, SessionType sessionType,
                   Speaker speaker) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.locationId = locationId;
        this.sessionDate = sessionDate;
        this.sessionOrder = sessionOrder;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.sessionType = sessionType;
        this.speaker = speaker;
    }

    // DELETE THE CODE BLOCK BELOW BEFORE SUBMISSION
    private static Location physMain = new Location(
            "physmain",
            "Physics Main",
            (float) 52.415941,
            (float) -4.065818,
            "Main lecture theatre for the conference, near where registration happens."
    );
    private static Speaker steveScott = new Speaker(
            "SteveScott",
            "Steve Scott",
            "Scotty is legendary in the MAC community as the organiser of the sadly" +
                    "defunct NSConference, but still appearing live at iOSDevUK",
            "macdevnet"
    );
    public static final Session[] sessions = {
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of softwar",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of softwa",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of softw",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of soft",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of sof",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of so",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of s",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of ",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs of",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            ),
            new Session(
                    "havoc",
                    "Cry Havoc! and let slip the dogs o",
                    "Scotty explores the wonder of the software industry",
                    physMain,
                    "2019-12-13",
                    5,
                    "12:00",
                    "12:40",
                    SessionType.TALK,
                    steveScott
            )
    };
    // DELETE THE CODE BLOCK ABOVE BEFORE SUBMISSION

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Location getLocationId() {
        return locationId;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public int getSessionOrder() {
        return sessionOrder;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public Speaker getSpeaker() {
        return speaker;
    }

}
