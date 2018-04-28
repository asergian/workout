package workout.sergian.com.workout

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Shronas on 4/28/18.
 */
@Entity(tableName = "workouts")
data class Workout(@PrimaryKey(autoGenerate = true) var id: Int?,
                   @ColumnInfo(name = "name") var name: String) {
    constructor():this(null,"")
    // TODO: Add validation (name should not be empty)
}