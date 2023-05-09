package aiss.gitminer.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.message.callback.PrivateKeyCallback.IssuerSerialNumRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.repository.UserRepository;

@RestController
@RequestMapping("/gitminer/issues")
public class IssueController {
    
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    IssueRepository issueRepository;

    // GET Get all issues --> http://localhost:8080/gitminer/issues
    // @GetMapping
    // public List<Issue> findAll() {
    //     return issueRepository.findAll();
    // }

    // GET Get issues by ID --> http://localhost:8080/gitminer/issues/{id}
    @GetMapping("/{id}")
    public Issue findOne(@PathVariable String id) {
        Optional<Issue> issues = issueRepository.findById(id);
        return issues.get();
    }

    // GET Get issue's comments --> http://localhost:8080/gitminer/issues/{id}/comments
    @GetMapping("{id}/comments")
    public List<Comment> getCommentsFromIssue(@PathVariable String id) {
        Issue issue = findOne(id);
        return issue.getComments();
    }

    // GET Get issues by author id --> http://localhost:8080/gitminer/issues?authorId={id}
    @GetMapping
    public List<Issue> findIssuesByAuthor(@RequestParam(required = false) String authorId,
                                            @RequestParam(required = false) String state) {
        List<Issue> issues = issueRepository.findAll();
        if (authorId != null) {
            issues = issues.stream()
                             .filter(i -> i.getAuthor().getId().equals(authorId))
                             .collect(Collectors.toList());
        } 
        if (state != null) {
            issues = issues.stream()
                             .filter(x -> x.getState().equals(state))
                             .collect(Collectors.toList());
        }            
        return issues;
    }
}
