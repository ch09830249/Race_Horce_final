package com.example.horcerunning_final.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {

    var winner: String? = null                     //獲勝馬匹
    var betmoney: Int? = null                      //下注金
    var capital: Int = 10000                       //賭金
    var bethorsename: String? = null               //下注馬匹
    var miles_apple = MutableLiveData<Int>()
    var miles_banana = MutableLiveData<Int>()
    var miles_orange = MutableLiveData<Int>()
    var miles_pineapple = MutableLiveData<Int>()
    var ratio_apple: Double = 2.0
    var ratio_banana: Double = 2.0
    var ratio_orange: Double = 2.0
    var ratio_pineapple: Double = 2.0
    var earn: Int? = null                           //獎金 (這裡已經乘上匯率所以是台幣)
    var currency: Double = 0.0                      //當天匯率


    init {
        miles_apple.value = 0
        miles_banana.value = 0
        miles_orange.value = 0
        miles_pineapple.value = 0
        Log.i("GameViewModel", "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }


    fun startGame() {
        initializeGame()

        CoroutineScope(Dispatchers.IO).launch{
            appleRun("apple")
        }
        CoroutineScope(Dispatchers.IO).launch{
            bananaRun("banana")
        }
        CoroutineScope(Dispatchers.IO).launch{
            orangeRun("orange")
        }
        CoroutineScope(Dispatchers.IO).launch{
            pineappleRun("pineapple")
        }
    }

    private fun initializeGame() {
        miles_apple.value = 0
        miles_banana.value = 0
        miles_orange.value = 0
        miles_pineapple.value = 0
    }

    //Running
    fun appleRun(horseName: String){
        var miles = 0
        while(miles<20){
            Thread.sleep((0..2000).random().toLong())
            miles++
            CoroutineScope(Dispatchers.Main).launch{
                miles_apple.value = miles
            }
            Log.i("Game","$horseName runs $miles miles")
        }
    }

    fun bananaRun(horseName: String){
        var miles = 0
        while(miles<20){
            Thread.sleep((0..2000).random().toLong())
            miles++
            CoroutineScope(Dispatchers.Main).launch{
                miles_banana.value = miles
            }
            Log.i("Game","$horseName runs $miles miles")
        }
    }

    fun orangeRun(horseName: String){
        var miles = 0
        while(miles<20){
            Thread.sleep((0..2000).random().toLong())
            miles++
            CoroutineScope(Dispatchers.Main).launch{
                miles_orange.value = miles
            }
            Log.i("Game","$horseName runs $miles miles")
        }
    }

    fun pineappleRun(horseName: String){
        var miles = 0
        while(miles<20){
            Thread.sleep((0..2000).random().toLong())
            miles++
            CoroutineScope(Dispatchers.Main).launch{
                miles_pineapple.value = miles
            }
            Log.i("Game","$horseName runs $miles miles")
        }
    }

}