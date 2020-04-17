package com.course.experimentthird

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.course.experimentthird.datasource.CourseContract
import com.course.experimentthird.datasource.CourseDbHelper
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mDb: SQLiteDatabase

    private lateinit var mCourse: String
    private lateinit var mTeacher: String
    private lateinit var mLocation: String
    private lateinit var mAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mContext = this
        recyclerView = findViewById(R.id.rv_grid)
//        recyclerView.background = getDrawable(R.drawable.bridge)

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
                        Log.i("Info: ", "$mCourse $mTeacher $mLocation")
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
                        if (mCourse == "" || mTeacher == "" || mLocation == ""){
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
        layoutManager = GridLayoutManager(mContext, MyValues.weekDisplayNum)
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
