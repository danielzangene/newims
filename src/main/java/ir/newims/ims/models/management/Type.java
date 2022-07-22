package ir.newims.ims.models.management;

import ir.newims.ims.models.SimpleEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_Type")
public class Type extends SimpleEntity {
    private String code;
    private List<Element> elements;

    @Column(name = "c_code")
    public String getCode() {
        return code;
    }
    @OneToMany(mappedBy = "type")
    @OrderColumn(name = "i_index", insertable=false, updatable=false, nullable=false)
    public List<Element> getElements() {
        return elements;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
