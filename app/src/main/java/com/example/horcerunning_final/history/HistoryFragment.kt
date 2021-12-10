package com.example.horcerunning_final.history


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horcerunning_final.R
import com.example.horcerunning_final.database.HistoryDatabase
import com.example.horcerunning_final.database.Record
import com.example.horcerunning_final.databinding.FragmentHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //Get all data
        lateinit var data_list: List<Record>
        val application = requireNotNull(this.activity).application
        val database = HistoryDatabase.getInstance(application).getDao()

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_history,
            container,
            false
        )

        //New a recyclerview's layoutManager as LinearLayoutManager
        val layoutManager = LinearLayoutManager(this.activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        CoroutineScope(Dispatchers.Main).launch {
            //Get all the Records
            val job = CoroutineScope(Dispatchers.IO).launch {
                data_list = database.getAll()
            }
            job.join()
            val adapter = HistoryRecordAdapter(data_list, database, application)
            //Set recyclerview's layoutManager and adapter
            binding.recyclerview.layoutManager = layoutManager
            binding.recyclerview.adapter = adapter
        }

        binding.backGame.setOnClickListener{ view: View ->
            view.findNavController().popBackStack()
        }

        return binding.root
    }

}