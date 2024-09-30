package com.pwlimaverde.todolist.core.models

import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
)


fun todoToMap(todo: Todo): Map<String, Any> {
    return todo::class.memberProperties.associate {
        it.name to it.getter.call(todo)!!
    }
}


fun <T : Any> mapToDataClass(map: Map<String, Any>, clazz: KClass<T>): T {
    val constructor = clazz.constructors.first()
    val parameters = constructor.parameters
    val arguments = parameters.associateWith { parameter ->
        when (parameter.type.classifier) {
            Long::class -> map[parameter.name]?.toString()?.toLongOrNull()
            String::class -> map[parameter.name]?.toString()
            Boolean::class -> map[parameter.name]?.toString()?.toBooleanStrictOrNull()
            else -> throw IllegalArgumentException("Unsupported type: ${parameter.type}")
        }
    }
    return constructor.callBy(arguments)
}

