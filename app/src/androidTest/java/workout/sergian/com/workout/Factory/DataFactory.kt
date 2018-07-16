package workout.sergian.com.workout.Factory

import java.lang.Math.random
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.roundToInt

/**
 * Factory class for random data
 */

class DataFactory {

    companion object Factory {
        var categories = arrayOf("Legs", "Chest", "Shoulders", "Arms")

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        fun randomInt(min_inclusive: Int, max_exclusive: Int): Int {
            return ThreadLocalRandom.current().nextInt(min_inclusive, max_exclusive)
        }

        fun randomLong(): Long {
            return randomInt().toLong()
        }

        fun randomString(): String {
            return randomInt().toString()
        }

        fun randomCategory() : String {
            val index = Random().nextInt(categories.size)
            return categories[index]
        }

        fun randomBoolean(): Boolean {
            return Math.random() < 0.5
        }

        fun makeStringList(count: Int): List<String> {
            val items: MutableList<String> = mutableListOf()
            repeat(count) {
                items.add(randomUuid())
            }
            return items
        }

    }

}