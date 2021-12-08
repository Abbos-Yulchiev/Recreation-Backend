package com.epam.recreation_module.model;

import com.epam.recreation_module.model.template.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AttachmentContent extends UserDateAudit {

    private byte[] bytes;

    @OneToOne
    private Attachment attachment;
}
