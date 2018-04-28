package workout.sergian.com.workout

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by Shronas on 4/28/18.
 */

@Dao
interface WorkoutDao {
    @Query("SELECT * from workouts")
    fun getAll() : List<Workout>

    @Insert(onConflict = REPLACE)
    fun insert(workout: Workout)

    @Query("DELETE from workouts")
    fun deleteAll()
}