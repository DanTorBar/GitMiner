package aiss.gitminer.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import aiss.gitminer.exception.ProjectNotFoundException;
import aiss.gitminer.model.Project;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {
    
    @Autowired
    ProjectRepository repository;

    // POST Create project --> http://localhost:8080/gitminer/projects
    @Operation (
        summary = "Insert a project",
        description = "Add a new project whose data is passed in the body of the request in JSON format",
        tags = { "projects", "post" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "201", content = { @Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        Project _project = repository
            .save(new Project(null, project.getName(), null, null, null));
        return _project;
    }

    /*
        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping
        public Project create(@Valid @RequestBody Project project) {
            return repository.create(project);
        }
        Otra posibilidad

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public Project create(@RequestBody Project project) {
            Project _project = new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues());
            return project;
        }
        Codigo antiguo miguel
    */
    

    // GET Get all projects --> http://localhost:8080/gitminer/projects
    @Operation (
        summary = "Retrieve all projects",
        description = "Get all projects",
        tags = { "projects", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
    })
    @GetMapping
    // public List<Project> findAll() {
    //     return repository.findAll();
    // }
    public List<Project> findAll(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String order,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {

        Pageable paging;

        if(order != null) {
            if(order.startsWith("-")) {
                paging = PageRequest.of(page, size, Sort.by(order.substring(1)).descending());
            } else {
                paging = PageRequest.of(page, size, Sort.by(order).ascending());
            }
        } else {
            paging = PageRequest.of(page, size); 
        }

        Page<Project> pageProjects;

        if(name == null) {
            pageProjects = repository.findAll(paging);
        } else {
            pageProjects = repository.findByName(name, paging);
        }

        return pageProjects.getContent();

    }
    


    // GET Get project --> http://localhost:8080/gitminer/projects/{id}
    @Operation (
        summary = "Retrieve a project by id",
        description = "Get a project object by specifying its id",
        tags = { "projects", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Project.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Project findOne(@Parameter(description = "id of the project to be searched") @PathVariable String id) throws ProjectNotFoundException {
        Optional<Project> project = repository.findById(id);
        if(!project.isPresent()) {
            throw new ProjectNotFoundException();
       }
        return project.get();
    }

}
