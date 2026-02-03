package com.example.multisyncaudio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import kotlinx.coroutines.*
import okhttp3.*

class HostActivity : AppCompatActivity() {
    private lateinit var sendBtn: Button
    private lateinit var statusText: TextView
    private val serverUrl = "ws://echo.websocket.org"  // temporary test server
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host)

        sendBtn = findViewById(R.id.sendMsg)
        statusText = findViewById(R.id.statusText)

        setupConnection()

        sendBtn.setOnClickListener {
            webSocket?.send("PLAY_NOW")
            statusText.text = "Sent: PLAY_NOW"
        }
    }

    private fun setupConnection() {
        val client = OkHttpClient()
        val request = Request.Builder().url(serverUrl).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                runOnUiThread { statusText.text = "Connected to test server" }
            }
        })
    }
}
