package com.example.appauto.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProductos() {
    var filtro by remember { mutableStateOf("") }
    var ordenarPor by remember { mutableStateOf("") }
    var nombreNuevo by remember { mutableStateOf("") }

    // Lista de productos
    var listaProductos by remember { mutableStateOf(listOf<String>()) }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text("Barra inferior / acciones")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título principal
            Text("Productos", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))

            // Campo de búsqueda
            OutlinedTextField(
                value = filtro,
                onValueChange = { filtro = it },
                label = { Text("Filtro / Búsqueda") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Ordenar por
            OutlinedTextField(
                value = ordenarPor,
                onValueChange = { ordenarPor = it },
                label = { Text("Ordenar por ▼") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para agregar nuevo producto
            OutlinedTextField(
                value = nombreNuevo,
                onValueChange = { nombreNuevo = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (nombreNuevo.isNotEmpty()) {
                        listaProductos = listaProductos + nombreNuevo
                        nombreNuevo = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar producto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de productos
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(listaProductos) { producto ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(producto)
                            Button(
                                onClick = {
                                    listaProductos = listaProductos - producto
                                }
                            ) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}
