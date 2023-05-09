package aiss.gitminer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
    Page<Comment> findById(String id, Pageable pageable);
    Page<Comment> findByAuthor(User author, Pageable pageable);
}
