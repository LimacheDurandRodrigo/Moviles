package com.example.proyectoagriaplicacion.model


class Commentary {

    var description: String? = null
    var stars: String? = null
    var nameCommentator: String? = null
    var date: String? = null
    var urlImagen: String? = null
    var idCommentary: String? = null
    var idProfile: String? = null
    var idCommentator: String? = null

    constructor(

        description: String?,
        stars: String?,
        idCommentary: String?,
        nameCommentator: String?,
        idProfile: String?,
        idCommentator: String?
    ) {

        this.description = description
        this.stars = stars
        this.idCommentary = idCommentary
        this.nameCommentator = nameCommentator
        this.idProfile = idProfile
        this.idCommentator = idCommentator
    }

    constructor() {}
}