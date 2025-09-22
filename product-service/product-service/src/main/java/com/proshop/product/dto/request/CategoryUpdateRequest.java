package com.proshop.product.dto.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {
    private String name;
    private String slug;
    private UUID parentId;
    private boolean removeParent; // để remove parent category
}
