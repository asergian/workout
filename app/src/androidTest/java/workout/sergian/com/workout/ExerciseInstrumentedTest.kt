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
import workout.sergian.com.workout.Factory.ExerciseFactory

/**
 * Created by Shronas on 4/29/18.
 * Test CRUD operations for Exercise Entity/DAO
 *
 * Test: Insert 1 exercise, ensure it exists in table
 * Test: Insert 3 exercises, Get all, ensure correct number in table
 * Test: Insert 1 exercise, Get that id, ensure that name is persisted
 * Test: Insert 2 exercises, delete all, ensure table is empty
 * Test: Insert 2 exercises, update one name, get all, ensure names are accurate
 * Test: Insert 2 exercises, delete one, ensure proper one was deleted
 */

@RunWith(AndroidJUnit4::class)
class ExerciseInstrumentedTest {
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

    @Test //Test: Insert 1 exercise, ensure it exists in table
    fun insertExerciseAddsExercise() {
        val cachedExercise = ExerciseFactory.makeExerciseEntity()

        workoutsDatabase.ExerciseDao().insertExercises(cachedExercise)

        val retrievedExercises = workoutsDatabase.ExerciseDao().getAll()
        assertTrue(retrievedExercises.isNotEmpty())
    }

    @Test // Test: Insert 3 exercises, Get all, ensure correct number in table
    fun insertManyExercisesAddsAll() {
        val cachedExercises = ExerciseFactory.makeExerciseEntityList(3)

        cachedExercises.forEach {
            workoutsDatabase.ExerciseDao().insertExercises(it)
        }

        val retrievedExercises = workoutsDatabase.ExerciseDao().getAll()
        assertTrue(retrievedExercises == cachedExercises.sortedWith(compareBy({it.uid}, {it.uid})))
    }

    @Test // Test: Insert 1 exercise, Get that id, ensure that name is persisted
    fun insertExerciseSavesData() {
        val cachedExercise = ExerciseFactory.makeExerciseEntity()

        workoutsDatabase.ExerciseDao().insertExercises(cachedExercise)

        val retrievedExercise = workoutsDatabase.ExerciseDao().getExercise(cachedExercise.uid)
        assertTrue(retrievedExercise.name == cachedExercise.name)
    }

    @Test // Test: Insert 2 exercises, delete all, ensure table is empty
    fun deleteAllEmptiesTable() {
        val cachedExercises = ExerciseFactory.makeExerciseEntityList(2)

        cachedExercises.forEach {
            workoutsDatabase.ExerciseDao().insertExercises(it)
        }

        workoutsDatabase.ExerciseDao().deleteAll()

        val retrievedExercises = workoutsDatabase.ExerciseDao().getAll()
        assertTrue(retrievedExercises.isEmpty())
    }

    @Test // Test: Insert 2 exercises, update one name, get all, ensure names are accurate
    fun updateNameSavesData() {
        val cachedExercises = ExerciseFactory.makeExerciseEntityList(2)
        cachedExercises.forEach {
            workoutsDatabase.ExerciseDao().insertExercises(it)
        }

        cachedExercises[1].name = "Test Name"
        workoutsDatabase.ExerciseDao().updateExercises(cachedExercises[1])

        val retrievedExercises = workoutsDatabase.ExerciseDao().getAll()
        assertTrue(retrievedExercises == cachedExercises.sortedWith(compareBy({it.uid}, {it.uid})))
    }

    @Test // Test: Insert 2 exercises, delete one, ensure proper one was deleted
    fun deleteExerciseDeletesData() {
        val cachedExercises = ExerciseFactory.makeExerciseEntityList(2)
        cachedExercises.forEach {
            workoutsDatabase.ExerciseDao().insertExercises(it)
        }

        val uid = cachedExercises[1].uid
        workoutsDatabase.ExerciseDao().deleteExercise(cachedExercises[1])

        val retrievedExercises = workoutsDatabase.ExerciseDao().getAll()
        assertNull(workoutsDatabase.ExerciseDao().getExercise(uid))
        assertTrue(retrievedExercises.size == 1)
    }
} // ExerciseInstrumentedTest