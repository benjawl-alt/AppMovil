package com.example.appauto.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.appauto.model.Producto

@Composable
fun PantallaInicio(
    productos: List<Producto>,
    irACrearProducto: () -> Unit
) {
    var textoBusqueda by remember { mutableStateOf(TextFieldValue("")) }
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    val categorias = listOf("Todos", "Sedán", "Deportivo", "Camioneta", "SUV", "Eléctrico")

    val filtrados = productos.filter { p ->
        (categoriaSeleccionada == "Todos" || p.categoria == categoriaSeleccionada) &&
                p.nombre.contains(textoBusqueda.text, ignoreCase = true)
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = irACrearProducto) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Banner superior: imagen remota (si quieres usar recurso, cambia AsyncImage por painterResource)
            AsyncImage(
                model = "https://www.autoshopping.cl/wp-content/themes/autoshopping/images/slider_header/banner_agosto_03.jpg",
                contentDescription = "Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Barra de búsqueda
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                label = { Text("Buscar productos...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Filtro por categoría (chips simples)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                categorias.forEach { cat ->
                    val selected = cat == categoriaSeleccionada
                    AssistChip(
                        onClick = { categoriaSeleccionada = cat },
                        label = { Text(cat) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (selected) MaterialTheme.colorScheme.primaryContainer
                            else MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Lista
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filtrados) { producto ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /*detalle*/ },
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = producto.imagenUrl,
                                contentDescription = producto.nombre,
                                modifier = Modifier.size(100.dp),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(producto.nombre, style = MaterialTheme.typography.titleMedium)
                                Text(producto.descripcion, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
                                Text("Categoría: ${producto.categoria}", style = MaterialTheme.typography.bodySmall)
                                Text("Precio: ${producto.precio}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
