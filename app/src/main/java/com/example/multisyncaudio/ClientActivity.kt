package com.example.multisyncaudio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import okhttp3.*

class ClientActivity : AppCompatActivity() {
    private lateinit var connectBtn: Button
    private lateinit var statusText: TextView
    private lateinit var ipInput: EditText
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        connectBtn = findViewById(R.id.connectBtn)
        statusText = findViewById(R.id.statusText)
        ipInput = findViewById(R.id.ipInput)

        connectBtn.setOnClickListener { connectToHost() }
    }

    private fun connectToHost() {
        val ip = ipInput.text.toString().trim()
        val client = OkHttpClient()
        val request = Request.Builder().url("ws://echo.websocket.org").build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(ws: WebSocket, response: Response) {
                runOnUiThread { statusText.text = "Connected to test server" }
            }

            override fun onMessage(ws: WebSocket, text: String) {
                runOnUiThread { statusText.text = "Received: $text" }
            }
        })
    }
}
