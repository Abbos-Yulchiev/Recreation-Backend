package com.epam.recreation_module.model;

import com.epam.recreation_module.model.template.AbsEntity;
import com.epam.recreation_module.model.template.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Commentary extends AbsEntity {

    @Column(columnDefinition = "text")
    private String commentText;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recreation recreation;

    private LocalDateTime writtenTime;

    private boolean deleted = false;

    private String writer;

    private Long writerId;
}
