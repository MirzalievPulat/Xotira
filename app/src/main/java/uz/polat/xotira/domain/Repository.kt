package uz.polat.xotira.domain

import uz.polat.xotira.data.CardData
import uz.polat.xotira.R
import uz.polat.xotira.data.LevelEnum
import uz.polat.xotira.data.TypeEnum

class Repository {

    companion object{
        private var repository:Repository? = null

        fun getInstance():Repository{
            if (repository == null){
                repository = Repository()
            }
            return repository!!
        }
    }

    private val list = ArrayList<CardData>()


    fun getCardDataByLevel(type:TypeEnum,level: LevelEnum): List<CardData> {

        prepareList(type)

        val count = level.horCount * level.verCount
        list.shuffle()
        val result = ArrayList<CardData>(count)
        val l = list.subList(0, count / 2)
        result.addAll(l)
        result.addAll(l)
        result.shuffle()
        return result
    }

    private fun prepareList(type: TypeEnum) {
        list.clear()
        when(type){
            TypeEnum.FRUIT -> {
                list.add(CardData(1, R.drawable.f1))
                list.add(CardData(2, R.drawable.f2))
                list.add(CardData(3, R.drawable.f3))
                list.add(CardData(4, R.drawable.f4))
                list.add(CardData(5, R.drawable.f5))
                list.add(CardData(6, R.drawable.f6))
                list.add(CardData(7, R.drawable.f7))
                list.add(CardData(8, R.drawable.f8))
                list.add(CardData(9, R.drawable.f9))
                list.add(CardData(10, R.drawable.f10))
                list.add(CardData(11, R.drawable.f11))
                list.add(CardData(12, R.drawable.f12))
                list.add(CardData(13, R.drawable.f13))
                list.add(CardData(14, R.drawable.f14))
                list.add(CardData(15, R.drawable.f15))
                list.add(CardData(16, R.drawable.f16))
                list.add(CardData(17, R.drawable.f17))
                list.add(CardData(18, R.drawable.f18))
                list.add(CardData(19, R.drawable.f19))
                list.add(CardData(20, R.drawable.f20))
                list.add(CardData(21, R.drawable.f21))
                list.add(CardData(22, R.drawable.f22))
                list.add(CardData(23, R.drawable.f23))
                list.add(CardData(24, R.drawable.f24))
            }
            TypeEnum.ANIMAL ->{
                list.add(CardData(1, R.drawable.a1))
                list.add(CardData(2, R.drawable.a2))
                list.add(CardData(3, R.drawable.a3))
                list.add(CardData(4, R.drawable.a4))
                list.add(CardData(5, R.drawable.a5))
                list.add(CardData(6, R.drawable.a6))
                list.add(CardData(7, R.drawable.a7))
                list.add(CardData(8, R.drawable.a8))
                list.add(CardData(9, R.drawable.a9))
                list.add(CardData(10, R.drawable.a10))
                list.add(CardData(11, R.drawable.a11))
                list.add(CardData(12, R.drawable.a12))
                list.add(CardData(13, R.drawable.a13))
                list.add(CardData(14, R.drawable.a14))
                list.add(CardData(15, R.drawable.a15))
                list.add(CardData(16, R.drawable.a16))
                list.add(CardData(17, R.drawable.a17))
                list.add(CardData(18, R.drawable.a18))
                list.add(CardData(19, R.drawable.a19))
                list.add(CardData(20, R.drawable.a20))
                list.add(CardData(21, R.drawable.a21))
                list.add(CardData(22, R.drawable.a22))
                list.add(CardData(23, R.drawable.a23))
                list.add(CardData(24, R.drawable.a24))
            }

        }
    }
}