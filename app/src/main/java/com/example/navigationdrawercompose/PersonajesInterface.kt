
package com.example.navigationdrawercompose



import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val base_user = "http://iesayala.ddns.net/sergioM/"

interface PersonajeInterface {
    @GET("listarPersonajes.php")
    fun personajeInformation(): Call<PersonajesInfo>
}

object PersonajeInstance {
    val personajeInterface: PersonajeInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(base_user)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        personajeInterface = retrofit.create(PersonajeInterface::class.java)
        }


}
