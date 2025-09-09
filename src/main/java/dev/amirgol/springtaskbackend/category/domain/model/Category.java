package dev.amirgol.springtaskbackend.category.domain.model;

import dev.amirgol.springtaskbackend.task.domain.model.Task;
import dev.amirgol.springtaskbackend.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(nullable = false, length = 50)
    private String name;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;

//    @ManyToMany(mappedBy = "categories")
//    private List<Task> tasks = new ArrayList<>();

}