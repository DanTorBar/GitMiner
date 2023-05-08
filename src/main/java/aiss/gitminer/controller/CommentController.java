package aiss.gitminer.controller;

// import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aiss.gitminer.model.Comment;
import aiss.gitminer.repository.CommentRepository;

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {
    
    @Autowired
    CommentRepository repository;

    // GET Get all comments --> http://localhost:8080/gitminer/comments
    @GetMapping
    public List<Comment> findAll() {
        return repository.findAll();
    }

    // GET get comments --> http://localhost:8080/gitminer/comments/{id}
    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id) {
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }

}
