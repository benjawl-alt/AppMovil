package com.example.appauto

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appauto.model.Producto
import com.example.appauto.pantallas.PantallaCrearProducto
import com.example.appauto.pantallas.PantallaInicio
import com.example.appauto.pantallas.PantallaLogin
import com.example.appauto.pantallas.PantallaRegistro

@Composable
fun Navegacion() {
    val navController = rememberNavController()

    // usuario registrado (temporal, en memoria)
    var correoRegistrado by remember { mutableStateOf("") }
    var claveRegistrada by remember { mutableStateOf("") }

    // lista base de productos (los 3 que querías)
    var productos by remember {
        mutableStateOf(
            listOf(
                Producto(
                    nombre = "Toyota Corolla",
                    descripcion = "Sedán compacto confiable y eficiente.",
                    precio = "12000",
                    categoria = "Sedán",
                    imagenUrl = "https://scene7.toyota.eu/is/image/toyotaeurope/COR0001a_25_WEB_CROP:Large-Landscape?ts=0&resMode=sharp2&op_usm=1.75,0.3,2,0&fmt=png-alpha"
                ),
                Producto(
                    nombre = "Ford Mustang",
                    descripcion = "Deportivo potente con diseño clásico.",
                    precio = "35000",
                    categoria = "Deportivo",
                    imagenUrl = "https://www.ford.es/content/dam/guxeu/rhd/central/cars/S650-Mustang/my26/column_cards/ford-eu-S650_Nite_Pony_CG_Thumbnail_1000x667.jpg"
                ),
                Producto(
                    nombre = "Chevrolet Silverado",
                    descripcion = "Camioneta robusta ideal para trabajo pesado.",
                    precio = "40000",
                    categoria = "Camioneta",
                    imagenUrl = "https://di-uploads-pod44.dealerinspire.com/nyechevrolet/uploads/2024/03/2024-chevrolet-silverado-hd-zr2-2-1.jpg"
                )
            )
        )
    }

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            PantallaLogin(
                alIngresar = { navController.navigate("inicio") },
                alIrARegistro = { navController.navigate("registro") },
                correoRegistrado = correoRegistrado,
                claveRegistrada = claveRegistrada
            )
        }

        composable("registro") {
            PantallaRegistro(
                alRegistrar = { correo, clave ->
                    correoRegistrado = correo
                    claveRegistrada = clave
                    navController.popBackStack() // vuelve al login
                },
                alCancelar = { navController.popBackStack() }
            )
        }

        composable("inicio") {
            // Llamada con la firma antigua que tú querías: productos + irACrearProducto
            PantallaInicio(
                productos = productos,
                irACrearProducto = { navController.navigate("crear") }
            )
        }

        composable("crear") {
            PantallaCrearProducto(
                onGuardar = { nuevoProducto ->
                    productos = productos + nuevoProducto
                    navController.popBackStack()
                },
                onCancelar = { navController.popBackStack() }
            )
        }
    }
}
