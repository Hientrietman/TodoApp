package com.prelude.todoapp.controller;

import com.prelude.todoapp.dto.req.TaskRequest;
import com.prelude.todoapp.dto.res.ApiResponse;
import com.prelude.todoapp.model.Task;
import com.prelude.todoapp.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;


    // Lấy task theo ID
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Task retrieved successfully", task));
    }
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Task>>> searchTasks(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Task> result = taskService.searchTasks(query, page, size);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Tasks retrieved successfully", result));
    }

    // Tạo task mới
    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(@Valid @RequestBody TaskRequest request) {
        Task task = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, true, "Task created successfully", task));
    }

    // Cập nhật task
    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Task>> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequest request) {

        Task updatedTask = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Task updated successfully", updatedTask));
    }

    // Xóa task
    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Task deleted successfully", null));
    }

    // Gắn liên kết giữa 2 task
    @PostMapping("/{taskId}/dependencies/{dependsOnId}")
    public ResponseEntity<ApiResponse<Task>> addDependency(
            @PathVariable Long taskId,
            @PathVariable Long dependsOnId) {

        Task updatedTask = taskService.addDependency(taskId, dependsOnId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Dependency added successfully", updatedTask));
    }

    // Lấy danh sách tất cả dependencies của một task
    @GetMapping("/{taskId}/dependencies")
    public ResponseEntity<ApiResponse<Set<Task>>> getAllRelatedTasks(@PathVariable Long taskId) {
        Set<Task> relatedTasks = taskService.getAllDependencies(taskId);
        return ResponseEntity.ok(new ApiResponse<>(200, true, "Task dependencies retrieved successfully", relatedTasks));
    }
}
