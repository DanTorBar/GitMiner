package aiss.gitminer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aiss.gitminer.model.Commit;

@Repository
public interface CommitRepository extends JpaRepository<Commit,String> {
    Page<Commit> findByAuthorEmail(String authorEmail, Pageable pageable);
}
