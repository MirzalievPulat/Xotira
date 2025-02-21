package uz.polat.xotira.util

import android.view.View
import android.widget.ImageView
import uz.polat.xotira.data.CardData
import uz.polat.xotira.R


fun ImageView.openCard(finishAnim: () -> Unit = {}) {
    this.animate()
        .setDuration(300)
        .rotationY(90f)
        .withEndAction {
            this.setImageResource((tag as CardData).imageRes)
            this.rotationY = -90f
            this.animate()
                .setDuration(300)
                .rotationY(0f)
                .withEndAction {
                    finishAnim.invoke()
                }.start()
        }
        .start()
}

fun ImageView.closeCard(finishAnim: () -> Unit = {}) {
    this.animate()
        .setDuration(600)
        .rotationY(-90f)
        .withEndAction {
            this.setImageResource(R.drawable.bg_question)
            this.rotationY = 90f
            this.animate()
                .setDuration(300)
                .rotationY(0f)
                .withEndAction {
                    finishAnim.invoke()
                }.start()
        }
        .start()
}


fun ImageView.hideCard(finishAnim: () -> Unit = {}) {
    this.animate()
        .setDuration(300)
        .scaleX(0f)
        .scaleY(0f)
        .withEndAction {
            this.visibility = View.GONE
            finishAnim.invoke()
        }
        .start()
}

var lastTime = 0L

fun canClick():Boolean{
    val currentTime = System.currentTimeMillis()
    val result = currentTime - lastTime >= 500
    lastTime = currentTime
    return result
}
