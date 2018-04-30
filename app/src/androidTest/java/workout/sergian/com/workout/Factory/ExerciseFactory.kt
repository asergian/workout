package workout.sergian.com.workout.Factory

import workout.sergian.com.workout.Factory.DataFactory.Factory.randomInt
import workout.sergian.com.workout.Factory.DataFactory.Factory.randomString
import workout.sergian.com.workout.models.Exercise

/**
 * Created by Shronas on 4/29/18.
 * Factory class for Workout-related instances
 */
class ExerciseFactory {
    companion object Factory {
        fun makeExerciseEntity(): Exercise {
            return Exercise(randomInt(), randomString())
        }

        fun makeExerciseEntityList(count: Int): List<Exercise> {
            val exerciseEntities = mutableListOf<Exercise>()
            repeat(count) {
                exerciseEntities.add(makeExerciseEntity())
            }
            return exerciseEntities
        } // makeExerciseEntityList()
    } // Factory
} // ExerciseFactory