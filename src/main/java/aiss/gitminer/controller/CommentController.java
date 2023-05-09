package aiss.gitminer.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {
    
    @Autowired
    CommentRepository repository;

    // GET Get all comments --> http://localhost:8080/gitminer/comments
    @Operation (
        summary = "Retrieve all comments",
        description = "Get all comments",
        tags = { "comments", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
    })
    @GetMapping
    public List<Comment> findAll() {
        return repository.findAll();
    }

    
    // GET get comments --> http://localhost:8080/gitminer/comments/{id}
    @Operation (
        summary = "Retrieve a comment by id",
        description = "Get a comment object by specifying its id",
        tags = { "comment", "get" }
    )
    @ApiResponses ({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Comment.class), mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/{id}")
    public Comment findOne(@Parameter(description = "id of the comment to be searched") @PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()) {
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

}
