package com.example.appauto.pantallas

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import com.example.appauto.model.Producto

@Composable
fun PantallaCrearProducto(
    onGuardar: (Producto) -> Unit,
    onCancelar: () -> Unit
) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var precio by rememberSaveable { mutableStateOf("") }
    var imagenUrl by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf("") }

    val categorias = listOf("Sedán", "Deportivo", "Camioneta", "SUV", "Eléctrico")
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = onCancelar) { Text("Cancelar") }
                    Button(onClick = {
                        if (nombre.isNotBlank() && precio.isNotBlank()) {
                            onGuardar(Producto(nombre, descripcion, precio, categoria, imagenUrl))
                        }
                    }) { Text("Guardar") }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(padding)) {

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown estable
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = categoria,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    categorias.forEach { opcion ->
                        DropdownMenuItem(
                            onClick = {
                                categoria = opcion
                                expanded = false
                            },
                            text = { Text(opcion) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = imagenUrl, onValueChange = { imagenUrl = it }, label = { Text("URL de imagen") }, modifier = Modifier.fillMaxWidth())
        }
    }
}
