package bg.softuni.mobilele.model.entity;

import bg.softuni.mobilele.model.entity.enums.CategoryEnum;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "models")
public class Model extends Base {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    @Column(nullable = false)
    private String imageUrl;
    private int startYear;
    private Long endYear;
    @ManyToOne
    private Brand brand;


}
