package ir.newims.ims.models;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity extends SimpleEntity {

    private String creationDateTime;
    private String lastModifiedDateTime;

    @Column(name = "c_creationDateTime")
    public String getCreationDateTime() {
        return creationDateTime;
    }

    @Column(name = "c_lastModifiedDateTime")
    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }
}
