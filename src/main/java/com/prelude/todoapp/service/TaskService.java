package com.prelude.todoapp.service;

import com.prelude.todoapp.dto.req.TaskRequest;
import com.prelude.todoapp.exception.ResourceNotFound;
import com.prelude.todoapp.model.Enum.Status;
import com.prelude.todoapp.model.Task;

import com.prelude.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.prelude.todoapp.specification.TaskSpecification.buildTaskSpecification;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    // 1️⃣ Tạo task mới
    public Task createTask(TaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setPriority(request.getPriority());
        task.setStatus(request.getStatus() != null ? request.getStatus() : Status.TODO);

        return taskRepository.save(task);
    }

    // 2️⃣ Cập nhật task
    public Task updateTask(Long taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFound("Task not found", HttpStatus.NOT_FOUND));

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getStatus() != null) task.setStatus(request.getStatus());

        task.setUpdatedAt(LocalDate.now());
        return taskRepository.save(task);
    }


    // 3️⃣ Xóa task
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFound("Task not found", HttpStatus.NOT_FOUND));

        taskRepository.delete(task);
    }


    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFound("Task not found", HttpStatus.NOT_FOUND));
    }

    public Page<Task> searchTasks(String query, int page, int size) {
        Specification<Task> spec = buildTaskSpecification(query);
        return taskRepository.findAll(spec, PageRequest.of(page, size));
    }
    // Gắn liên kết giữa 2 task (Kiểm tra chu trình trước)
    public Task addDependency(Long taskId, Long dependsOnId) {
        if (taskId.equals(dependsOnId)) {
            throw new IllegalArgumentException("A task cannot depend on itself");
        }

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFound("Task not found", HttpStatus.NOT_FOUND));

        Task dependsOnTask = taskRepository.findById(dependsOnId)
                .orElseThrow(() -> new ResourceNotFound("Dependency task not found", HttpStatus.NOT_FOUND));

        //  Kiểm tra chu trình trước khi thêm liên kết
        if (hasCircularDependency(dependsOnTask, task)) {
            throw new IllegalArgumentException("Circular dependency detected! Task " + taskId + " already depends on " + dependsOnId);
        }

        task.getDependencies().add(dependsOnTask);
        return taskRepository.save(task);
    }

    // Kiểm tra chu trình (Đệ quy kiểm tra xem `task` có phụ thuộc `target` không)
    private boolean hasCircularDependency(Task task, Task target) {
        if (task.getDependencies().contains(target)) {
            return true; // Nếu target đã có trong dependencies thì có chu trình
        }

        for (Task dependency : task.getDependencies()) {
            if (hasCircularDependency(dependency, target)) {
                return true; // Nếu tìm thấy target ở cấp sâu hơn, cũng là chu trình
            }
        }

        return false;
    }
    // Lấy tất cả dependencies (trực tiếp và gián tiếp)
    public Set<Task> getAllDependencies(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFound("Task not found", HttpStatus.NOT_FOUND));

        Set<Task> allDependencies = new HashSet<>();
        fetchDependencies(task, allDependencies);
        return allDependencies;
    }

    // Đệ quy lấy tất cả dependencies
    private void fetchDependencies(Task task, Set<Task> allDependencies) {
        for (Task dependency : task.getDependencies()) {
            if (allDependencies.add(dependency)) {
                fetchDependencies(dependency, allDependencies);
            }
        }
    }
}


