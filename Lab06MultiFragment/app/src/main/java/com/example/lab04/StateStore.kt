package com.example.lab04

import android.content.Context
import android.system.ErrnoException
import android.system.Os
import java.io.File
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class StateStore(private val context: Context) {
    fun <T : Serializable> load(name: String, type: Class<T>, default: () -> T): T =
        try {
            ObjectInputStream(context.openFileInput(name)).use { input ->
                val value = input.readObject()
                if (type.isInstance(value)) type.cast(value)!! else default()
            }
        } catch (_: IOException) {
            default()
        } catch (_: ClassNotFoundException) {
            default()
        }

    fun save(name: String, value: Serializable) {
        val pending = "$"+"{name}.tmp"
        context.openFileOutput(pending, Context.MODE_PRIVATE).use { file ->
            ObjectOutputStream(file).use { output ->
                output.writeObject(value)
                output.flush()
                file.fd.sync()
            }
        }
        try {
            Os.rename(File(context.filesDir, pending).path, File(context.filesDir, name).path)
        } catch (error: ErrnoException) {
            throw IOException(error)
        }
    }
}
