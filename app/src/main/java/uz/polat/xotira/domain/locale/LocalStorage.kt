package uz.polat.xotira.domain.locale

import android.content.Context
import androidx.annotation.Keep

//@Keep
class LocalStorage(context: Context) : SharedPreferenceHelper(context) {

//    companion object{
//        private var instance:LocalStorage? = null
//
//        fun getInstance():LocalStorage{
//            if (instance == null){
//                instance = LocalStorage(context)
//            }
//        }
//    }

    var fruit_easy: Boolean by booleans(false)
    var fruit_normal: Boolean by booleans(false)
    var fruit_hard: Boolean by booleans(false)


    var animal_easy: Boolean by booleans(false)
    var animal_normal: Boolean by booleans(false)
    var animal_hard: Boolean by booleans(false)


}

