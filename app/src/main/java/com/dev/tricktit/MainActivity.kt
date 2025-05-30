package com.dev.tricktit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dev.tricktit.domain.timer.Timer
import com.dev.tricktit.presentation.maps.MapSection
import com.dev.tricktit.ui.theme.TricktItTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.time.Duration

//Create this annotation for use hill injector dependency
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //nos permite hacer la activacion de la corrutina
        lifecycleScope.launch {
            //usamos el collect para iniciar el flujo del timer
            Timer.timeAndEmit().collect{
                Log.d("FlowTimer", "Timer interval: $it")
            }
        }

        Timer.timeAndEmit()
            //inicia con un valor inicial Duration Zero en este caso
            //lleva un acumulador y el value actual
            //con eso se puede hacer operaciones
            .scan(Duration.ZERO){
                acc, value -> acc+ value
            }.
            //zip emite valores solo cuando ambos Flows han emitido un nuevo valor.
            //en este caso timeAndEmit se emite cada 200 milisegundos en caso del random es cada 1 segundo
            //lo cual es controlado usando zip la emision cada 1 segundo
            zip(
                Timer.randomFlow()
            ){
                time,randomValue ->
                time to randomValue
            }.onEach {
                Log.d("ZipFlow","value: ${it.first} - ${it.second}")
            }.launchIn(lifecycleScope)

        //Tambien permite recopilar la infomracion de un timer y emitirla
        Timer.timeAndEmit()
            .onEach {
                Log.d("FlowTimer", "Timer interval: $it")
            }
            .launchIn(lifecycleScope)



        setContent {
            val navController = rememberNavController()
            TricktItTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    innerpadding -> Greeting(
                        name = "Hello",
                        modifier = Modifier.padding(innerpadding)
                    )

                }
                Box (
                    modifier = Modifier.fillMaxSize()
                ){
                    NavHost(
                        navController = navController,
                        startDestination = MapScreenDes
                    ){
                        composable<MapScreenDes>(){
                            MapSection(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        composable<CameraScreenDes>(){}
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@kotlinx.serialization.Serializable
data object MapScreenDes

@Serializable
data object CameraScreenDes