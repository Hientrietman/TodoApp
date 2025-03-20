package com.prelude.todoapp.service;

import com.prelude.todoapp.model.Task;
import com.prelude.todoapp.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DueDateCheckerService {

    private final TaskRepository taskRepository;
    private final MailService mailService;

    @Scheduled(fixedRate = 600000) // Chạy mỗi 1 phút
    public void checkDueDates() {
        List<Task> tasks = taskRepository.findByDueDate(LocalDate.now().plusDays(1)); // Task hết hạn vào ngày mai

        if (tasks.isEmpty()) {
            log.info("✅ Không có task nào sắp đến hạn.");
            return;
        }

        for (Task task : tasks) {
            mailService.sendDueDateReminder("dopax67406@erapk.com", task.getTitle(), task.getDueDate().toString());
        }
    }
}
