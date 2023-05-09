package aiss.gitminer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.model.Project;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    // TODO: Uncomment after creating the repository
   @Autowired
   ProjectRepository projectRepository;

    // POST Create project --> http://localhost:8080/gitminer/projects
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody @Valid Project project) {
        // TODO: COMPLETE
        Project _project = projectRepository
                            .save(new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues()));
        return _project;
    }

    // @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    // public Project create(@RequestBody Project project) {
    //     // Project _project = new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues());
    //     return project;
    // }
    // Codigo antiguo miguel

    // GET Get all projects --> http://localhost:8080/gitminer/projects
    @GetMapping
    public List<Project> findAll() {
        return repository.findAll();
    }
    


    // GET Get project --> http://localhost:8080/gitminer/projects/{id}
    @GetMapping("/{id}")
    public Project findOne(@PathVariable String id) {
        Optional<Project> project = repository.findById(id);
        return project.get();
    }

}
