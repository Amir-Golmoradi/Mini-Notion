package dev.amirgol.springtaskbackend.comment.domain.model;

import dev.amirgol.springtaskbackend.task.domain.model.Task;
import dev.amirgol.springtaskbackend.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "task_id", nullable = false)
//    private Task task;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

}
