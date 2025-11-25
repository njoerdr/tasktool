package org.example.adapter.rest;

import io.quarkus.qute.Template;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import org.example.core.domain.Task;
import org.example.core.port.TaskRepository;
import org.jboss.resteasy.reactive.RestHeader;

@Path("/tasks")
public class TaskResource {

    private final Template page;

    private final TaskRepository repository;

    public TaskResource(Template page, TaskRepository repository) {
        this.page = requireNonNull(page, "page is required");
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getJsonTasks() {

        return repository.retrieveAll();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtmlAllTasks(@RestHeader("HX-Request") boolean hxRequest) {

        if (hxRequest) {
            return page.getFragment("content").data("tasks", repository.retrieveAll()).render();
        } else {
            return page.data("tasks", repository.retrieveAll()).render();
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id}")
    public String getHtmlSingleTask(
            @RestHeader("HX-Request") boolean hxRequest,
            @PathParam("id") Long id) {

        if (hxRequest) {
            return page.getFragment("plaintask").data("task", repository.retrieve(id)).render();
        } else {
            return page.data("task", repository.retrieve(id), "id", id).render();
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Path("/{id}/edit")
    public String getHtmlTaskEditable(
            @RestHeader("HX-Request") boolean hxRequest,
            @PathParam("id") Long id) {

        if (hxRequest) {
            return page.getFragment("editabletask").data("task", repository.retrieve(id), "edit", true).render();
        } else {
            return page.data("task", repository.retrieve(id), "id", id, "edit", true).render();
        }
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response create(@RestHeader("HX-Request") boolean hxRequest, Task task) {

        if (Objects.isNull(task)) {
            return Response.notAcceptable(null).build();
        }
        repository.persist(Task.createTask(task.getName(), task.getPriority()));
        return Response.accepted().entity(getHtmlAllTasks(hxRequest)).build();
    }

    @Transactional
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Long id, Task task) {

        if (Objects.isNull(task)) {
            return Response.notAcceptable(null).build();
        }
        Task state = repository.retrieve(id);
        state.setName(task.getName());
        if (task.isDone()) {
            state.done();
        } else {
            state.undone();
        }
        state.setPriority(task.getPriority());
        repository.update(state);
        return Response.accepted().entity(
                page.getFragment("plaintask").data("task", state).render()).build();
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {

        repository.delete(id);
        return Response.accepted().build();
    }

}
