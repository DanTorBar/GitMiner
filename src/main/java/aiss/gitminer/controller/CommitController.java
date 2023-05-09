package aiss.gitminer.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.exception.CommitNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/gitminer/commits")
public class CommitController {
    
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CommitRepository commitRepository;

    // GET Get all commits --> http://localhost:8080/gitminer/commits
    @Operation (
        summary = "Retrieve all commits",
        description = "Get all commits",
        tags = { "commits", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
    })
    @GetMapping
    public List<Commit> findAll() {
        return commitRepository.findAll();
    }


    // GET get commit --> http://localhost:8080/gitminer/commits/{id}
    @Operation (
        summary = "Retrieve a commit by id",
        description = "Get a commit object by specifying its id",
        tags = { "commit", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Commit findOne(@Parameter(description = "id of the commit to be searched") @PathVariable String id) throws CommitNotFoundException {
        Optional<Commit> commit = commitRepository.findById(id);
        if(!commit.isPresent()) {
            throw new CommitNotFoundException();
        }
        return commit.get();
    }

    
    // GET get commit by email address --> http://localhost:8080/gitminer/commits?email={emailAddress}
    @Operation (
        summary = "Retrieve all commits made by an email",
        description = "Get all commits which are made by a specifying email address",
        tags = { "commits", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/commits")
    public List<Commit> findCommits(@Parameter(description = "email address of the commits which are going to be searched") @RequestParam(value="author_email", required = false) String author_email) {
        List<Commit> commits = findAll();
        if (author_email != null) {
            commits = commits.stream()
                             .filter(x -> x.getAuthorEmail() == author_email)
                             .collect(Collectors.toList());
        }
        return commits;
    }

}
