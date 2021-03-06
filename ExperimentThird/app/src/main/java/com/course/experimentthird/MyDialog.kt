package com.course.experimentthird

import android.app.Dialog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.course.experimentthird.datasource.CourseDbHelper


class MyDialog : Dialog, View.OnClickListener {
    private lateinit var mEtCourse: EditText
    private lateinit var mEtTeacher: EditText
    private lateinit var mEtLocation: EditText
    private lateinit var mBtnCancel: Button
    private lateinit var mBtnRemove: Button
    private lateinit var mBtnConfirm: Button

    private lateinit var cardInfo: CardInfo
    private lateinit var cancelListener: IOnCancelListener
    private lateinit var removeListener: IOnRemoveListener
    private lateinit var confirmListener: IOnConfirmListener

    constructor(context: Context) : super(context)

    constructor(context: Context, themeId: Int) : super(context, themeId)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_my_dialog)

        // 设置宽度
        val m: WindowManager = window!!.windowManager
        val d: Display = m.defaultDisplay
        val p: WindowManager.LayoutParams = window!!.attributes
        val size: Point = Point()
        d.getSize(size)
        p.width = (size.x * 0.8).toInt()
        window!!.attributes = p

        mEtCourse = this.findViewById(R.id.et_course)
        mEtTeacher = this.findViewById(R.id.et_teacher)
        mEtLocation = this.findViewById(R.id.et_location)
        mBtnCancel = this.findViewById(R.id.btn_cancel)
        mBtnRemove = this.findViewById(R.id.btn_remove)
        mBtnConfirm = this.findViewById(R.id.btn_confirm)

        mBtnCancel.setOnClickListener(this)
        mBtnConfirm.setOnClickListener(this)
        mBtnRemove.setOnClickListener(this)
    }

    fun setCardInfo(cardInfo: CardInfo) {
        this.cardInfo = cardInfo
    }

    fun setCancel(listener: IOnCancelListener) {
        this.cancelListener = listener
    }

    fun setRemove(listener: IOnRemoveListener) {
        this.removeListener = listener
    }

    fun setConfirm(listener: IOnConfirmListener) {
        this.confirmListener = listener
    }

    interface CardInfo {
        fun getInfo(course: String, teacher: String, location: String)
    }

    interface IOnCancelListener {
        fun onCancel(myDialog: MyDialog)
    }

    interface IOnRemoveListener {
        fun onRemove(myDialog: MyDialog)
    }

    interface IOnConfirmListener {
        fun onConfirm(myDialog: MyDialog)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_cancel -> {
                cancelListener.onCancel(this)
            }
            R.id.btn_remove -> {
                cardInfo.getInfo(
                    "",
                    "",
                    ""
                )
                removeListener.onRemove(this)
            }
            R.id.btn_confirm -> {
                cardInfo.getInfo(
                    mEtCourse.text.toString(),
                    mEtTeacher.text.toString(),
                    mEtLocation.text.toString()
                )
                confirmListener.onConfirm(this)
            }
        }
    }
}