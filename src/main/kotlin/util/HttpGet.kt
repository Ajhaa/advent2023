package util

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun httpGet(url: String, cookies: String): String {
    val client: HttpClient = HttpClient.newHttpClient()

    val req = HttpRequest.newBuilder()
        .uri(URI(url))
        .GET()
        .header("Cookie", cookies)
        .build()

    val response = client.send(req, HttpResponse.BodyHandlers.ofString())
    if (response.statusCode() != 200) {
        throw RuntimeException("Http request failed: ${response.body()}")
    }

    return response.body()
}