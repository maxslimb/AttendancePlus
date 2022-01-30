package com.example.attendanceplus

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.attendanceplus.model.attendance

class AttendanceListAdapter  (var data: List<attendance>): RecyclerView.Adapter<AttendanceListAdapter.ItemViewHolder>() {

    lateinit var context: Context

    class ItemViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val SName_textView = row.findViewById<TextView>(R.id.SName_textView)
        val SRollNo_textView = row.findViewById<TextView>(R.id.SRollNo_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.itemview_a,parent,false)
        val holder =  ItemViewHolder(layout)
        return holder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.SName_textView.text = item.Student_name
        holder.SRollNo_textView.text = item.Student_rollno
    }

    override fun getItemCount(): Int {
        return data.size
    }
}