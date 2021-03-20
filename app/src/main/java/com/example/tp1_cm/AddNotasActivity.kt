package com.example.tp1_cm

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class AddNotasActivity : AppCompatActivity() {
    private lateinit var editWordView: EditText
    private lateinit var editDescView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notas)

        editWordView = findViewById(R.id.edit_word)
        editDescView = findViewById(R.id.edit_desc)


        val button = findViewById<Button>(R.id.button_save)

        if (intent.getStringExtra(EXTRA_REPLY_TITULO).isNullOrEmpty() && intent.getStringExtra(EXTRA_REPLY_DESCRICAO).isNullOrEmpty()) {
            button.setOnClickListener {
                val replyIntent = Intent()
                if (TextUtils.isEmpty(editWordView.text) || TextUtils.isEmpty(editDescView.text)) {
                    if(TextUtils.isEmpty((editWordView.text)) && !TextUtils.isEmpty((editDescView.text))){
                        editWordView.error = getString(R.string.toast_title)
                    }
                    if(!TextUtils.isEmpty((editWordView.text)) && TextUtils.isEmpty((editDescView.text))){
                        editDescView.error = getString(R.string.toast_desc)
                    }
                    if(TextUtils.isEmpty((editWordView.text)) && TextUtils.isEmpty((editDescView.text))){
                        editWordView.error = getString(R.string.toast_title)
                        editDescView.error = getString(R.string.toast_desc)
                    }
                } else {

                    replyIntent.putExtra(EXTRA_REPLY_TITULO, editWordView.text.toString())
                    replyIntent.putExtra(EXTRA_REPLY_DESCRICAO, editDescView.text.toString())
                    setResult(Activity.RESULT_OK, replyIntent)
                    finish()
                }
            }
        }
    }

    companion object {
        //const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_REPLY_TITULO = "com.example.android.titlelistsql.REPLY"
        const val EXTRA_REPLY_DESCRICAO = "com.example.android.desclistsql.REPLY"
    }
}