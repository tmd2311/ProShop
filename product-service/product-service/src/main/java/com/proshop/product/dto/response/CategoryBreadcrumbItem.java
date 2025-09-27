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
public class CategoryBreadcrumbItem {
    private UUID id;
    private String name;
    private String slug;
    private Integer level;
    private Boolean isLast; // True if this is the current/last item in breadcrumb
    private String url; // Full URL path for this category
}