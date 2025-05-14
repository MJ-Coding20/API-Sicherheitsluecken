package de.seminar.resources;

import de.seminar.objects.Credentials;
import de.seminar.objects.Invoice;
import de.seminar.objects.PDF;
import de.seminar.services.APIService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Path("")
public class APIResource {
    // endpoint for unrestricted resource consumption
    @GET
    @Path("/api/fibonacci")
    public Response fibonacci(@QueryParam("index") int index) {
        return Response.ok().entity(APIService.calculateFibonacci(index)).build();
    }

    // endpoints for login
    @POST
    @Path("/api/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(Credentials creds) {
        if(APIService.checkLogin(creds.getUsername(), creds.getPassword()))
            return Response.ok().build();
        return Response.status(Response.Status.FORBIDDEN).build();
    }

    @GET
    @Path("/login")
    public Response getLoginPage() {
        String html;
        InputStream htmlStream = getClass().getClassLoader().getResourceAsStream("Login.html");

        if (htmlStream == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Login page not found.")
                    .build();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(htmlStream))) {
            html = reader.lines().reduce("", (acc, line) -> acc + line + "\n");
        } catch (IOException e) {
            return Response.serverError().entity("Error reading HTML file").build();
        }

        return Response.ok(html, MediaType.TEXT_HTML).build();
    }

    // endpoint for IDOR
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/api/profile")
    public Response profile(@QueryParam("id") int id) {
        return Response.ok().entity(APIService.getProfile(id)).build();
    }

    @GET
    @Path("/profile")
    public Response getProfilePage() {
        String html;
        InputStream htmlStream = getClass().getClassLoader().getResourceAsStream("Profile.html");

        if (htmlStream == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Login page not found.")
                    .build();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(htmlStream))) {
            html = reader.lines().reduce("", (acc, line) -> acc + line + "\n");
        } catch (IOException e) {
            return Response.serverError().entity("Error reading HTML file").build();
        }

        return Response.ok(html, MediaType.TEXT_HTML).build();
    }

    //endpoint for picture

    @GET
    @Path("/catsite")
    public Response getCatPage() {
        String html;
        InputStream htmlStream = getClass().getClassLoader().getResourceAsStream("Catsite.html");

        if (htmlStream == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Catsite page not found.")
                    .build();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(htmlStream))) {
            html = reader.lines().reduce("", (acc, line) -> acc + line + "\n");
        } catch (IOException e) {
            return Response.serverError().entity("Error reading HTML file").build();
        }

        return Response.ok(html, MediaType.TEXT_HTML).build();
    }

    @GET
    @Path("/api/picture")
    public Response getPicture(@QueryParam("url") String url) {
        try {
            return Response.ok().entity(APIService.getPicture(url)).build();
        } catch (IOException e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Could not fetch image from remote URL.")
                    .build();
        } catch (InterruptedException e) {
            return Response.status(Response.Status.BAD_GATEWAY)
                    .entity("Could not fetch image from remote URL.")
                    .build();
        }
    }

    @GET
    @Path("/rechnung")
    public Response getRechnung() {
        String html;
        InputStream htmlStream = getClass().getClassLoader().getResourceAsStream("Rechnung.html");

        if (htmlStream == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Rechnung page not found.")
                    .build();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(htmlStream))) {
            html = reader.lines().reduce("", (acc, line) -> acc + line + "\n");
        } catch (IOException e) {
            return Response.serverError().entity("Error reading HTML file").build();
        }

        return Response.ok(html, MediaType.TEXT_HTML).build();
    }


    //TODO endpoint for rechnung
    @POST
    @Path("/api/rechnung")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response getRechnung(Invoice inv) {
        try {
            return Response.ok().entity(APIService.getRechnung(inv)).header("Content-Disposition", "inline; filename=rechnung.pdf").build();
        } catch (IOException | InterruptedException e) {
            return Response.serverError().build();
        }

    }

    @POST
    @Path("/api2/pdfGenerator")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/pdf")
    public Response getPDF(PDF pdf) {
        return Response.ok().entity(APIService.getPDF(pdf)).build();
    }
}
