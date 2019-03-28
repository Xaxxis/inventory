package id.xaxxis.inventory.entity.master.item;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "categoryItem")
    private Collection<MasterItem> items = new ArrayList<>();
}
