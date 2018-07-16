package workout.sergian.com.workout.controllers

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import workout.sergian.com.workout.models.Exercise

/**
 * Created by Shronas on 4/28/18.
 * Data Access Object for exercise database table
 */

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises")
    fun getAll() : List<Exercise>

    @Query("SELECT * FROM exercises WHERE uid = :uid")
    fun getExercise(uid : Int?) : Exercise

    @Query("SELECT * FROM exercises WHERE category = :category")
    fun getExercisesByCategory(category : String?) : List<Exercise>

    @Insert(onConflict = REPLACE)
    fun insertExercises(vararg exercise: Exercise)

    // TODO: Do we need this? Insert + onConflict same thing?
    @Update(onConflict = REPLACE)
    fun updateExercises(vararg exercise : Exercise)

    @Query("DELETE from exercises")
    fun deleteAll()

    @Delete
    fun deleteExercise(vararg exercise : Exercise)
} // ExerciseDao