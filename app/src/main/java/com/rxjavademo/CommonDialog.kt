package com.rxjavademo

import android.content.Context
import kotlinx.android.synthetic.main.dialog_info.view.*

//提示对话框
@DialogInfo(300,400,R.layout.dialog_info)
class HintDialog(ctx: Context): KotlinDialog(ctx){
    init {
        dialogView.tvYes.setOnClickListener {
            dismiss()
        }
        setCancelable(false)
        setOnDismissListener { dialogView.tvMain.text = "" }
    }
}
