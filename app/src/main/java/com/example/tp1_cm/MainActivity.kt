package com.example.tp1_cm

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.tp1_cm.api.EndPoints
import com.example.tp1_cm.api.OutputPost
import com.example.tp1_cm.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //verifica se o login já foi realizado - SharedPreferences
        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_login), Context.MODE_PRIVATE
        )
        if (sharedPref != null){
            if(sharedPref.all[getString(R.string.LoginShared)]==true){
                var intent = Intent(this, MapsActivity::class.java)
                startActivity(intent)

            }
        }
    }

    fun paginaNotas(view: View) {
        var intent = Intent(this, NotasActivity::class.java)
        startActivity(intent)
    }

    //função para login
    fun map(view: View) {
        val emailInserido = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val passwordInserida = findViewById<EditText>(R.id.editTextTextPassword)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.postTest(emailInserido.text.toString(),passwordInserida.text.toString())
        var intent = Intent(this, MapsActivity::class.java)

        //Validações do formulário
        if(emailInserido.text.isNullOrEmpty() || passwordInserida.text.isNullOrEmpty()){

            if(emailInserido.text.isNullOrEmpty() && !passwordInserida.text.isNullOrEmpty()){
                emailInserido.error = getString(R.string.Login_Email)
            }
            if(!emailInserido.text.isNullOrEmpty() && passwordInserida.text.isNullOrEmpty()){
                passwordInserida.error = getString(R.string.Login_Password)
            }
            if(emailInserido.text.isNullOrEmpty() && passwordInserida.text.isNullOrEmpty()){
                emailInserido.error = getString(R.string.Login_Email)
                passwordInserida.error = getString(R.string.Login_Password)
            }
        }

        //Obter resposta do ws
        call.enqueue(object : Callback<OutputPost>{
            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>) {
                if (response.isSuccessful){
                    val e: OutputPost = response.body()!!

                    //Confirmação login
                    if(emailInserido.text.toString().equals(e.email) && (passwordInserida.text.toString().equals(e.password))){
                        /*Toast.makeText(this@MainActivity, e.email.toString() + "-" + e.password, Toast.LENGTH_SHORT).show()*/
                                startActivity(intent)

                        //Shared Preferences Login
                        val sharedPref: SharedPreferences = getSharedPreferences(
                                getString(R.string.preference_login), Context.MODE_PRIVATE
                        )
                        with(sharedPref.edit()){
                            putBoolean(getString(R.string.LoginShared), true)
                            putString(getString(R.string.EmailShared), "${emailInserido.text}")
                            putInt(getString(R.string.Id_LoginUser), e.id)
                            commit()
                            Log.d("**SHARED","${e.id}" )
                        }
                    }else if (!(emailInserido.text.toString().equals(e.email) && (passwordInserida.text.toString().equals(e.password)))){

                        Toast.makeText(this@MainActivity, R.string.Error_login, Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<OutputPost>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })




    }


}