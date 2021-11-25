package com.example.navigationdrawercompose

sealed class NavegacionItem(var route: String, var icon: Int, var title: String)
{
    object Home : NavegacionItem("home", R.drawable.ic_home, "Home")
    object Profile : NavegacionItem("profile", R.drawable.ic_profile, "Profile")
    object Settings : NavegacionItem("settings", R.drawable.ic_settings, "Settings")
    object Share : NavegacionItem("share", R.drawable.ic_share, "Share")
    object Contact : NavegacionItem("contact", R.drawable.ic_contact, "Contact")
}