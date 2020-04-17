package com.course.experimentthird.datasource

import android.provider.BaseColumns

class CourseContract {
    object CourseEntry : BaseColumns {
        const val TABLE_NAME = "course"
        const val COLUMN_COURSE_INDEX = "course_index"
        const val COLUMN_COURSE_NAME = "course_name"
        const val COLUMN_TEACHER_NAME = "teacher_name"
        const val COLUMN_LOCATION = "location"
        // 暂时用不到
        val COLUMN_BEGIN_WEEK = "begin_week"
        val COLUMN_END_WEEK = "end_week"
    }
}