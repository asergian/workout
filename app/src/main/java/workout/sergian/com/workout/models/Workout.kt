package workout.sergian.com.workout.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shronas on 4/28/18.
 * Workouts table
 * Col 1: id (primary key, autogenerate - increment)
 * Col 2: name
 */

@Entity(tableName = "workouts",
        indices = [Index(name = "name", value = "name", unique = true)])
data class Workout(@PrimaryKey(autoGenerate = true) var uid: Int?,
                   @ColumnInfo(name = "name") var name: String)