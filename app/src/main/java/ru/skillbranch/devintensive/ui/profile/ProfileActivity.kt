package ru.skillbranch.devintensive.ui.profile


import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_profile.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.models.Bender

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var benderobj:Bender
    lateinit var benderImage:ImageView
    lateinit var textTxt:TextView
    lateinit var messageEt:EditText
    lateinit var sendBtn:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        val answerCount = savedInstanceState?.getInt("ANSWERCOUNT")?: 0
        Log.d("M_MainActivity","onCreate $status $question")
        benderobj = Bender(Bender.Status.valueOf(status),Bender.Question.valueOf(question))
        benderobj.answerCount = answerCount
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send
        val (r,g,b) = benderobj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
        textTxt.text =benderobj.askQuestion()
        sendBtn.setOnClickListener(this)
        messageEt.setOnEditorActionListener { textView, i, keyEvent ->
            if (textView?.id == R.id.et_message && i == EditorInfo.IME_ACTION_DONE) {
                sendMessage()

                hideKeyboard()
                true
            }else{
                false
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS", benderobj.status.name)
        outState?.putString("QUESTION", benderobj.question.name)
        outState?.putInt("ANSWERCOUNT", benderobj.answerCount)

        Log.d("M_MainActivity","onSaveInstanceState ${benderobj.status.name} ${benderobj.question.name}")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("M_MainActivity", "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.d("M_MainActivity", "onStart")
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

    override fun onClick(view: View?) {
        if (view?.id == R.id.iv_send){
           sendMessage()

        }else{

        }
    }

    private fun sendMessage(){
        val (phrase, color) = benderobj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        val (r,g,b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }
}
