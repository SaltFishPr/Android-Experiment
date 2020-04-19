package com.course.experimentthird

import android.app.ActionBar
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.course.experimentthird.datasource.CourseContract
import com.course.experimentthird.datasource.CourseDbHelper
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mLLWeekTitle: LinearLayout
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mDb: SQLiteDatabase

    private lateinit var mCourse: String
    private lateinit var mTeacher: String
    private lateinit var mLocation: String
    private lateinit var mAdapter: MyAdapter
    private val weekDays = arrayOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mContext = this

        recyclerView = findViewById(R.id.rv_grid)
        mLLWeekTitle = findViewById(R.id.ll_week_title)
        recyclerView.setBackgroundColor(getColor(R.color.mWhite))
        // 添加表头
        for (i in 0 until MyValues.weekDisplayNum) {
            val v =
                View.inflate(mContext, R.layout.item_weekday_index, null)
            (v.findViewById<View>(R.id.tv_weekday) as TextView).text = weekDays[i]

            val calendar: Calendar = Calendar.getInstance()
            val firstDayOfWeek: Int = calendar.firstDayOfWeek
            calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek + i + 1)
            val sdf = SimpleDateFormat("MM-dd", Locale.getDefault())
            (v.findViewById<View>(R.id.tv_date) as TextView).text = sdf.format(calendar.time)
            mLLWeekTitle.addView(
                v,
                LinearLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT, 3F)
            )
        }

        val dbHelper = CourseDbHelper(mContext)
        mDb = dbHelper.writableDatabase

        val cursor = getAllGuests()
        val mListener = object : MyAdapter.OnItemClickedListener {
            override fun onClick(pos: Int) {
                // show dialog
                val myDialog = MyDialog(mContext)
                myDialog.setCardInfo(object : MyDialog.CardInfo {
                    override fun getInfo(course: String, teacher: String, location: String) {
                        mCourse = course
                        mTeacher = teacher
                        mLocation = location
                    }
                })
                myDialog.setCancel(object : MyDialog.IOnCancelListener {
                    override fun onCancel(myDialog: MyDialog) {
                        myDialog.dismiss()
                    }
                })
                myDialog.setRemove(object : MyDialog.IOnRemoveListener {
                    override fun onRemove(myDialog: MyDialog) {
                        myDialog.dismiss()
                        updateCourse(pos)
                        mAdapter.swapCursor(getAllGuests())
                    }
                })
                myDialog.setConfirm(object : MyDialog.IOnConfirmListener {
                    override fun onConfirm(myDialog: MyDialog) {
                        // 不能全为空
                        if (mCourse == "" || mTeacher == "" || mLocation == "") {
                            return
                        }
                        myDialog.dismiss()
                        updateCourse(pos)
                        mAdapter.swapCursor(getAllGuests())
                    }
                })
                myDialog.show()
            }
        }
        layoutManager = GridLayoutManager(mContext, 3 * MyValues.weekDisplayNum + 1)
        (layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % (MyValues.weekDisplayNum + 1) == 0) {
                        1
                    } else {
                        3
                    }
                }
            }
        recyclerView.layoutManager = layoutManager
        mAdapter = MyAdapter(mContext, cursor, mListener)
        recyclerView.adapter = mAdapter
    }

    private fun getAllGuests(): Cursor {
        return mDb.query(
            CourseContract.CourseEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            CourseContract.CourseEntry.COLUMN_COURSE_INDEX
        )
    }

    private fun addNewCourse(pos: Int) {
        val cv = ContentValues()
        cv.put(CourseContract.CourseEntry.COLUMN_COURSE_INDEX, pos)
        cv.put(CourseContract.CourseEntry.COLUMN_COURSE_NAME, mCourse)
        cv.put(CourseContract.CourseEntry.COLUMN_TEACHER_NAME, mTeacher)
        cv.put(CourseContract.CourseEntry.COLUMN_LOCATION, mLocation)
        mDb.insert(CourseContract.CourseEntry.TABLE_NAME, null, cv)
    }

    private fun updateCourse(pos: Int) {
        val cv = ContentValues()
        cv.put(CourseContract.CourseEntry.COLUMN_COURSE_NAME, mCourse)
        cv.put(CourseContract.CourseEntry.COLUMN_TEACHER_NAME, mTeacher)
        cv.put(CourseContract.CourseEntry.COLUMN_LOCATION, mLocation)
        cv.put(CourseContract.CourseEntry.COLUMN_COURSE_INDEX, pos)
        mDb.update(
            CourseContract.CourseEntry.TABLE_NAME,
            cv,
            "${CourseContract.CourseEntry.COLUMN_COURSE_INDEX} = ?",
            arrayOf(pos.toString())
        )
    }
}
