package dev.amirgol.springtaskbackend.user.domain.repository;

import dev.amirgol.springtaskbackend.user.domain.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByToken(String token);
}
