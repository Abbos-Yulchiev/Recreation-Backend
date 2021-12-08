package com.epam.recreation_module.model.DTO;

import com.epam.recreation_module.model.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {

    private Long recreationId;

    private Attachment attachment;
}
