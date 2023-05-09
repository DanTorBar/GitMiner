package aiss.gitminer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.exception.CommitNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.ProjectRepository;

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

    // GET Get all commits --> http://localhost:8080/gitminer/commits
    @GetMapping
    public List<Commit> findAll() {
        return commitRepository.findAll();
    }

    // GET get commit --> http://localhost:8080/gitminer/commits/{id}
    @GetMapping("/{id}")
    public Commit findOne(@PathVariable String id) {
        Optional<Commit> commit = commitRepository.findById(id);
        return commit.get();
    }

    // GET get commit by email address --> http://localhost:8080/gitminer/commits?email={emailAddress}
    @GetMapping("/commits")
    public List<Commit> findCommits(@RequestParam(value="author_email", required = false) String author_email) {
        List<Commit> commits = findAll();
        if (author_email != null) {
            commits = commits.stream()
                             .filter(x -> x.getAuthorEmail() == author_email)
                             .collect(Collectors.toList());
        }
        return commits;
    }

}
