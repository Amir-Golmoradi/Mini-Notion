package dev.amirgol.springtaskbackend.Domain.user.repository;

import dev.amirgol.springtaskbackend.Domain.user.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    List<Token> findByToken(String token);
}
