package com.example.proyectoagriaplicacion.model

class Product {
    constructor() {}
    constructor(
        urlImagen: String?,
        nombre: String?,
        descripcion: String?,
        precio: String?,
        cantidad: String?,
        idUsuario: String?,
        idProducto: String?
    ) {
        this.urlImagen = urlImagen
        this.nombre = nombre
        this.descripcion = descripcion
        this.precio = precio
        this.cantidad = cantidad
        this.idUsuario = idUsuario
        this.idProducto = idProducto
    }

    var nombre: String? = ""
    var descripcion: String? = ""
    var precio: String? = ""
    var cantidad: String? = ""
    var urlImagen: String? = ""
    var idUsuario: String? = ""
    var idProducto: String? = ""
    var estado: String? = ""
    var fecha: String? = ""
    var departamento: String? = ""
    var lat: String? = ""
    var lng: String? = ""
    var tipo: String? = ""
}