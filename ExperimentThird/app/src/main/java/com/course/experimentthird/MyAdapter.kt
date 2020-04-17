package com.course.experimentthird

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.course.experimentthird.datasource.CourseContract


class MyAdapter(
    private val mContext: Context,
    private var mCursor: Cursor,
    private var mListener: OnItemClickedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val v = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_weekday_index, viewGroup, false)
                v.setBackgroundColor(getColor(mContext, R.color.mGray))
                v.isClickable = false
                WeekdayHeaderViewHolder(v)
            }
            1 -> {
                val v =
                    LayoutInflater.from(mContext).inflate(R.layout.item_course, viewGroup, false)
                v.setBackgroundColor(getColor(mContext, R.color.mPink))
                CardViewHolder(v)
            }
            2 -> {
                val v =
                    LayoutInflater.from(mContext).inflate(R.layout.item_course, viewGroup, false)
                v.setBackgroundColor(getColor(mContext, R.color.mBlue))
                CardViewHolder(v)
            }
            3 -> {
                val v =
                    LayoutInflater.from(mContext).inflate(R.layout.item_course, viewGroup, false)
                v.setBackgroundColor(getColor(mContext, R.color.mGreen))
                CardViewHolder(v)
            }
            4 -> {
                val v =
                    LayoutInflater.from(mContext).inflate(R.layout.item_course, viewGroup, false)
                v.setBackgroundColor(getColor(mContext, R.color.mOrange))
                CardViewHolder(v)
            }
            else -> {
                val v =
                    LayoutInflater.from(mContext).inflate(R.layout.item_course, viewGroup, false)
                v.setBackgroundColor(getColor(mContext, R.color.mPurple))
                CardViewHolder(v)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        // 用数据中的内容替换布局中的内容
        if (getItemViewType(position) == 0) {
            val temp: WeekdayHeaderViewHolder = viewHolder as WeekdayHeaderViewHolder
            temp.tvWeekday.text = "星期X"
            temp.tvDate.text = "xx-xx"
        } else {
            if (!mCursor.moveToPosition(position - 7))
                return

            val nCourseName =
                mCursor.getString(mCursor.getColumnIndex(CourseContract.CourseEntry.COLUMN_COURSE_NAME))
            val nTeacherName =
                mCursor.getString(mCursor.getColumnIndex(CourseContract.CourseEntry.COLUMN_TEACHER_NAME))
            val nLocation =
                mCursor.getString(mCursor.getColumnIndex(CourseContract.CourseEntry.COLUMN_LOCATION))

            val temp: CardViewHolder = viewHolder as CardViewHolder
            temp.tvCourse.text = nCourseName
            temp.tvTeacher.text = nTeacherName
            temp.tvLocation.text = nLocation
            temp.itemView.setOnClickListener {
                mListener.onClick(position - 7)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position / MyValues.weekDisplayNum
    }

    override fun getItemCount(): Int {
        return mCursor.count
    }

    interface OnItemClickedListener {
        fun onClick(pos: Int)
    }

    fun swapCursor(newCursor: Cursor?) { // Always close the previous mCursor first
        mCursor.close()
        if (newCursor != null) {
            mCursor = newCursor
            this.notifyDataSetChanged()
        }
    }

    class CardViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvCourse: TextView = v.findViewById(R.id.tv_course)
        val tvTeacher: TextView = v.findViewById(R.id.tv_teacher)
        val tvLocation: TextView = v.findViewById(R.id.tv_location)
    }

    class WeekdayHeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvWeekday: TextView = v.findViewById(R.id.tv_weekday)
        val tvDate: TextView = v.findViewById(R.id.tv_date)
    }
}
