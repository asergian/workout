package workout.sergian.com.workout

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import workout.sergian.com.workout.models.Workout

/**
 * Created by Shronas on 4/28/18.
 * Data Access Object for workouts database table
 */

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts")
    fun getAll() : List<Workout>

    @Query("SELECT * FROM workouts WHERE uid EQUALS :uid")
    fun getWorkout(uid : Int) : Workout

    @Insert(onConflict = REPLACE)
    fun insert(workout: Workout)

    @Query("UPDATE workouts SET name = :name WHERE uid = :uid")
    fun updateWorkout(name : String, uid : Int)

    //@Query("DELETE from workouts")
    //fun deleteAll()

    @Query("DELETE FROM workouts WHERE uid EQUALS :uid")
    fun deleteWorkout(uid : Int)
} // WorkoutDao