package workout.sergian.com.workout

import android.support.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.util.Log
import org.junit.Assert.*
import workout.sergian.com.workout.Factory.WorkoutFactory

/**
 * Created by Shronas on 4/29/18.
 * Test CRUD operations for Workout Entity/DAO
 *
 * Test: Insert 1 Workout, ensure it exists in table
 * Test: Insert 3 Workouts, Get all, ensure correct number in table
 * Test: Insert 1 Workout, Get that id, ensure that name is persisted
 * Test: Insert 2 Workouts, delete all, ensure table is empty
 * Test: Insert 2 Workouts, update one name, get all, ensure names are accurate
 * Test: Insert 2 workouts, delete one, ensure proper one was deleted
 */

@RunWith(AndroidJUnit4::class)
class WorkoutInstrumentedTest {
    private lateinit var workoutsDatabase : WorkoutDatabase

    @Before
    fun initDb() {
        val context = InstrumentationRegistry.getTargetContext()
        workoutsDatabase = Room.inMemoryDatabaseBuilder(context, WorkoutDatabase::class.java).build()
    }

    @After
    //@Throws(IOException::class)
    fun closeDb() {
        workoutsDatabase.close()
    }

    @Test //Test: Insert 1 Workout, ensure it exists in table
    fun insertWorkoutAddsWorkout() {
        val cachedWorkout = WorkoutFactory.makeWorkoutEntity()

        workoutsDatabase.WorkoutDao().insertWorkout(cachedWorkout)

        val retrievedWorkouts = workoutsDatabase.WorkoutDao().getAll()
        assertTrue(retrievedWorkouts.isNotEmpty())
    }

    @Test // Test: Insert 3 Workouts, Get all, ensure correct number in table
    fun insertManyWorkoutsAddsAll() {
        val cachedWorkouts = WorkoutFactory.makeWorkoutEntityList(3)

        cachedWorkouts.forEach {
            workoutsDatabase.WorkoutDao().insertWorkout(it)
        }

        val retrievedWorkouts = workoutsDatabase.WorkoutDao().getAll()
        assertTrue(retrievedWorkouts == cachedWorkouts.sortedWith(compareBy({it.uid}, {it.uid})))
    }

    @Test // Test: Insert 1 Workout, Get that id, ensure that name is persisted
    fun insertWorkoutSavesData() {
        val cachedWorkout = WorkoutFactory.makeWorkoutEntity()

        workoutsDatabase.WorkoutDao().insertWorkout(cachedWorkout)

        val retrievedWorkout = workoutsDatabase.WorkoutDao().getWorkout(cachedWorkout.uid)
        assertTrue(retrievedWorkout.name == cachedWorkout.name)
    }

    @Test // Test: Insert 2 Workouts, delete all, ensure table is empty
    fun deleteAllEmptiesTable() {
        val cachedWorkouts = WorkoutFactory.makeWorkoutEntityList(2)

        cachedWorkouts.forEach {
            workoutsDatabase.WorkoutDao().insertWorkout(it)
        }

        workoutsDatabase.WorkoutDao().deleteAll()

        val retrievedWorkouts = workoutsDatabase.WorkoutDao().getAll()
        assertTrue(retrievedWorkouts.isEmpty())
    }

    @Test // Test: Insert 2 Workouts, update one name, get all, ensure names are accurate
    fun updateNameSavesData() {
        val cachedWorkouts = WorkoutFactory.makeWorkoutEntityList(2)
        cachedWorkouts.forEach {
            workoutsDatabase.WorkoutDao().insertWorkout(it)
        }

        cachedWorkouts[1].name = "Test Name"
        workoutsDatabase.WorkoutDao().updateWorkout(cachedWorkouts[1])

        val retrievedWorkouts = workoutsDatabase.WorkoutDao().getAll()
        assertTrue(retrievedWorkouts == cachedWorkouts.sortedWith(compareBy({it.uid}, {it.uid})))
    }

    @Test // Test: Insert 2 workouts, delete one, ensure proper one was deleted
    fun deleteWorkoutDeletesData() {
        val cachedWorkouts = WorkoutFactory.makeWorkoutEntityList(2)
        cachedWorkouts.forEach {
            workoutsDatabase.WorkoutDao().insertWorkout(it)
        }

        val uid = cachedWorkouts[1].uid
        workoutsDatabase.WorkoutDao().deleteWorkout(cachedWorkouts[1])

        val retrievedWorkouts = workoutsDatabase.WorkoutDao().getAll()
        assertNull(workoutsDatabase.WorkoutDao().getWorkout(uid))
        assertTrue(retrievedWorkouts.size == 1)
    }
} // WorkoutInstrumentedTest