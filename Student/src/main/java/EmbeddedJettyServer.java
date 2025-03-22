package com.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class EmbeddedJettyServer {

    public static void main(String[] args) throws Exception {

        // Create a new Jetty server listening on port 8080
        Server server = new Server(8080);

        // Create a context handler to define the servlets and their mappings
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        // Add servlets to the Jetty context
        context.addServlet(new ServletHolder(StudentMarksServlet.class), "/student-marks");

        // Set the server handler to the context
        server.setHandler(context);

        // Start the server
        server.start();
        System.out.println("Jetty server started at http://localhost:8080");

        // Keep the server running until it is manually stopped
        server.join();
    }
}
