package com.luoyuan.studentmanager

import org.junit.Assert.*
import org.junit.Test

class StudentTest {
    private val valid = Student("2024110801", "测试学生", 1, "2024级2班")
    @Test fun acceptsCompleteStudent() { assertNull(valid.validate(emptyList())) }
    @Test fun rejectsEmptyId() { assertEquals(StudentError.EMPTY_ID, valid.copy(studentId = "").validate(emptyList())) }
    @Test fun rejectsNonNumericId() { assertEquals(StudentError.INVALID_ID, valid.copy(studentId = "A12").validate(emptyList())) }
    @Test fun rejectsBlankName() { assertEquals(StudentError.EMPTY_NAME, valid.copy(name = "  ").validate(emptyList())) }
    @Test fun requiresGender() { assertEquals(StudentError.MISSING_GENDER, valid.copy(gender = 0).validate(emptyList())) }
    @Test fun requiresClass() { assertEquals(StudentError.EMPTY_CLASS, valid.copy(className = "").validate(emptyList())) }
    @Test fun rejectsDuplicateId() { assertEquals(StudentError.DUPLICATE_ID, valid.validate(listOf(valid.studentId))) }
    @Test fun serializesStudent() {
        val bytes = java.io.ByteArrayOutputStream()
        java.io.ObjectOutputStream(bytes).use { it.writeObject(valid) }
        val restored = java.io.ObjectInputStream(java.io.ByteArrayInputStream(bytes.toByteArray())).use { it.readObject() }
        assertEquals(valid, restored)
    }
}
