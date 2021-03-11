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
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(EXTRA_REPLY_TITULO, editWordView.text.toString())
                replyIntent.putExtra(EXTRA_REPLY_DESCRICAO, editDescView.text.toString())
                setResult(Activity.RESULT_OK, replyIntent)
                /*val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)*/
            }
            finish()
        }
    }

    companion object {
        //const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
        const val EXTRA_REPLY_TITULO = "com.example.android.titlelistsql.REPLY"
        const val EXTRA_REPLY_DESCRICAO = "com.example.android.desclistsql.REPLY"
    }
}