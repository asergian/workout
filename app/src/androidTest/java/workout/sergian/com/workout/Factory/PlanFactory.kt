package workout.sergian.com.workout.Factory

import workout.sergian.com.workout.Factory.DataFactory.Factory.randomInt
import workout.sergian.com.workout.models.Exercise
import workout.sergian.com.workout.models.Plan
import workout.sergian.com.workout.models.Workout

/**
 * Created by Shronas on 7/15/18.
 * Factory class for Plan-related instances
 */
class PlanFactory {
    companion object Factory {
        // make a plan, givem the workout and exercise
        fun makePlanEntity(workoutId: Int, exerciseId: Int): Plan {
            return Plan(workoutId, exerciseId, randomInt(), randomInt(), randomInt())
        }

/*        fun makePlanEntityForWorkout(workoutId: Int, exercises: List<Exercise>): Plan {
            val exerciseId: Int = exercises.get(randomInt(0, exercises.size)).uid
            return makePlanEntity(workoutId, exerciseId)
        }

        fun makePlanEntitiesForWorkout(workoutId: Int, exercises: List<Exercise>): List<Plan> {
            val planEntities = mutableListOf<Plan>()

            repeat(randomInt(0, exercises.size)) {
                planEntities.add(makePlanEntityForWorkout(workoutId, exercises))
            }

            return planEntities
        }

        fun makePlanEntities(workouts: List<Workout>, exercises: List<Exercise>): List<Plan> {
            val planEntities = mutableListOf<Plan>()
            var workoutId: Int

            repeat(randomInt(0, workouts.size)) {
                workoutId = workouts.get(randomInt(0, workouts.size)).uid
                for(plan in makePlanEntitiesForWorkout(workoutId, exercises))
                    planEntities.add(plan)
            }

            return planEntities
        }*/

        fun makePlanEntities(workouts: List<Workout>, exercises: List<Exercise>): List<Plan> {
            val planEntities = mutableListOf<Plan>()

            for(workout in workouts)
                for(exercise in exercises)
                    planEntities.add(makePlanEntity(workout.uid, exercise.uid))

            return planEntities
        }
    } // Factory
} // PlanFactory