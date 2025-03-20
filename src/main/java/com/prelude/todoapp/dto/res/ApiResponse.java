package com.prelude.todoapp.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;      // HTTP Status Code
    private boolean success; // Thành công hay thất bại
    private String status; // Mô tả trạng thái (SUCCESS, ERROR)
    private T data;        // Dữ liệu trả về
}
