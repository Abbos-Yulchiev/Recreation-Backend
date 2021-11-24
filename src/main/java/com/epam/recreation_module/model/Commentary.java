package com.epam.recreation_module.model;

import com.epam.recreation_module.model.template.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Commentary extends UserDateAudit {

    @Column(columnDefinition = "text")
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recreation recreation;

    private boolean deleted = false;
}
