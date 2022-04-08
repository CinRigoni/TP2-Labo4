import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteHTTP {
    public static String responseBody;

    public static String peticion(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(ClienteHTTP::catchResponseBody)
                .join();
        return responseBody;
    }

    public static void catchResponseBody(String response){
        responseBody = response;
    }
}
