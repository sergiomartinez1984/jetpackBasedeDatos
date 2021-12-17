package com.example.navigationdrawercompose



import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.transform.CircleCropTransformation
import com.example.navigationdrawercompose.ui.theme.NavigationDrawerComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

import java.io.IOException
import java.net.URL


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Llamada()
                    MainScreen()
                }

            }
        }
    }
}


@Composable
fun MainScreen() {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope = scope, scaffoldState = scaffoldState) },
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
    ) {
        Navigation(navController = navController)
    }

}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {

    TopAppBar(
        title = { Text(text = "VideoGamer Zone", fontSize = 28.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = Color.Black,
        contentColor = Color.Red
    )

}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {

    val items = listOf(
        NavegacionItem.insertar,
        NavegacionItem.borrar,
        NavegacionItem.personajes,

    )

    Column(
        modifier = Modifier
            .background(color = Color.Black)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.personajes),
                contentDescription = "",
                modifier = Modifier
                    .border(BorderStroke(5.dp, Color.Cyan), CircleShape)
                    .height(200.dp)
                    .width(200.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(CircleShape)
            )

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { items ->
            DrawerItem(item = items, selected = currentRoute == items.route, onItemClick = {

                navController.navigate(items.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }

                scope.launch {
                    scaffoldState.drawerState.close()
                }

            })
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            painter = painterResource(id = R.drawable.supermario1),
            contentDescription = "",
            contentScale = ContentScale.Fit
        )

        Text(
            text = "Designed by Sergio Martinez",
            color = Color.Red,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )

    }
}

@Composable
fun DrawerItem(item: NavegacionItem, selected: Boolean, onItemClick: (NavegacionItem) -> Unit) {
    val background = if (selected) Color.DarkGray else Color.Transparent
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .height(45.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {

        Image(
            painter = painterResource(id = item.icon),
            contentDescription = item.title,
            colorFilter = ColorFilter.tint(Color.White),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(34.dp)
                .width(34.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(7.dp))
    }

}

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController, startDestination = NavegacionItem.insertar.route) {

        composable(NavegacionItem.insertar.route) {
            Greeting()
        }

        composable(NavegacionItem.borrar.route) {
            GreetingDelete()
        }

        composable(NavegacionItem.personajes.route) {
            Llamada()
        }

    }

}


@Composable
fun Greeting() {
    var textFieldValueId by rememberSaveable { mutableStateOf("") }
    var textFieldValueNombre by rememberSaveable { mutableStateOf("") }
    var textFieldValueCompañia by rememberSaveable { mutableStateOf("") }
    var textFieldValueImagen by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()


    ) {

        TextField(
            value = textFieldValueId,
            onValueChange = { nuevo ->
                textFieldValueId = nuevo
            },
            label = {
                Text(text = "Introduce el id")
            },
            modifier = Modifier
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )


        TextField(
            value = textFieldValueNombre,
            onValueChange = { nuevo ->
                textFieldValueNombre = nuevo
            },
            label = {
                Text(text = "Introduce el Nombre")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )


        TextField(
            value = textFieldValueCompañia,
            onValueChange = { nuevo ->
                textFieldValueCompañia = nuevo
            },
            label = {
                Text(text = "Introduce la Compañia")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )


        TextField(
            value = textFieldValueImagen,
            onValueChange = { nuevo ->
                textFieldValueImagen = nuevo
            },
            label = {
                Text(text = "imagen")
            },
            modifier = Modifier
                .padding( 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )

        Spacer(Modifier.height(20.dp) )


        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(width = 100.dp, height = 50.dp)
            ,


            onClick = {
                insertar(textFieldValueId,textFieldValueNombre,textFieldValueCompañia,textFieldValueImagen)
                textFieldValueId=""
                textFieldValueNombre=""
                textFieldValueCompañia=""
                textFieldValueImagen=""
            }
        ){
            Text(text = "Insert"
            )
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),

            painter = painterResource(id = R.drawable.wow),
            contentDescription = "",
            contentScale = ContentScale.Inside
        )

    }

}
@Composable
fun GreetingDelete() {
    var textFieldValueId by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()


    ) {

        TextField(

            value = textFieldValueId,
            onValueChange = { nuevo ->
                textFieldValueId = nuevo
            },
            label = {
                Text(text = "Introduce el id")
            },
            modifier = Modifier
                .padding(10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            textStyle = TextStyle(textAlign = TextAlign.Right)
        )
        Button(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .size(width = 100.dp, height = 50.dp),

            onClick = {

                borrarPersonajes(textFieldValueId)
                textFieldValueId=""

            }
        ){
            Text(text = "Delete"

            )
        }
        Image(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(300.dp),

            painter = painterResource(id = R.drawable.mariodelete),
            contentDescription = "",
            contentScale = ContentScale.FillHeight
        )
    }

}

fun insertar(id:String, nombre:String, compañia:String, imagen:String){
    val url = "http://iesayala.ddns.net/sergioM/insertPersonajes.php/?id=$id&nombre=$nombre&compañia=$compañia&imagen=$imagen"
    leerUrl(url)
}

fun borrarPersonajes(id:String) {
    val url = "http://iesayala.ddns.net/sergioM/deletePersonajes.php/?id=$id"
    leerUrl(url)
}


fun leerUrl(urlString:String){
    GlobalScope.launch(Dispatchers.IO)   {
        val response = try {
            URL(urlString)
                .openStream()
                .bufferedReader()
                .use { it.readText() }
        } catch (e: IOException) {
            "Error with ${e.message}."
            Log.d("io", e.message.toString())
        } catch (e: Exception) {
            "Error with ${e.message}."
            Log.d("io", e.message.toString())
        }
    }

    return
}

@Composable
fun cargarJSON(): PersonajesInfo {
    val contest = LocalContext.current

    var personajes by rememberSaveable { mutableStateOf(PersonajesInfo()) }
    val personaje = PersonajeInstance.personajeInterface.personajeInformation()

    personaje.enqueue(object : Callback<PersonajesInfo> {
        override fun onResponse(
            call: Call<PersonajesInfo>,
            response: Response<PersonajesInfo>
        ) {
            val personajesInfo: PersonajesInfo? = response.body()
            if (personajesInfo!=null) {
                personajes = personajesInfo

            }else{
                println(personajes.toString())
            }

        }

        override fun onFailure(call: Call<PersonajesInfo>, t: Throwable) {

            Toast.makeText(contest, t.toString(), Toast.LENGTH_SHORT).show()
        }

    })

    return personajes

}

@Composable
fun Llamada() {
    var lista = cargarJSON()
    LazyColumn()

    {
        items(lista) { personaje ->
            Card(
                modifier = Modifier
                    .padding(8.dp, 4.dp)
                    .fillMaxWidth()
                    .width(100.dp)
                    .height(110.dp), shape = RoundedCornerShape(1.dp), elevation = 1.dp

            ) {
                Surface(color = Color.White) {

                    Row(
                        Modifier
                            .padding(4.dp)
                            .fillMaxSize()
                    ) {

                      CargarImagen(url = personaje.imagen)


                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxHeight()
                                .weight(0.8f)
                        )
                        {
                            Text(
                                text = personaje.id,
                                fontSize = 20.sp,

                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = personaje.nombre,
                                fontSize = 20.sp,

                                style = MaterialTheme.typography.subtitle1,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = personaje.compania,
                                fontSize = 15.sp,

                                style = MaterialTheme.typography.caption,
                                overflow = TextOverflow.Ellipsis,

                                modifier = Modifier
                                    .background(
                                        Color.LightGray
                                    )
                                    .padding(4.dp)
                            )

                        }
                    }
                }
            }

        }
    }
}
@Composable
fun CargarImagen(url: String) {
    Image(
        painter = rememberImagePainter(url),
        contentDescription = "Imagen",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(150.dp)
            .clip(RoundedCornerShape(100.dp)),
        contentScale = ContentScale.FillWidth
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NavigationDrawerComposeTheme {
        MainScreen()
        Greeting()
    }
}