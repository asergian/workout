package workout.sergian.com.workout.controllers

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import workout.sergian.com.workout.models.Workout

/**
 * Created by Shronas on 4/28/18.
 * Data Access Object for workouts database table
 */

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workouts")
    fun getAll() : List<Workout>

    @Query("SELECT * FROM workouts WHERE uid = :uid")
    fun getWorkout(uid : Int?) : Workout

    @Insert(onConflict = REPLACE)
    fun insertWorkout(workout: Workout)

    @Update(onConflict = REPLACE)
    fun updateWorkout(workout : Workout)

    @Query("DELETE from workouts")
    fun deleteAll()

    @Delete
    fun deleteWorkout(workout : Workout)
} // WorkoutDao