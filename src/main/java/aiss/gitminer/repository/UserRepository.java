package aiss.gitminer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aiss.gitminer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
