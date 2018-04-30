package workout.sergian.com.workout.Factory

import workout.sergian.com.workout.Factory.DataFactory.Factory.randomInt
import workout.sergian.com.workout.Factory.DataFactory.Factory.randomString
import workout.sergian.com.workout.models.Workout

/**
 * Created by Shronas on 4/29/18.
 * Factory class for Workout-related instances
 */
class WorkoutFactory {
    companion object Factory {
        fun makeWorkoutEntity(): Workout {
            return Workout(randomInt(), randomString())
        }

        fun makeWorkoutEntityList(count: Int): List<Workout> {
            val workoutEntities = mutableListOf<Workout>()
            repeat(count) {
                workoutEntities.add(makeWorkoutEntity())
            }
            return workoutEntities
        } // makeWorkoutEntityList()
    } // Factory
} // WorkoutFactory