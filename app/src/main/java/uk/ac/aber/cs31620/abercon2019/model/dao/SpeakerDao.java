package uk.ac.aber.cs31620.abercon2019.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import uk.ac.aber.cs31620.abercon2019.model.Speaker;

@Dao
public interface SpeakerDao {

    @Query("SELECT * FROM speakers")
    LiveData<List<Speaker>> getAllSpeakers();

    @Query("SELECT * FROM speakers WHERE :id = id")
    LiveData<Speaker> fetchSpeakerById(String id);
}
