package com.example.tp1_cm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.tp1_cm.api.EndPoints
import com.example.tp1_cm.api.Pontos_adicionar
import com.example.tp1_cm.api.ServiceBuilder
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import java.io.*
import java.net.URI
import kotlin.properties.Delegates
import java.net.URL
import java.util.*
private lateinit var lastLocation: Location
private lateinit var fusedLocationClient: FusedLocationProviderClient
private lateinit var  locationCallback: LocationCallback
private lateinit var locationRequest: LocationRequest
val REQUEST_CODE = 1
private lateinit var image: ImageView
private var imageUri: Uri? = null
private lateinit var imagemLink: String


class AdicionarOcorrencias : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_ocorrencias)

        var intent = intent
        //Latitude
        val latitude = intent.getParcelableExtra<LatLng>("localizacao")!!.latitude
        //Longitude
        val longitude = intent.getParcelableExtra<LatLng>("localizacao")!!.longitude

        val id_user = intent.getStringExtra("id_user")

        image = findViewById(R.id.imagemRecebida)

        val spinner = findViewById<Spinner>(R.id.ocorrenciaRecebida)
        val adapter = ArrayAdapter.createFromResource(this, R.array.tipos, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        val button = findViewById<Button>(R.id.addOcorrencia)

        button.setOnClickListener {
            //imagem
            val nomeImagem = findViewById<EditText>(R.id.nomeImagem).text.toString()

            val imgBitmap: Bitmap = findViewById<ImageView>(R.id.imagemRecebida).drawable.toBitmap()
            val imageFile: File = convertBitmapToFile("file", imgBitmap)
            var imageUrl = "https://22362tpcm.000webhostapp.com/myslim/api/imagens/" + nomeImagem + ".JPG"

            val bos = ByteArrayOutputStream()
            val imagem: Bitmap = (image.drawable as BitmapDrawable).bitmap
            imagem.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val encodedImage: String = Base64.getEncoder().encodeToString(bos.toByteArray())



            val titulo = findViewById<EditText>(R.id.tituloRecebido).text.toString()

            val descricao = findViewById<EditText>(R.id.descricaoRecebida).text.toString()

            val lat = intent.getParcelableExtra<LatLng>("localizacao")!!.latitude

            val long = intent.getParcelableExtra<LatLng>("localizacao")!!.longitude



            val tipo = spinner.selectedItemId



            /*Log.d("***AQUI",titulo )
            Log.d("***AQUI",descricao )
            Log.d("***AQUI",lat.toString() )
            Log.d("***AQUI",long.toString() )
            Log.d("***AQUI",id_user.toString() )
            Log.d("***AQUI",tipo.toString() )*/

            val request = ServiceBuilder.buildService(EndPoints::class.java)
            val call = request.adicionarOcorrencia(lat.toString(), long.toString(), titulo, descricao, imageUrl.toString(),id_user!!.toInt(),tipo.toInt(),encodedImage,nomeImagem)
            var intent = Intent(this, MapsActivity::class.java)

            Log.d("***AQUI",imgBitmap.toString() )
            Log.d("***AQUI",encodedImage )


            call.enqueue(object : Callback<Pontos_adicionar> {
                override fun onResponse(call: Call<Pontos_adicionar>, response: Response<Pontos_adicionar>) {
                    if (response.isSuccessful){

                        Toast.makeText(this@AdicionarOcorrencias, R.string.updatesuccessful, Toast.LENGTH_SHORT).show()
                        startActivity(intent)



                    } else {
                        Toast.makeText(this@AdicionarOcorrencias, R.string.ErrorupdatePoint, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Pontos_adicionar>, t: Throwable) {
                    //Toast.makeText(this@Editar_eliminarPontos, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }


    }
    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        //create a file to write bitmap data
        val file = File(this@AdicionarOcorrencias.cacheDir, fileName)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos)
        val bitMapData = bos.toByteArray()

        //write the bytes in file
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }
    fun chooseImage(view: View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null){
                image.setImageURI(data.data)
            }
            //imageView.setImageURI(imageUri)
            Log.d("IMAGEM", "image " + image.toString() )
        }
    }


}