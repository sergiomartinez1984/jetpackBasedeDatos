package com.example.navigationdrawercompose

sealed class NavegacionItem(var route: String, var icon: Int, var title: String)
{
    object insertar : NavegacionItem("Añadir", R.drawable.anadir, "Añadir Personaje")
    object borrar : NavegacionItem("Borrar", R.drawable.borrar, "Borrar Personaje")
    object personajes : NavegacionItem("ListarPersonajes", R.drawable.supermario, "Lista de Personajes")

}