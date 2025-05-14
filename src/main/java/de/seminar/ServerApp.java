package de.seminar;

import de.seminar.utils.CORSFilter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;

public class ServerApp {

    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://0.0.0.0:8080/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        // Create a resource config that scans for JAX-RS resources and providers in the package
        final ResourceConfig rc = new ResourceConfig().packages("de.seminar.resources");

        rc.register(JacksonFeature.class);
        rc.register(CORSFilter.class);

        SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator();
        sslContextConfigurator.setKeyStorePass("geheim");
        sslContextConfigurator.setKeyStoreFile("keystore_server");
        sslContextConfigurator.setTrustStorePass("geheim");
        sslContextConfigurator.setTrustStoreFile("truststore_server");

        SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfigurator(sslContextConfigurator);
        sslEngineConfigurator.setClientMode(false);
        sslEngineConfigurator.setNeedClientAuth(false);

        // HTTPS Listener hinzufügen
        NetworkListener httpsListener = new NetworkListener("https-listener", "0.0.0.0", 443);
        httpsListener.setSecure(true);
        httpsListener.setSSLEngineConfig(sslEngineConfigurator);

        // Create and start a new instance of the Grizzly HTTP server
        // Exposing the Jersey application at BASE_URI
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);

        // Füge den HTTPS-Listener zum Server hinzu
        server.addListener(httpsListener);

        return server;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Start the server
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started at %s", BASE_URI));
        while (true) {
            Thread.sleep(1000);
        }
    }
}