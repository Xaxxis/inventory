package id.xaxxis.inventory.controller.inventory;

import id.xaxxis.inventory.entity.master.item.CategoryItem;
import id.xaxxis.inventory.entity.master.item.UnitItem;
import id.xaxxis.inventory.service.master.item.MasterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/app/inv")
public class CategoryController {
    private final MasterItemService masterItemService;

    @Autowired
    public CategoryController (MasterItemService masterItemService) {
        this.masterItemService = masterItemService;
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String categoryItem(Model model) {
        model.addAttribute("invOpen", "open");
        model.addAttribute("iukOpen", "open");
        model.addAttribute("categoryActive", "active");
        model.addAttribute("categoryList", masterItemService.findAllCategory());
        return "inventory/item-category";
    }

    @RequestMapping(value = "/category/create", method = RequestMethod.POST)
    public String cerateCategory(@ModelAttribute("categoryItem") CategoryItem categoryItem, Model model){
        model.addAttribute("categoryItem", categoryItem);
        masterItemService.saveCategory(categoryItem);
        return "inventory/item-category";
    }

    @RequestMapping(value = "/category/update", method = RequestMethod.POST)
    public String updateCategory(@ModelAttribute("categoryId")String categoryId,
                                 @ModelAttribute("categoryName")String categoryName) {
        CategoryItem categoryItem = masterItemService.findBYCategoryId(categoryId);
        categoryItem.setCategoryName(categoryName);
        masterItemService.saveCategory(categoryItem);
        return "redirect:/app/inv/category";
    }

    @RequestMapping(value = "/unit", method = RequestMethod.GET)
    public String unitItem(Model model) {
        model.addAttribute("invOpen", "open");
        model.addAttribute("iukOpen", "open");
        model.addAttribute("unitActive", "active");
        model.addAttribute("unitList", masterItemService.findAllUnit());
        return "inventory/item-unit";
    }

    @RequestMapping(value = "/unit/create", method = RequestMethod.POST)
    public String createUnit(@ModelAttribute("unitItem") UnitItem unitItem, Model model) {
        model.addAttribute("unitItem", unitItem);
        masterItemService.saveUnit(unitItem);
        return "redirect:/app/inv/unit";
    }

    @RequestMapping(value = "/unit/update", method = RequestMethod.POST)
    public String updateUnit(@ModelAttribute("unitId") String unitId,
                             @ModelAttribute("unitName") String unitName,
                             @ModelAttribute("unitDescription") String unitDescription) {
        UnitItem unitItem = masterItemService.findByUnitId(unitId);
        unitItem.setUnitName(unitName);
        unitItem.setUnitDescription(unitDescription);
        masterItemService.saveUnit(unitItem);
        return "redirect:/app/inv/unit";
    }

}
