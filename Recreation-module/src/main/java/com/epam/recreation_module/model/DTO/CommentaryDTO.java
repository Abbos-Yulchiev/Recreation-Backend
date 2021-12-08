package com.epam.recreation_module.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentaryDTO {

    private String commentText;

    private Long recreationId;
}
