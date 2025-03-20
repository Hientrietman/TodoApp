package com.prelude.todoapp.specification;
import com.prelude.todoapp.model.Task;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {

    public static Specification<Task> buildTaskSpecification(String query) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (query == null || query.isBlank()) {
                return criteriaBuilder.conjunction(); // Nếu query rỗng, trả về tất cả
            }

            List<Predicate> predicates = new ArrayList<>();

            // Tìm theo title
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + query.toLowerCase() + "%"));

            // Tìm theo description
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + query.toLowerCase() + "%"));

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
