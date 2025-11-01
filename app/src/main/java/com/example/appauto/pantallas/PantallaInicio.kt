package com.example.appauto.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
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
    irACrearProducto: () -> Unit,
    irACarrito: () -> Unit,
    onAgregarCarrito: (Producto) -> Unit
) {
    var textoBusqueda by remember { mutableStateOf(TextFieldValue("")) }
    var categoriaSeleccionada by remember { mutableStateOf("Todos") }
    val categorias = listOf("Todos", "Sedán", "Deportivo", "Camioneta", "SUV", "Eléctrico")

    val filtrados = productos.filter { p ->
        (categoriaSeleccionada == "Todos" || p.categoria == categoriaSeleccionada) &&
                p.nombre.contains(textoBusqueda.text, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // --- HEADER (título + carrito)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Catálogo de Autos", style = MaterialTheme.typography.h6)
            IconButton(onClick = irACarrito) {
                Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
            }
        }

        // --- BANNER
        AsyncImage(
            model = "https://www.autoshopping.cl/wp-content/themes/autoshopping/images/slider_header/banner_agosto_03.jpg",
            contentDescription = "Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- BUSCADOR
        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            label = { Text("Buscar productos...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // --- FILTROS (botones simples)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            categorias.forEach { cat ->
                val isSelected = cat == categoriaSeleccionada
                Button(
                    onClick = { categoriaSeleccionada = cat },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
                    )
                ) {
                    Text(cat)
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- LISTA (usa weight para ocupar el resto y evitar espacios en blanco)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filtrados) { producto ->
                Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = producto.imagenUrl,
                            contentDescription = producto.nombre,
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(producto.nombre, style = MaterialTheme.typography.subtitle1)
                            Text(producto.descripcion, maxLines = 2)
                            Text("Categoría: ${producto.categoria}")
                            Text("Precio: ${producto.precio}")
                        }
                        IconButton(onClick = { onAgregarCarrito(producto) }) {
                            Icon(Icons.Default.AddShoppingCart, contentDescription = "Agregar al carrito")
                        }
                    }
                }
            }
        }

        // --- BOTTOM NAV (simple Row, estable)
        Divider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* ya estás en Inicio */ }) { Text("Inicio") }
            Button(onClick = irACarrito) { Text("Carrito") }
            Button(onClick = irACrearProducto) { Text("Admin") }
        }
    }
}
