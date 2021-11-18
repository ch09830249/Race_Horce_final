package com.example.horcerunning_final.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.horcerunning_final.R
import com.example.horcerunning_final.database.Record

class HistoryRecordAdapter(private val data: List<Record>): RecyclerView.Adapter<HistoryRecordAdapter.ViewHolder>() {

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


