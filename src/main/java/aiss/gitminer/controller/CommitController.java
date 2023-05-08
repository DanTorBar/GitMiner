package aiss.gitminer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import aiss.gitminer.model.Commit;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CommitController {

    // TODO: Uncomment after creating the repositories
//    @Autowired
//    ProjectRepository projectRepository;
//    @Autowired
//    CommitRepository commitRepository;

    // GET http://localhost:8080/api/projects/{projectId}/commits
    @GetMapping("/projects/{projectId}/commits")
    public List<Commit> getAllCommitsByProjectId(@PathVariable(value="projectId") long projectId) {
        // TODO: COMPLETE
        return null;
    }

    // GET http://localhost:8080/api/commits/{id}
    @GetMapping("/commits/{id}")
    public Commit findOne(@PathVariable(value="id") Long id) {
        // TODO: COMPLETE
        return null;
    }

    // POST http://localhost:8080/api/projects/{projectId}/commits
    @PostMapping("/projects/{projectId}/commits")
    @ResponseStatus(HttpStatus.CREATED)
    public Commit createCommit(@RequestBody @Valid Commit commit, @PathVariable("projectId") long projectId) {
        // TODO: COMPLETE
        return null;
    }

    // PUT http://localhost:8080/api/commits/{id}
    @PutMapping("/commits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCommit(@RequestBody @Valid Commit updatedCommit, @PathVariable Long id) {
        // TODO: COMPLETE
    }

    // DELETE http://localhost:8080/api/commits/{id}
    @DeleteMapping("/commits/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommit(@PathVariable Long id) {
        // TODO: COMPLETE
    }

}
