package com.course.experimentthird.datasource

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.course.experimentthird.MyValues

class CourseDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "course.db"
        private const val DATABASE_VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_COURSE_TABLE =
            "CREATE TABLE " + CourseContract.CourseEntry.TABLE_NAME + " (" +
                    CourseContract.CourseEntry.COLUMN_COURSE_INDEX + " INTEGER PRIMARY KEY," +
                    CourseContract.CourseEntry.COLUMN_COURSE_NAME + " TEXT, " +
                    CourseContract.CourseEntry.COLUMN_TEACHER_NAME + " TEXT, " +
                    CourseContract.CourseEntry.COLUMN_LOCATION + " TEXT" +
                    "); "
        db.execSQL(SQL_CREATE_COURSE_TABLE)
        // 初始化
        for (i in 0 until (MyValues.courseNum + 1)* MyValues.weekDisplayNum) {
            val cv = ContentValues()
            cv.put(CourseContract.CourseEntry.COLUMN_COURSE_INDEX, i)
            cv.put(CourseContract.CourseEntry.COLUMN_COURSE_NAME, "Default")
            cv.put(CourseContract.CourseEntry.COLUMN_TEACHER_NAME, "Default")
            cv.put(CourseContract.CourseEntry.COLUMN_LOCATION, "Default")
            db.insert(CourseContract.CourseEntry.TABLE_NAME, null, cv)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + CourseContract.CourseEntry.TABLE_NAME)
        onCreate(db)
    }
}