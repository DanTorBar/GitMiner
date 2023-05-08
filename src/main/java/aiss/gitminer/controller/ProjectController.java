package aiss.gitminer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository repository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Project create(@RequestBody Project project) {
        // Project _project = new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues());
        return project;
    }

    
}
