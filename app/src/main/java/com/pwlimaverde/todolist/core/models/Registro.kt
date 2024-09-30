package com.pwlimaverde.todolist.core.models

data class Registro(
    val colecao: String,
    val documento: String,
    val subcolecao: Registro? = null,
    val dados: Map<String, Any>? = null
)