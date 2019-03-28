package id.xaxxis.inventory.controller.inventory;

import id.xaxxis.inventory.entity.inventory.Inventory;
import id.xaxxis.inventory.entity.master.item.CategoryItem;
import id.xaxxis.inventory.service.inventory.InventoryService;
import id.xaxxis.inventory.service.master.item.MasterItemService;
import id.xaxxis.inventory.service.master.location.MasterLocationService;
import id.xaxxis.inventory.service.master.suplier.SuplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/app/inv")
public class InventoryController {

    private final InventoryService inventoryService;

    private final MasterItemService masterItemService;

    private final MasterLocationService masterLocationService;

    private final SuplierService suplierService;

    @Autowired
    public InventoryController(InventoryService inventoryService,
                               MasterItemService masterItemService,
                               MasterLocationService masterLocationService,
                               SuplierService suplierService) {
        this.inventoryService = inventoryService;
        this.masterItemService = masterItemService;
        this.masterLocationService = masterLocationService;
        this.suplierService = suplierService;
    }

    @RequestMapping(value = "/warehouse", method = RequestMethod.GET)
    public String warehouse(@RequestParam(value = "by", required = false) String by, Model model) {
        model.addAttribute("whActive", "active");
        model.addAttribute("invOpen", "open");
        model.addAttribute("item", this.masterItemService.findAll());
        model.addAttribute("location", this.masterLocationService.findAll());
        model.addAttribute("outlet", this.masterLocationService.findAllOutletByLoc());
        model.addAttribute("suplierList", suplierService.findAll());
        if((null) != by){
            CategoryItem category = masterItemService.findByCategoryName(by);
            String categoryId = category.getCategoryId();
            model.addAttribute("inventoryList", this.inventoryService.findAllByItemCategoryAndLocation(categoryId));
            return "/inventory/warehouse";
        }
        model.addAttribute("inventoryList", this.inventoryService.findAll());
        return "/inventory/warehouse";
    }

    @RequestMapping(value = "/warehouse/create", method = RequestMethod.POST)
    public String whCreate(@ModelAttribute("inventory")Inventory inventory, Model model) {
        model.addAttribute("inventory", inventory);
        inventory.setStock(0);
        inventoryService.saveInv(inventory);
        return "redirect:/app/inv/warehouse";
    }

}
