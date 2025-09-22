package com.proshop.product.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Data;

@Data
@Valid
public class CategoryCreateRequest {
    @NotBlank
    private String name;
    @NotBlank private String slug;
    private UUID parentId;
}
