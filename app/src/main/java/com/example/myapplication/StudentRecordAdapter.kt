package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.student_record_row.view.*

class StudentRecordAdapter(val arrayList: ArrayList<StudentRecordModel>, val context: Context) :
    RecyclerView.Adapter<StudentRecordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(student_record: StudentRecordModel) {
            itemView.txtStudentRecordName.text = student_record.name
            itemView.txtStudentRecordUniversity.text = student_record.university
            itemView.txtStudentRecordCollege.text = student_record.college
            itemView.txtStudentRecordFees.text = student_record.fees
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.student_record_row ,parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(arrayList[position])
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }
}