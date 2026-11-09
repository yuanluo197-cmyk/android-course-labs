package com.luoyuan.studentmanager

import java.io.Serializable

data class Student(
    val studentId: String,
    val name: String,
    val gender: Int,
    val className: String
) : Serializable {
    fun validate(existingIds: Collection<String>): StudentError? = when {
        studentId.isBlank() -> StudentError.EMPTY_ID
        studentId.any { !it.isDigit() } -> StudentError.INVALID_ID
        name.isBlank() -> StudentError.EMPTY_NAME
        gender !in 1..2 -> StudentError.MISSING_GENDER
        className.isBlank() -> StudentError.EMPTY_CLASS
        studentId in existingIds -> StudentError.DUPLICATE_ID
        else -> null
    }
}

enum class StudentError {
    EMPTY_ID, INVALID_ID, EMPTY_NAME, MISSING_GENDER, EMPTY_CLASS, DUPLICATE_ID
}
