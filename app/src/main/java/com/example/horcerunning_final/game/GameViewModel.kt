package com.example.horcerunning_final.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameViewModel : ViewModel() {

    var winner: String? = null                     //獲勝馬匹
    var miles_apple = MutableLiveData<Int>()
    var miles_banana = MutableLiveData<Int>()
    var miles_orange = MutableLiveData<Int>()
    var miles_pineapple = MutableLiveData<Int>()
    var ratio_apple= MutableLiveData<Double>()
    var ratio_banana= MutableLiveData<Double>()
    var ratio_orange= MutableLiveData<Double>()
    var ratio_pineapple= MutableLiveData<Double>()


    var betmoney: Int? = null                      //下注金
    var capital: Int = 10000                       //賭金
    var bethorsename: String? = null               //下注馬匹
    var earn: Int? = null                           //獎金 (這裡已經乘上匯率所以是台幣)
    var currency: Double = 0.0                      //當天匯率


    init {
        miles_apple.value = 0
        miles_banana.value = 0
        miles_orange.value = 0
        miles_pineapple.value = 0
        ratio_apple.value = 2.0
        ratio_banana.value = 2.0
        ratio_orange.value = 2.0
        ratio_pineapple.value = 2.0
        Log.i("GameViewModel", "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed!")
    }


    fun startGame() {
        CoroutineScope(Dispatchers.Main).launch {

            initializeGame()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                Run("apple")
            }
            val job2 = CoroutineScope(Dispatchers.IO).launch {
                Run("banana")
            }
            val job3 = CoroutineScope(Dispatchers.IO).launch {
                Run("orange")
            }
            val job4 = CoroutineScope(Dispatchers.IO).launch {
                Run("pineapple")
            }

            job1.join()
            job2.join()
            job3.join()
            job4.join()
        }

    }

    private fun initializeGame() {
        miles_apple.value = 0
        miles_banana.value = 0
        miles_orange.value = 0
        miles_pineapple.value = 0
        winner = null
    }

    //The functions of each horse running
    fun Run(horseName: String) {
        var name = horseName
        lateinit var miles_horse: MutableLiveData<Int>
        when (name) {
            "apple" -> {
                miles_horse = miles_apple
            }
            "banana" -> {
                miles_horse = miles_banana
            }
            "orange" -> {
                miles_horse = miles_orange
            }
            else -> {
                miles_horse = miles_pineapple
            }
        }
        while (miles_horse.value!! < 19) {
            Thread.sleep((0..2000).random().toLong())
            CoroutineScope(Dispatchers.Main).launch {
                miles_horse.value = (miles_horse.value)?.plus(1)
            }
        }
        win_or_lose(name, miles_horse)
    }

    @Synchronized
    fun win_or_lose(horseName: String, miles_horse: MutableLiveData<Int>) {
        CoroutineScope(Dispatchers.Main).launch {
            miles_horse.value = (miles_horse.value)?.plus(1)
        }
        if (winner == null) {
            winner = horseName
            Thread.sleep((0..200).random().toLong())
        }
    }
}