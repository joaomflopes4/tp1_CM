package com.example.tp1_cm

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.tp1_cm.api.EndPoints
import com.example.tp1_cm.api.Pontos
import com.example.tp1_cm.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var pontos: List<Pontos>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val request = ServiceBuilder.buildService(EndPoints::class.java)
        val call = request.getPontos()
        var position: LatLng
        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_login), Context.MODE_PRIVATE
        )

        //Obter resposta do ws
        call.enqueue(object : Callback<List<Pontos>>{
            override fun onResponse(call: Call<List<Pontos>>, response: Response<List<Pontos>>) {
                if (response.isSuccessful){
                    pontos = response.body()!!
                    for(ponto in pontos){
                        position = LatLng(ponto.latitude.toString().toDouble(), ponto.longitude.toString().toDouble())

                        //se o id_Utilizador do ponto for igual ao id do login(SharedPreferences
                        if (ponto.id_user.equals(sharedPref.all[getString(R.string.Id_LoginUser)])){

                            mMap.addMarker(MarkerOptions()
                                    .position(position)
                                    .title(ponto.nome)
                                    .snippet(ponto.descricao + "+" + ponto.foto + "+" + ponto.id_user + "+" + sharedPref.all[getString(R.string.Id_LoginUser)].toString() + "+" + ponto.id_ocorrencia + "+" + ponto.id + "+" + ponto.latitude.toString().toDouble() + "+" +ponto.longitude.toString().toDouble() )
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) //altera a cor

                            )
                        }else {
                            mMap.addMarker(
                                    MarkerOptions()
                                            .position(position)
                                            .title(ponto.nome)
                                            .snippet(ponto.descricao + "+" + ponto.foto + "+" + ponto.id_user + "+" + sharedPref.all[getString(R.string.Id_LoginUser)].toString() + "+" + ponto.id_ocorrencia + "+" + ponto.id + "+" + ponto.latitude.toString().toDouble() + "+" +ponto.longitude.toString().toDouble() )
                            )
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Pontos>>, t: Throwable) {
                Toast.makeText(this@MapsActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val zone = LatLng(41.6946, -8.83016)
        val zoomLevel = 15f

        /* mMap.moveCamera(CameraUpdateFactory.newLatLng(zone))*/
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(zone, zoomLevel))
        mMap.setInfoWindowAdapter(Markerwindow(this))
        //Passar a informação quando a InfoWindow é clicada
                mMap.setOnInfoWindowClickListener { marker ->
                    val intent = Intent(this, Editar_eliminarPontos::class.java).apply{
                        putExtra("Título", marker.title)
                        putExtra("Spinnet", marker.snippet)
                    }
                    startActivity(intent)
                }
    }

    //logout
    fun logout(view: View) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.logout)
        builder.setMessage(R.string.logoutMessage)
        builder.setIcon(R.drawable.ic_baseline_exit_to_app_24)
        builder.setPositiveButton(R.string.yes) { dialog: DialogInterface?, which: Int ->
            //Fab
            val sharedPref: SharedPreferences = getSharedPreferences(
                    getString(R.string.preference_login), Context.MODE_PRIVATE
            )
            with(sharedPref.edit()){
                putBoolean(getString(R.string.LoginShared), false)
                putString(getString(R.string.EmailShared), "")
                commit()
            }
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        builder.setNegativeButton(R.string.no) { dialog: DialogInterface?, which: Int ->}
        builder.show()


    }

    override fun onBackPressed() {
        //nothing
        Toast.makeText(this@MapsActivity, R.string.back, Toast.LENGTH_SHORT).show()
    }
}