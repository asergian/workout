package workout.sergian.com.workout

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import workout.sergian.com.workout.models.DateConverter
import workout.sergian.com.workout.models.Exercise
import workout.sergian.com.workout.models.Workout

/**
 * Created by Shronas on 4/28/18.
 */

@Database(entities = [Workout::class, Exercise::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun WorkoutDao(): WorkoutDao
    abstract fun ExerciseDao(): ExerciseDao

    companion object {
        private var INSTANCE: WorkoutDatabase? = null

        fun getInstance(context: Context): WorkoutDatabase? {
            if (INSTANCE == null) {
                synchronized(WorkoutDatabase::class) {
                    //Room.inMemoryDatabaseBuilder()
                    INSTANCE = Room.databaseBuilder(context,
                            WorkoutDatabase::class.java, "workouts.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}