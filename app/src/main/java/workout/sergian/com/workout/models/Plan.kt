package workout.sergian.com.workout.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Index

/*
 * Plan: join table between Workout & Exercises (many-many)
 * Cols: exerciseId, workoutId
 */

@Entity(tableName = "plans",
        primaryKeys = ["workoutId", "exerciseId"],
        foreignKeys = [ForeignKey(entity = Workout::class,
                                    parentColumns = ["uid"],
                                    childColumns = ["workoutId"],
                                    onDelete = CASCADE,
                                    onUpdate = CASCADE),
                        ForeignKey(entity = Exercise::class,
                                parentColumns = ["uid"],
                                childColumns = ["exerciseId"],
                                onDelete = CASCADE,
                                onUpdate = CASCADE)
                        ],
        indices = [Index("workoutId"), Index("exerciseId")])
data class Plan(var workoutId : Int,
                var exerciseId : Int,
                var exercise_order : Int,
                var sets : Int,
                var reps : Int)