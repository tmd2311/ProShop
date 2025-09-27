package com.proshop.product.dto.response;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDeleteResponse {
    private UUID id;
    private String name;
    private String message;

    public CategoryDeleteResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.message = "Đã xóa danh mục: " + name;
    }
}