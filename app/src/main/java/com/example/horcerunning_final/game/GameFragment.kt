package com.example.horcerunning_final.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.horcerunning_final.R
import com.example.horcerunning_final.database.HistoryDatabase
import com.example.horcerunning_final.databinding.FragmentGameBinding
import java.lang.Exception

class GameFragment : Fragment() {

    //Global variables
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )

        //Get the activity's application
        val application = requireNotNull(this.activity).application

        //Get the database's Dao by this application
        val dataSource = HistoryDatabase.getInstance(application).getDao()

        // Create an instance of the ViewModel Factory
        val viewModelFactory = GameViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        //Fetch the exchange rate USDTWD from the internet
        viewModel.fetch_exchangeRate()

        //??
        binding.lifecycleOwner = viewLifecycleOwner

        //Start game button
        binding.button1.setOnClickListener {

            //Try to fetch the bet money and the horse name to the GameViewModel
            try {
                viewModel.betmoney = binding.edittext.text.toString().trim().toInt()
                val RadioB1 = binding.rg1
                val RadioBtm: RadioButton = binding.root.findViewById(RadioB1.getCheckedRadioButtonId())
                viewModel.bethorsename = RadioBtm.text.toString()
            }catch (e: Exception){
                Log.i("Game", "Please insert the money and choose one horse")
            }

            //Start the game
            viewModel.startGame(application)
        }

        //Navigate to the history records
        binding.button2.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_historyFragment)
        }

        //Reset the game
        binding.button3.setOnClickListener { view: View ->
            viewModel.reset_game()
        }

        //Game over
        binding.button4.setOnClickListener {
            requireNotNull(this.activity).finish()
        }

        //Observe
        viewModel.miles_apple.observe(viewLifecycleOwner, Observer { newMiles ->
            binding.progressBar1.progress = newMiles
        })
        viewModel.miles_banana.observe(viewLifecycleOwner, Observer { newMiles ->
            binding.progressBar2.progress = newMiles
        })
        viewModel.miles_orange.observe(viewLifecycleOwner, Observer { newMiles ->
            binding.progressBar3.progress = newMiles
        })
        viewModel.miles_pineapple.observe(viewLifecycleOwner, Observer { newMiles ->
            binding.progressBar4.progress = newMiles
        })
        viewModel.ratio_apple.observe(viewLifecycleOwner, Observer { newRatio ->
            binding.ratio1.text = String.format("%.1f", newRatio)
        })
        viewModel.ratio_banana.observe(viewLifecycleOwner, Observer { newRatio ->
            binding.ratio2.text = String.format("%.1f", newRatio)
        })
        viewModel.ratio_orange.observe(viewLifecycleOwner, Observer { newRatio ->
            binding.ratio3.text = String.format("%.1f", newRatio)
        })
        viewModel.ratio_pineapple.observe(viewLifecycleOwner, Observer { newRatio ->
            binding.ratio4.text = String.format("%.1f", newRatio)
        })
        viewModel.capital.observe(viewLifecycleOwner, Observer { newCapital ->
            binding.txt3.text = newCapital.toString()
        })
        viewModel.currency.observe(viewLifecycleOwner, Observer { newExchangeRate ->
            binding.txtRatio4.text = newExchangeRate.toString()
        })

        return binding.root
    }
}