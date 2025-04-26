package com.rkbapps.imagesearch.network

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*

class SocketClient(private val scope: CoroutineScope) {
    private var socket: Socket? = null
    private var inputChannel: ByteReadChannel? = null
    private var outputChannel: ByteWriteChannel? = null

    suspend fun connect(host: String, port: Int) {
        try {
            val selectorManager = SelectorManager(Dispatchers.IO)
            socket = aSocket(selectorManager).tcp().connect(host, port)
            inputChannel = socket?.openReadChannel()
            outputChannel = socket?.openWriteChannel(autoFlush = true)
        } catch (e: Exception) {
            println("Connection failed: ${e.message}")
            throw e
        }
    }

    suspend fun send(message: String) {
        try {
            outputChannel?.writeStringUtf8("$message\n")
        } catch (e: Exception) {
            println("Send failed: ${e.message}")
            throw e
        }
    }

    suspend fun sendBytes(data: ByteArray) {
        try {
            outputChannel?.writeFully(data, 0, data.size)
        } catch (e: Exception) {
            println("Send failed: ${e.message}")
            throw e
        }
    }

    suspend fun receiveString(): String? {
        return try {
            inputChannel?.readUTF8Line()
        } catch (e: Exception) {
            println("Receive failed: ${e.message}")
            null
        }
    }

    suspend fun receiveBytes(size: Int): ByteArray? {
        return try {
            val buffer = ByteArray(size)
            inputChannel?.readFully(buffer, 0, size)
            buffer
        } catch (e: Exception) {
            println("Receive failed: ${e.message}")
            null
        }
    }

    fun disconnect() {
        socket?.close()
        socket = null
        inputChannel = null
        outputChannel = null
    }
}