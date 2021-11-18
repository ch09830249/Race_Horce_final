package com.example.horcerunning_final.game

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.horcerunning_final.database.Record
import com.example.horcerunning_final.database.RecordDao
import com.example.horcerunning_final.json.Currency
import com.example.horcerunning_final.network.MyAPIService
import com.example.horcerunning_final.network.RetrofitManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GameViewModel(private val database: RecordDao, application: Application) : AndroidViewModel(application) {

    var winner: String? = null                     //The winner horse name

    //Mile of each horse
    var miles_apple = MutableLiveData<Int>()
    var miles_banana = MutableLiveData<Int>()
    var miles_orange = MutableLiveData<Int>()
    var miles_pineapple = MutableLiveData<Int>()

    //Ratio of each horse
    var ratio_apple = MutableLiveData<Double>()
    var ratio_banana = MutableLiveData<Double>()
    var ratio_orange = MutableLiveData<Double>()
    var ratio_pineapple = MutableLiveData<Double>()
    var capital = MutableLiveData<Int>()                      //ALl money we have
    var currency = MutableLiveData<Double>()                  //Exchange Rate from the USD to TWD
    var betmoney: Int? = null                      //The money you bet in this turn
    var bethorsename: String? = null               //The horse you bet in this turn
    var earn: Int? = 0                           //The money you earn in this turn

    //Each data row id in the database
    var lastDataID: Int = 1


    init {
        miles_apple.value = 0
        miles_banana.value = 0
        miles_orange.value = 0
        miles_pineapple.value = 0
        ratio_apple.value = 2.0
        ratio_banana.value = 2.0
        ratio_orange.value = 2.0
        ratio_pineapple.value = 2.0
        capital.value = 10000
        currency.value = 30.0
        lastDataID = 1

        //PS: Must place the database access codes in the CoroutineScope(Dispatchers.IO) and launch
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteall()
        }
        Log.i("GameViewModel", "GameViewModel created!")
    }


    //Fetch the exchange rate from open api
    fun fetch_exchangeRate(){
        CoroutineScope(Dispatchers.IO).launch {

            //PS: Import right packages in this class (EX: Many packages have Callback class)
            val apiService = RetrofitManager.client.create(MyAPIService::class.java)
            apiService.getExchangeRate().enqueue(object: Callback<Currency>{
                override fun onResponse(call: Call<Currency>, response: Response<Currency>) {

                    //PS: Update LiveData in the Main thread
                    CoroutineScope(Dispatchers.Main).launch {
                        currency.value = response.body()!!.USDTWD!!.Exrate
                        Log.v("Game", "Network access successfully")
                    }
                }
                override fun onFailure(call: Call<Currency>, t: Throwable) {
                    Log.v("Game", "Network access failed")
                }
            })
        }
    }

    //Start the game
    fun startGame() {
        CoroutineScope(Dispatchers.Main).launch {

            initializeGame()

            //Four horses run concurrently
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

            //Insert the game record
            CoroutineScope(Dispatchers.IO).launch {
                val newData = Record(lastDataID, bethorsename!!, betmoney!!, winner!!, earn!!, capital.value!!)
                database.insertData(newData)
                lastDataID++
            }
        }
    }

    //Initialize the game before the game starts
    private fun initializeGame() {
        miles_apple.value = 0
        miles_banana.value = 0
        miles_orange.value = 0
        miles_pineapple.value = 0
        winner = null
    }

    //The functions of each horse running
    fun Run(horseName: String) {
        val name = horseName
        lateinit var miles_horse: MutableLiveData<Int>
        lateinit var ratio_horse: MutableLiveData<Double>
        when (name) {
            "apple" -> {
                miles_horse = miles_apple
                ratio_horse = ratio_apple
            }
            "banana" -> {
                miles_horse = miles_banana
                ratio_horse = ratio_banana
            }
            "orange" -> {
                miles_horse = miles_orange
                ratio_horse = ratio_orange
            }
            else -> {
                miles_horse = miles_pineapple
                ratio_horse = ratio_pineapple
            }
        }
        while (miles_horse.value!! < 19) {
            Thread.sleep((0..2000).random().toLong())
            CoroutineScope(Dispatchers.Main).launch {
                miles_horse.value = (miles_horse.value)?.plus(1)
            }
        }
        win_or_lose(name, miles_horse, ratio_horse)
    }

    //The last mile to the destination and winner should calculate the final earn
    @Synchronized
    fun win_or_lose(
        horseName: String,
        miles_horse: MutableLiveData<Int>,
        ratio_horse: MutableLiveData<Double>
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            miles_horse.value = (miles_horse.value)?.plus(1)
            if (winner == null) {
                winner = horseName
                race_result(ratio_horse)
                if (ratio_horse.value!! > 2.0)
                    ratio_horse.value = (ratio_horse.value)?.minus(0.1)
            } else {
                if (ratio_horse.value!! < 5.0)
                    ratio_horse.value = (ratio_horse.value)?.plus(0.1)
            }
        }
    }

    //Calculate the final money
    fun race_result(ratio_horse: MutableLiveData<Double>) {
        val ratio = ratio_horse.value!!.toDouble()
        val exchange_ratio = currency.value!!.toDouble()
        if (bethorsename == winner) {
            earn = ((betmoney!!.toDouble()) * ratio * exchange_ratio).toInt()
            capital.value = (capital.value)?.plus(earn!!)
        } else {
            earn = 0
            betmoney = (betmoney!!.toDouble() * exchange_ratio).toInt()
            capital.value = (capital.value)?.minus(betmoney!!)
        }
    }
}
