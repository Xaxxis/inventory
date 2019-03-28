package id.xaxxis.inventory.dao.master.item;

import id.xaxxis.inventory.entity.master.item.CategoryItem;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemDao extends PagingAndSortingRepository<CategoryItem, String> {
    CategoryItem findByCategoryId(String categoryId);
    CategoryItem findByCategoryName(String categoryName);
    List<CategoryItem> findAll();
}
