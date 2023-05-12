package aiss.gitminer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.exception.CommitNotFoundException;
import aiss.gitminer.model.Commit;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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

    // GET Get all commits --> http://localhost:8080/gitminer/commits?email={emailAddress}
    @Operation (
        summary = "Retrieve all commits",
        description = "Get all commits",
        tags = { "commits", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Commit.class), mediaType = "application/json")}),
    })

    // GET get commit by email address --> http://localhost:8080/gitminer/commits?email={emailAddress}
    @GetMapping
    public List<Commit> findAll(@RequestParam(required = false) String email,
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

        Page<Commit> pageProjects;

        if(email == null) {
            pageProjects = commitRepository.findAll(paging);
        } else {
            pageProjects = commitRepository.findByAuthorEmail(email, paging);
        }

        return pageProjects.getContent();
    }
    
    // GET get commit --> http://localhost:8080/gitminer/commits/{id}
    @Operation (
        summary = "Retrieve a commit by id",
        description = "Get a commit object by specifying its id",
        tags = { "commits", "get" }
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

}
