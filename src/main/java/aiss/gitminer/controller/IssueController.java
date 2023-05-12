package aiss.gitminer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.exception.IssueNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.ProjectRepository;
import aiss.gitminer.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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
    @Operation (
        summary = "Retrieve all issues",
        description = "Get all issues",
        tags = { "issues", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
    })
    @GetMapping
    public List<Issue> findAll(  @RequestParam(required = false) String authorId,
                                 @RequestParam(required = false) String state,
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

        Page<Issue> pageIssuesS;
        Page<Issue> pageIssuesA;

        if(state == null) {
            pageIssuesS = issueRepository.findAll(paging);            
        } else {
            pageIssuesS = issueRepository.findByState(state, paging);
        }
        if(authorId == null) {
            pageIssuesA = issueRepository.findAll(paging);            
        } else {
            pageIssuesA = issueRepository.findByAuthor(userRepository.findById(authorId).get(), paging);
        }

        return pageIssuesS.getContent().stream().filter(i -> pageIssuesA.getContent().contains(i)).collect(Collectors.toList());

    }
    
    // GET Get issues by ID --> http://localhost:8080/gitminer/issues/{id}
    @Operation (
        summary = "Retrieve a issue by id",
        description = "Get a issue object by specifying its id",
        tags = { "issues", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Issue findOne(@Parameter(description = "id of the issue to be searched") @PathVariable String id) throws IssueNotFoundException {
        Optional<Issue> issue = issueRepository.findById(id);
        if(!issue.isPresent()) {
            throw new IssueNotFoundException();
        }
        return issue.get();
    }


    // GET Get issue's comments --> http://localhost:8080/gitminer/issues/{id}/comments
    @Operation (
        summary = "Retrieve all comments from an issue",
        description = "Get all comments from a specifying issue by its id",
        tags = { "comments", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Issue.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsFromIssue(@Parameter(description = "id of the issue which comments are going to be searched") @PathVariable (value = "id") String id) throws IssueNotFoundException {
       Optional<Issue> issue = issueRepository.findById(id);
       if(!issue.isPresent()) {
            throw new IssueNotFoundException();
       }
       List<Comment> comments = new ArrayList<>(issue.get().getComments());
       return comments;
    }

}
