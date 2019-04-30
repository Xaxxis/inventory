package id.xaxxis.inventory.entity.master.item;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Table(name = "item_category")
public class CategoryItem {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "cateory_id")
    private String categoryId;

    @Column(name = "category_name", length = 50, unique = true)
    private String categoryName;
}
