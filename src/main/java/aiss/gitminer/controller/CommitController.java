package aiss.gitminer.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.ProjectRepository;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {
    
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CommitRepository commitRepository;

    // GET get commit --> http://localhost:8080/gitminer/commits/{id}
    @GetMapping("/{id}")
    public Commit findOne(@PathVariable String id) {
        Optional<Commit> commit = commitRepository.findById(id);
        return commit.get();
    }

    // GET get commit by email address --> http://localhost:8080/gitminer/commits?email={emailAddress}
    @GetMapping
    public List<Commit> findCommits(@RequestParam(required = false) String email) {
        List<Commit> commits = commitRepository.findAll();
        if (email != null) {
            commits = commits.stream()
                                .filter(c -> c.getAuthorEmail().equals(email))
                                .collect(Collectors.toList());
        }
        return commits;
    }

}