package workout.sergian.com.workout

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import workout.sergian.com.workout.Factory.DataFactory.Factory.randomInt
import workout.sergian.com.workout.Factory.ExerciseFactory.Factory.makeExerciseEntityList
import workout.sergian.com.workout.Factory.PlanFactory.Factory.makePlanEntities
import workout.sergian.com.workout.Factory.WorkoutFactory.Factory.makeWorkoutEntityList
import workout.sergian.com.workout.models.Exercise
import workout.sergian.com.workout.models.Plan
import workout.sergian.com.workout.models.Workout

/**
 * Test CRUD operations for Workout Entity/DAO
 * Test: Create a workout plan
 * Test: Update a workout plan (delete all rows, reinsert all)
 * Test: Delete a plan (deletes row)
 * Test: Delete workout (deletes all plans with same workout id)
 * Test: Delete exercise (deletes all plans with same exercise id)
 */

@RunWith(AndroidJUnit4::class)
class PlanTest {
    private lateinit var workoutsDatabase : WorkoutDatabase

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getTargetContext()
        workoutsDatabase = Room.inMemoryDatabaseBuilder(context, WorkoutDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        workoutsDatabase.close()
    }

    @Test // Test: Create a workout plan
    fun insertPlanAddsPlan() {
        // test that inserted rows equals number inserted
        val cachedWorkouts: List<Workout> = makeWorkoutEntityList(2)
        val cachedExercises: List<Exercise> = makeExerciseEntityList(2)
        val cachedPlans: List<Plan> = makePlanEntities(cachedWorkouts, cachedExercises)

        cachedExercises.forEach { workoutsDatabase.ExerciseDao().insertExercises(it) }
        cachedWorkouts.forEach { workoutsDatabase.WorkoutDao().insertWorkout(it) }
        workoutsDatabase.PlanDao().addPlans(cachedPlans)

        val retrievedPlans = workoutsDatabase.PlanDao().getAll()

        assertTrue(retrievedPlans.isNotEmpty())
        assertTrue(retrievedPlans.sortedWith(compareBy({it.workoutId}, {it.exerciseId}))
                == cachedPlans.sortedWith(compareBy({it.workoutId}, {it.exerciseId})))
    }

    @Test // Test: Update a workout plan (delete all rows, reinsert all)
    fun updatePlanSavesData() {
        // test that update deletes old rows and reinserts "clean" data
        val cachedWorkouts: List<Workout> = makeWorkoutEntityList(2)
        val cachedExercises: List<Exercise> = makeExerciseEntityList(4)
        val cachedPlans: List<Plan> = makePlanEntities(cachedWorkouts, cachedExercises)

        cachedExercises.forEach { workoutsDatabase.ExerciseDao().insertExercises(it) }
        cachedWorkouts.forEach { workoutsDatabase.WorkoutDao().insertWorkout(it) }
        workoutsDatabase.PlanDao().addPlans(cachedPlans)

        // do the update
        cachedPlans[0].reps = cachedPlans[0].reps * 2
        cachedPlans[0].sets = cachedPlans[0].sets + 1

        workoutsDatabase.PlanDao().updatePlans(cachedPlans)

        // check database
        val retrievedPlans = workoutsDatabase.PlanDao().getAll()
        assertTrue(retrievedPlans.sortedWith(compareBy({it.workoutId}, {it.exerciseId}))
                == cachedPlans.sortedWith(compareBy({it.workoutId}, {it.exerciseId})))
    }

    @Test // Test: Delete a workout plan (deletes a row)
    fun deletePlanDeletesData() {
        // test that the given rows is removed
        val cachedWorkouts: List<Workout> = makeWorkoutEntityList(2)
        val cachedExercises: List<Exercise> = makeExerciseEntityList(4)
        var cachedPlans: List<Plan> = makePlanEntities(cachedWorkouts, cachedExercises)

        cachedExercises.forEach { workoutsDatabase.ExerciseDao().insertExercises(it) }
        cachedWorkouts.forEach { workoutsDatabase.WorkoutDao().insertWorkout(it) }
        workoutsDatabase.PlanDao().addPlans(cachedPlans)

        // delete row
        val workoutId = cachedPlans[0].workoutId
        val exerciseId = cachedPlans[0].exerciseId
        val testPlan = mutableListOf<Plan>()
        testPlan.add(cachedPlans[0])

        workoutsDatabase.PlanDao().deletePlans(testPlan)
        cachedPlans = cachedPlans.drop(1)

        // make sure it's deleted
        val retrievedPlans = workoutsDatabase.PlanDao().getAll()
        assertTrue(retrievedPlans.sortedWith(compareBy({it.workoutId}, {it.exerciseId}))
                == cachedPlans.sortedWith(compareBy({it.workoutId}, {it.exerciseId})))
    }

    @Test // Test: Delete a workout plan (deletes all rows for workout)
    fun deleteWorkoutPlanDeletesData() {
        // test that all rows for workout id get removed
        val cachedWorkouts: List<Workout> = makeWorkoutEntityList(2)
        val cachedExercises: List<Exercise> = makeExerciseEntityList(4)
        var cachedPlans: List<Plan> = makePlanEntities(cachedWorkouts, cachedExercises)

        cachedExercises.forEach { workoutsDatabase.ExerciseDao().insertExercises(it) }
        cachedWorkouts.forEach { workoutsDatabase.WorkoutDao().insertWorkout(it) }
        workoutsDatabase.PlanDao().addPlans(cachedPlans)

        // delete row
        val workoutId = cachedPlans[0].workoutId

        var retrievedPlans = workoutsDatabase.PlanDao().getWorkoutPlan(workoutId)
        assertTrue(retrievedPlans.isNotEmpty())

        workoutsDatabase.PlanDao().deleteWorkoutPlans(workoutId)

        // make sure it's deleted
        retrievedPlans = workoutsDatabase.PlanDao().getWorkoutPlan(workoutId)
        assertTrue(retrievedPlans.isEmpty())
    }

    @Test // Test: Delete workout (deletes all plans with same workout id)
    fun deleteWorkoutCascadesToPlan() {
        // test that deleting a workout removes associated plan
        val cachedWorkouts: List<Workout> = makeWorkoutEntityList(2)
        val cachedExercises: List<Exercise> = makeExerciseEntityList(4)
        val cachedPlans: List<Plan> = makePlanEntities(cachedWorkouts, cachedExercises)

        cachedExercises.forEach { workoutsDatabase.ExerciseDao().insertExercises(it) }
        cachedWorkouts.forEach { workoutsDatabase.WorkoutDao().insertWorkout(it) }
        workoutsDatabase.PlanDao().addPlans(cachedPlans)

        // delete workout
        val workout = cachedWorkouts[0]

        var retrievedPlans = workoutsDatabase.PlanDao().getWorkoutPlan(workout.uid)
        assertTrue(retrievedPlans.isNotEmpty())

        workoutsDatabase.WorkoutDao().deleteWorkout(workout)

        retrievedPlans = workoutsDatabase.PlanDao().getWorkoutPlan(workout.uid)
        assertTrue(retrievedPlans.isEmpty())
    }

    @Test // Test: Delete exercise (deletes all plans with same exercise id)
    fun deleteExerciseCascadesToPlans() {
        // test that deleting exercise removes it from all plans
        val cachedWorkouts: List<Workout> = makeWorkoutEntityList(2)
        val cachedExercises: List<Exercise> = makeExerciseEntityList(4)
        val cachedPlans: List<Plan> = makePlanEntities(cachedWorkouts, cachedExercises)

        cachedExercises.forEach { workoutsDatabase.ExerciseDao().insertExercises(it) }
        cachedWorkouts.forEach { workoutsDatabase.WorkoutDao().insertWorkout(it) }
        workoutsDatabase.PlanDao().addPlans(cachedPlans)

        // delete exercise
        val exercise = cachedExercises[0]

        var retrievedPlans = workoutsDatabase.PlanDao().getExercisePlans(exercise.uid)
        assertTrue(retrievedPlans.isNotEmpty())

        workoutsDatabase.ExerciseDao().deleteExercise(exercise)

        retrievedPlans = workoutsDatabase.PlanDao().getExercisePlans(exercise.uid)
        assertTrue(retrievedPlans.isEmpty())
    }
} // PlanTest()