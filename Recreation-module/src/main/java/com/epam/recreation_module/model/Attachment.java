package com.epam.recreation_module.model;

import com.epam.recreation_module.model.template.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "attachment")
public class Attachment extends UserDateAudit {

    /**
     * Uploaded files might be same name and same extension
     * the reason why the file saved with autogenerated name into DB(DataBase)
     */
    @Column(nullable = false)
    private String fileOriginalName;
    /**
     * Uploaded file's size
     */
    @Column(nullable = false)
    private Long size;

    private UUID name;

    /**
     * The extension type of uploaded file
     * for ex: .jpg, .txt, .xlsx, .png, ect.
     */
    @Column(nullable = false)
    private String contentType;

//    @OneToOne(mappedBy = "attachment")
//    private AttachmentContent attachmentContent;

    /**Generating random name to an attachment in order to save to DB*/
    public void setName(String name) {
        this.name = UUID.randomUUID();
    }
}
