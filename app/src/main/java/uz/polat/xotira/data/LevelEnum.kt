package uz.polat.xotira.data

import androidx.annotation.Keep

@Keep
enum class LevelEnum(val horCount: Int, val verCount: Int) {
    EASY(4, 3),
    MEDIUM(6, 4),
    HARD(8, 6)
}