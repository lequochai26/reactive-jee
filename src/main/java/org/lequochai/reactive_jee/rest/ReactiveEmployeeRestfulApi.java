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

import io.reactivex.rxjava3.core.Single;

@Path ("/remployees")
public class ReactiveEmployeeRestfulApi {
    
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    public Response<List<Employee>> getEmployees(@QueryParam ("keyword") String keyword) {
        Response<List<Employee>> response = new Response<>();
        Status status = new Status(false);

        Single<List<Employee>> single = null;
        if (keyword == null) {
            single = ReactiveEmployeeContainer
                .getInstance()
                .getAll();
        }
        else {
            single = ReactiveEmployeeContainer
                .getInstance()
                .get(keyword);
        }

        single.subscribe(
            r -> {
                response.setResult(r);
                response.setSuccess(true);
                status.setDone(true);
            },
            t -> {
                response.setSuccess(false);
                response.setMessage(t.getMessage());
                status.setDone(true);
            }
        );

        while (!status.isDone()) {}

        return response;
    }

    @GET
    @Path ("/{id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Employee> getEmployee(@PathParam ("id") String idStr) {
        Status status = new Status(false);
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
            .subscribe(
                target -> {
                    response.setSuccess(true);
                    response.setResult(target);
                    status.setDone(true);
                },
                t -> {
                    response.setSuccess(false);
                    response.setMessage(t.getMessage());
                    status.setDone(true);
                },
                () -> {
                    response.setSuccess(true);
                    status.setDone(true);
                }
            );

        while (!status.isDone()) {}

        return response;
    }

    @POST
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> insertEmployee(Employee employee) {
        Status status = new Status(false);
        Response<Void> response = new Response<>();

        ReactiveEmployeeContainer
            .getInstance()
            .insert(employee)
            .subscribe(
                r -> {
                    response.setSuccess(true);
                    status.setDone(true);
                },
                t -> {
                    response.setSuccess(false);
                    response.setMessage(t.getMessage());
                    status.setDone(true);
                }
            );

        while (!status.isDone()) {}

        return response;
    }

    @PUT
    @Consumes (MediaType.APPLICATION_JSON)
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> updateEmployee(Employee employee) {
        Status status = new Status(false);
        Response<Void> response = new Response<>();

        ReactiveEmployeeContainer
            .getInstance()
            .update(employee)
            .subscribe(
                r -> {
                    response.setSuccess(true);
                    status.setDone(true);
                },
                t -> {
                    response.setSuccess(false);
                    response.setMessage(t.getMessage());
                    status.setDone(true);
                }
            );

        while (!status.isDone()) {}

        return response;
    }

    @DELETE
    @Path ("/${id}")
    @Produces (MediaType.APPLICATION_JSON)
    public Response<Void> deleteEmployee(@PathParam ("id") String idStr) {
        Status status = new Status(false);
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
            .subscribe(
                r -> {
                    response.setSuccess(true);
                    status.setDone(true);
                },
                t -> {
                    response.setSuccess(false);
                    response.setMessage(t.getMessage());
                    status.setDone(true);
                }
            );

        while (!status.isDone()) {}

        return response;
    }

    @DELETE
    @Path ("/generateEmployees")
    public Response<Void> cancelGenerateEmployees() {
        Status status = new Status(false);
        Response<Void> response = new Response<>();

        ReactiveEmployeeContainer
            .getInstance()
            .cancelGenerateEmployees()
            .subscribe(
                r -> {
                    response.setSuccess(true);
                    status.setDone(true);
                },
                t -> {
                    response.setSuccess(false);
                    response.setMessage(t.getMessage());
                    status.setDone(true);
                }
            );

        while (!status.isDone()) {}

        return response;
    }

    // Inner classes:
    public class Status {
        private boolean done;

        public Status() {
        }

        public Status(boolean done) {
            this.done = done;
        }

        public boolean isDone() {
            return done;
        }

        public void setDone(boolean done) {
            this.done = done;
        }
    }
}
