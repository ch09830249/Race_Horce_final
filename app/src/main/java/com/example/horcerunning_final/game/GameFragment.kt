package com.example.horcerunning_final.game

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.horcerunning_final.R
import com.example.horcerunning_final.databinding.FragmentGameBinding

class GameFragment : Fragment() {

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

        Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        binding.button1.setOnClickListener {
            viewModel.startGame()
        }

        binding.button2.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_gameFragment_to_historyFragment)
        }

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

        return binding.root
    }


}