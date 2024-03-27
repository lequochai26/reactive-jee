package org.lequochai.reactive_jee.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.lequochai.reactive_jee.models.Employee;
import org.lequochai.reactive_jee.models.ReactiveEmployeeContainer;
import org.lequochai.reactive_jee.response.Response;

@Path ("/remployees")
public class ReactiveEmployeeRestfulApi {
    
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response<List<Employee>> getEmployees(@QueryParam ("keyword") String keyword) {
        Response<List<Employee>> response = new Response<>();

        if (keyword == null) {
            ReactiveEmployeeContainer
                .getInstance()
                .getAll()
                .blockingSubscribe(
                    r -> {
                        response.setSuccess(true);
                        response.setResult(r);
                    },
                    e -> {
                        response.setSuccess(false);
                        response.setMessage(e.getMessage());
                    }
                );
        }
        else {
            ReactiveEmployeeContainer
                .getInstance()
                .get(keyword)
                .blockingSubscribe(
                    r -> {
                        response.setSuccess(true);
                        response.setResult(r);
                    },
                    e -> {
                        response.setSuccess(false);
                        response.setMessage(e.getMessage());
                    }
                );
        }

        return response;
    }

    @GET
    @Path ("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Employee> getEmployee(@PathParam ("id") String idStr) {
        Response<Employee> response = new Response<>();

        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("ID must be an integer!");
            return response;
        }

        ReactiveEmployeeContainer
            .getInstance()
            .get(id)
            .blockingSubscribe(
                r -> {
                    response.setSuccess(true);
                    response.setResult(r);
                },
                e -> {
                    response.setSuccess(false);
                    response.setMessage(e.getMessage());
                }
            );

        return response;
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> insertEmployee(Employee employee) {
        Response<Void> response = new Response<>();

        ReactiveEmployeeContainer
            .getInstance()
            .insert(employee)
            .blockingSubscribe(
                r -> {
                    response.setSuccess(true);
                },
                e -> {
                    response.setSuccess(false);
                    response.setMessage(e.getMessage());
                }
            );

        return response;
    }

    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> updateEmployee(Employee employee) {
        Response<Void> response = new Response<>();

        ReactiveEmployeeContainer
            .getInstance()
            .update(employee)
            .blockingSubscribe(
                r -> {
                    response.setSuccess(true);
                },
                e -> {
                    response.setSuccess(false);
                    response.setMessage(e.getMessage());
                }
            );

        return response;
    }

    @DELETE
    @Path ("/${id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> deleteEmployee(@PathParam ("id") String idStr) {
        Response<Void> response = new Response<>();

        int id;
        try {
            id = Integer.parseInt(idStr);
        }
        catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("ID must be an integer!");
            return response;
        }

        ReactiveEmployeeContainer
            .getInstance()
            .delete(id)
            .blockingSubscribe(
                r -> {
                    response.setSuccess(true);
                },
                e -> {
                    response.setSuccess(false);
                    response.setMessage(e.getMessage());
                }
            );

        return response;
    }

    @DELETE
    @Path ("/generateEmployees")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> cancelGenerateEmployees() {
        Response<Void> response = new Response<>();

        ReactiveEmployeeContainer
            .getInstance()
            .cancelGenerateEmployees()
            .blockingSubscribe(
                r -> {
                    response.setSuccess(true);
                },
                e -> {
                    response.setSuccess(false);
                    response.setMessage(e.getMessage());
                }
            );

        return response;
    }
}
