package aiss.gitminer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    // TODO: Uncomment after creating the repository
   @Autowired
   ProjectRepository projectRepository;

    // GET http://localhost:8080/api/projects
    @GetMapping
    public List<Project> findAll() {
        // TODO: COMPLETE
        return null;
    }

    // GET http://localhost:8080/api/projects/{id}
    @GetMapping("/{id}")
    public Project findOne(@PathVariable Long id) {
        // TODO: COMPLETE
        return null;
    }

    // POST http://localhost:8080/api/projects
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project createProject(@RequestBody @Valid Project project) {
        // TODO: COMPLETE
        Project _project = projectRepository
                            .save(new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues()));
        return _project;
    }

    // PUT http://localhost:8080/api/projects/{id}
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProject(@RequestBody @Valid Project updatedProject, @PathVariable Long id) {
        // TODO: COMPLETE

    }

    // DELETE http://localhost:8080/api/projects/{id}
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable Long id) {
        // TODO: COMPLETE
    }

}
