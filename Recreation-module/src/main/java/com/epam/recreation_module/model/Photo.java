package com.epam.recreation_module.model;

import com.epam.recreation_module.model.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Photo extends AbsEntity {

    /**
     * Name of recreation
     */
    @Column(columnDefinition = "text", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recreation recreation;

    @OneToOne
    private Attachment attachment;
}
