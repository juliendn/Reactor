package fr.juliendenadai.reaktor.sample.debug

import android.util.Log
import com.google.gson.Gson
import com.koushikdutta.async.callback.CompletedCallback
import com.koushikdutta.async.http.WebSocket
import com.koushikdutta.async.http.server.AsyncHttpServer
import fr.juliendenadai.reaktor.Debug
import fr.juliendenadai.reaktor.DebugData
import io.reactivex.Observer
import io.reactivex.subjects.PublishSubject


class WebsocketDebug(port: Int, path: String) : AsyncHttpServer(), Debug {

    private var websockets = emptyList<WebSocket>()

    override val logger: Observer<List<DebugData>> by lazy {
        PublishSubject.create<List<DebugData>>().apply {
            subscribe { message ->
                websockets.forEach { it.send(Gson().toJson(message)) }
            }
        }
    }

    init {
        websocket(path, { websocket, _ ->
            this.websockets += websocket.apply {
                closedCallback = CompletedCallback {
                    websockets -= this
                }

                stringCallback = WebSocket.StringCallback {
                    Log.d("Meteo", "New message $it")
                }
            }
        })

        listen(port)
    }
}
