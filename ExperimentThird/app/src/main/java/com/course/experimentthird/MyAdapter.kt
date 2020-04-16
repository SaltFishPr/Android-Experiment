package com.course.experimentthird

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val mContext: Context, private val dataSet: Array<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 声明静态成员
    companion object {
        private const val TAG = "GridApter"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {  // 课程号
            val v =
                LayoutInflater.from(mContext).inflate(R.layout.item_weekday_index, viewGroup, false)
            v.isClickable = false
            WeekdayHeaderViewHolder(v)
        } else {  // 课程卡片
            val v = LayoutInflater.from(mContext).inflate(R.layout.item_course, viewGroup, false)
            CardViewHolder(v)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        // 用数据中的内容替换布局中的内容
        if (getItemViewType(position) == 0) {
            val temp: WeekdayHeaderViewHolder = viewHolder as WeekdayHeaderViewHolder
            temp.tvWeekday.text = "星期X"
            temp.tvDate.text = "xx-xx"
        } else {
            val temp: CardViewHolder = viewHolder as CardViewHolder
            temp.tvCourse.text = dataSet[position]
            temp.tvTeacher.text = "teacher"
            temp.tvClassroom.text = "classroom"
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < MyValues.weekDisplayNum) {
            0
        } else {
            1
        }
    }

    override fun getItemCount() = dataSet.size

    class CourseIndexViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvHead: TextView = v.findViewById(R.id.tv_head)
    }

    class CardViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvCourse: TextView
        val tvTeacher: TextView
        val tvClassroom: TextView

        init {
            v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
            tvCourse = v.findViewById(R.id.tv_course)
            tvTeacher = v.findViewById(R.id.tv_teacher)
            tvClassroom = v.findViewById(R.id.tv_classroom)
        }
    }

    class WeekdayHeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvWeekday: TextView = v.findViewById(R.id.tv_weekday)
        val tvDate: TextView = v.findViewById(R.id.tv_date)
    }
}
