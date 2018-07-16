package workout.sergian.com.workout.controllers

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import workout.sergian.com.workout.models.Exercise
import workout.sergian.com.workout.models.Plan
import workout.sergian.com.workout.models.Workout

/**
 * Data Access Object for plan database table
 */
@Dao
interface PlanDao {
    // get al plans
    @Query("SELECT * FROM plans")
    fun getAll() : List<Plan>

    @Query("SELECT * FROM plans WHERE workoutId = :workoutId")
    fun getWorkoutPlan(workoutId: Int?) : List<Plan>

    @Query("SELECT * FROM plans WHERE exerciseId = :exerciseId")
    fun getExercisePlans(exerciseId: Int?) : List<Plan>

    // get all exercises for a specified workout
    @Query("SELECT exercises.uid, exercises.name, exercises.category, plans.sets, plans.reps " +
            "FROM exercises INNER JOIN plans " +
            "ON exercises.uid = plans.exerciseId " +
            "WHERE plans.workoutId = :workoutId " +
            "ORDER BY plans.exercise_order ASC")
    fun getPlanExercises(workoutId : Int?) : List<Exercise>

    // TODO: get all workouts for a specified exercise
    @Query("SELECT DISTINCT workouts.uid, workouts.name FROM workouts INNER JOIN plans " +
            "ON workouts.uid = plans.workoutId " +
            "WHERE plans.exerciseId = :exerciseId " +
            "ORDER BY workouts.name ASC")
    fun getWorkoutsWithExercise(exerciseId : Int?) : List<Workout>

    // TODO: get plans with exercise

    // insert plan
    @Insert(onConflict = REPLACE)
    fun addPlans(plans : List<Plan>)

    // update plan
    // TODO: Do we need this? Insert + onConflict same thing?
    @Update(onConflict = REPLACE)
    fun updatePlans(plans : List<Plan>)

    // delete plan
    @Delete
    fun deletePlans(plans : List<Plan>)

    // delete a workout plan
    @Query("DELETE from plans WHERE workoutId = :workoutId")
    fun deleteWorkoutPlans(workoutId : Int)
} // PlanDao