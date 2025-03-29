package org.acme.resource;

import org.acme.entity.Project;
import org.acme.entity.ProjectDetail;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Project Resource", description = "Operations related to projects")
public class ProjectResource {

    @GET
    @Operation(summary = "Get all projects", description = "Retrieves a list of all projects")
    public List<Project> getAllProjects() {
        return Project.listAll(); // No necesitamos un JOIN FETCH aquí ya que eliminamos la propiedad 'details'
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get project by ID", description = "Retrieves a specific project by its ID")
    public Response getProjectById(@PathParam("id") Integer id) {
        Project project = Project.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(project).build();
    }

    @GET
    @Path("/{id}/details")
    @Operation(summary = "Get project details", description = "Retrieves the details for a specific project")
    public Response getProjectDetails(@PathParam("id") Integer id) {
        List<ProjectDetail> projectDetails = ProjectDetail.list("project.id", id); // Cambio aquí
        if (projectDetails.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(projectDetails).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new project", description = "Creates a new project and persists it in the database")
    public Response createProject(Project project) {
        project.createdAt = java.time.LocalDateTime.now();
        project.id = null; // Aseguramos que el id sea null para que se genere automáticamente
        project.persist();
        return Response.status(Response.Status.CREATED).entity(project).build();
    }


    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing project", description = "Updates an existing project's details by its ID")
    public Response updateProject(@PathParam("id") Integer id, Project updatedProject) {
        Project project = Project.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        project.nombre = updatedProject.nombre;
        project.status = updatedProject.status;
        project.persist();
        return Response.ok(project).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a project", description = "Deletes a project by its ID")
    public Response deleteProject(@PathParam("id") Integer id) {
        // Primero elimina los registros dependientes en 'projectdetail'
        long deletedDetails = ProjectDetail.delete("project.id = ?1", id);

        // Verifica si se eliminaron registros
        if (deletedDetails > 0) {
            System.out.println("Deleted " + deletedDetails + " project details.");
        }

        // Ahora elimina el proyecto
        boolean deleted = Project.deleteById(id);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }


    @POST
    @Path("/{id}/details")
    @Transactional
    @Operation(summary = "Add a project detail", description = "Adds a new detail to a specific project")
    public Response addProjectDetail(@PathParam("id") Integer id, ProjectDetail detail) {
        Project project = Project.findById(id);
        if (project == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        detail.project = project;
        detail.persist();
        return Response.status(Response.Status.CREATED).entity(detail).build();
    }
}
