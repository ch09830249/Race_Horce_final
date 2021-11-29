package com.example.horcerunning_final.history

import android.app.Application
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.horcerunning_final.R
import com.example.horcerunning_final.database.HistoryDatabase
import com.example.horcerunning_final.database.Record
import com.example.horcerunning_final.database.RecordDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Data list as the argument
class HistoryRecordAdapter(private val data: List<Record>, private val database: RecordDao, private val application: Application): RecyclerView.Adapter<HistoryRecordAdapter.ViewHolder>() {

    //Get the database's Dao by this application
    val dataSource = HistoryDatabase.getInstance(application).getDao()

    //The number of the data rows
    override fun getItemCount(): Int {
        return data.size
    }

    //Inflate the each row by item_view.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_view, parent, false)
        return ViewHolder (view)
    }

    //Bind the data values with views in item_view.xml
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        bind(holder, item)
    }

    //Assign value to each view's text
    private fun bind(holder: HistoryRecordAdapter.ViewHolder, item: Record) {
        holder.id.text = item.id.toString()
        holder.id.setOnClickListener { view ->
            CoroutineScope(Dispatchers.Main).launch {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    dataSource.deleteById(item.id)
                }
                job.join()
                view.findNavController().navigate(R.id.action_global_historyFragment)
            }
        }
        holder.betmoney.text = item.Betmoney.toString()
        holder.bethorse.text = item.Bethorse
        holder.capital.text = item.Captial.toString()
        holder.earn.text = item.Earn.toString()
        holder.winner.text = item.Winner
    }

    //A class with all the views (Six TextViews in this example) in a row
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id: TextView = itemView.findViewById(R.id.tv_id)
        val bethorse: TextView = itemView.findViewById(R.id.tv_horse)
        val betmoney: TextView = itemView.findViewById(R.id.tv_betmoney)
        val winner: TextView = itemView.findViewById(R.id.tv_winner)
        val earn: TextView = itemView.findViewById(R.id.tv_earn)
        val capital: TextView = itemView.findViewById(R.id.tv_capital)

//        private fun ViewHolder.bind(item: Record) {
//            id.text = item.id.toString()
//            bethorse.text = item.Bethorse
//            betmoney.text = item.Betmoney.toString()
//            winner.text = item.Winner
//            earn.text = item.Earn.toString()
//            capital.text = item.Captial.toString()
//        }
    }
}


