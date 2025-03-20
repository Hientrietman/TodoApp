package com.prelude.todoapp.dto.req;


import com.prelude.todoapp.model.Enum.Priority;
import com.prelude.todoapp.model.Enum.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TaskRequest {
    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 1000)
    private String description;

    @FutureOrPresent(message = "Due date must be in the future or today")
    private LocalDate dueDate;

    private Priority priority;
    private Status status;
}
