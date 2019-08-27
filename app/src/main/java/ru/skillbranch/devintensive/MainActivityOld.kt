package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_old.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender
import android.view.inputmethod.EditorInfo
import ru.skillbranch.devintensive.extensions.isKeyboardOpen


class MainActivityOld : AppCompatActivity(), View.OnClickListener{
    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView

    lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_old)
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS")?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION")?: Bender.Question.NAME.name
        messageEt.setText(savedInstanceState?.getString("ANSVER"))
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        Log.d("M_MainActivity", "onCreate $status $question")
        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = benderObj.ascQuestion()
        messageEt.setOnEditorActionListener { _, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_DONE){
                sendTxt()
                true
            } else {
                false
            }
        }

        sendBtn.setOnClickListener(this)

    }

    fun sendTxt() {
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
         Log.d("M_MainActivity", "this.isKeyboardOpen()"+this.isKeyboardOpen().toString())
        if(isKeyboardOpen())
            hideKeyboard()
    }

    override fun onRestart() {
        super.onRestart()
         Log.d("M_MainActivity", "onRestart")
    }

    override fun onResume() {
        super.onResume()
         Log.d("M_MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("M_MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("M_MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_MainActivity", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
        outState?.putString("ANSWER",et_message.text.toString())
         Log.d("M_MainActivity", "onSaveInstanceState ${benderObj.status.name} ${benderObj.question.name}")
    }

    override fun onClick(v: View?){
        if(v?.id == R.id.iv_send)
            sendTxt()

    }
}


