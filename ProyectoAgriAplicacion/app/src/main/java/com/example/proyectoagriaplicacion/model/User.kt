package com.example.proyectoagriaplicacion.model

class User {
    var nombres: String = ""
    var apellidos: String= ""
    var departamento: String = ""

    var email: String = ""
    var telefono: String = ""
    var tipoUsuario: String = ""
    var id: String = ""
    var urlImagen: String = ""
    var contrasena: String= ""

    constructor() {}
    constructor(
        nombres: String,
        apellidos: String,
        departamento: String,

        email: String,
        telefono: String,
        tipoUsuario: String,
        id: String

    ) {
        this.nombres = nombres
        this.apellidos = apellidos
        this.departamento = departamento

        this.email = email
        this.telefono = telefono
        this.tipoUsuario = tipoUsuario
        this.id = id
        this.contrasena = contrasena
    }
}