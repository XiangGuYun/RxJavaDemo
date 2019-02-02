package com.rxjavademo

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_info.view.*
import java.util.concurrent.TimeUnit

/**
 * https://www.jianshu.com/p/cd984dd5aae8
 * @property dialog HintDialog
 */
class MainActivity : AppCompatActivity() {

    private fun String.toast(){
        Toast.makeText(this@MainActivity,this,Toast.LENGTH_SHORT).show()
    }

    private lateinit var dialog: HintDialog

    fun show(func:(dialog:Dialog, dialogView:View)->Unit){
        func.invoke(dialog, dialog.dialogView)
        dialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = HintDialog(this)

        //create
        btnCreateCode.setOnClickListener {
            show { dialog, dialogView ->
                dialogView.tvMain.text = """
                /*
               我们首先来创建一个发射器
               在这里我们用到了create操作符进行创建，这里需要指定泛型来确认发送的数据类型
               在回调函数中，我们分别发送了1,2,3并进行完成操作
                */
                val emitter = Observable.create<String> {
                    it.onNext("Hello World!")
                    it.onComplete()
                }
                /*
                接着我们来创建接收器，来接受发送出去的数据，并打印出来
                 */
                emitter.subscribe {
                    println(it)
                }
                //输出
                //Hello World!
                """.trimIndent()
            }
        }

        //just
        btnJustCode.setOnClickListener {
            show { dialog, dialogView ->
                dialogView.tvMain.text = """
                    Observable.just("Hello RxJava!").subscribe {
                        println(it)
                    }
                    //输出
                    //Hello RxJava!
                    Observable.just("Hello RxJava!","Are You OK!").subscribe {
                        println(it)
                    }
                    //输出
                    //Hello RxJava!
                    //Are You OK!
                """.trimIndent()
            }
        }

        //timer操作符
        btnTimer.setOnClickListener {
            "3秒后弹出Hello World".toast()
            Observable.timer(3,TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe {
                "Hello World".toast()
            }
        }

        btnTimerCode.setOnClickListener {
           show { dialog, dialogView ->
               dialogView.tvMain.text = """
                Observable.timer(3,TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe {
                    "Hello World".toast()
                }
                """.trimIndent()
           }
        }

        //interval操作符
        btnInterval.setOnClickListener {
            show {
                dialog, view->
                view.tvMain.textSize = 40f
                view.tvMain.gravity = Gravity.CENTER
                var i = 0
                val disposable = Observable.interval(1,1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe {
                           view.tvMain.text = i++.toString()
                        }
                view.tvMain.setOnClickListener {
                    disposable.dispose()//处理掉
                }
                dialog.setOnDismissListener {
                    disposable.dispose()//处理掉
                    view.tvMain.text = ""
                    view.tvMain.textSize = 10f
                    view.tvMain.gravity = Gravity.LEFT
                }
            }
        }
        btnIntervalCode.setOnClickListener {
           show { dialog, dialogView ->
               dialogView.tvMain.text = """
                var i = 0
                val disposable = Observable.interval(1,1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                        .subscribe {
                           tv.text = i++.toString()
                        }
                tv.setOnClickListener {
                    disposable.dispose()//处理掉
                }
                """.trimIndent()
           }
        }



    }
}
