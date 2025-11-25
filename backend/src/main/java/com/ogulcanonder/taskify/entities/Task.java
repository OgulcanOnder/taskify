package com.ogulcanonder.taskify.entities;

import com.ogulcanonder.taskify.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;

    @Column(name = "task_group", nullable = false)
    private String taskGroup;

    @Column(name = "task_title", nullable = false)
    private String taskTitle;

    @Column(name = "task_detail")
    private String taskDetail;

    @ManyToMany(mappedBy = "tasks",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> userList = new ArrayList<User>();

}
