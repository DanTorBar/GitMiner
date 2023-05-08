package aiss.gitminer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aiss.gitminer.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,String> {
}
