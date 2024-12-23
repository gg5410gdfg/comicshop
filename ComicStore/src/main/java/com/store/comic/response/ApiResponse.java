package com.store.comic.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
	private Integer code; // HTTP 狀態碼或自定義代碼
    private String message; // 錯誤描述
    private T data; // 成功時返回的數據，失敗時為 null
    private String errorDetail; // 錯誤詳細訊息（新增）

    // 成功響應
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data, null);
    }

    // 失敗響應
    public static <T> ApiResponse<T> fail(Integer code, String message, String errorDetail) {
        return new ApiResponse<>(code, message, null, errorDetail);
    }
}
