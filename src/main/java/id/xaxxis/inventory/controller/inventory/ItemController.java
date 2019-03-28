package id.xaxxis.inventory.controller.inventory;

import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.service.master.item.MasterItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/app/inv/item")
public class ItemController {

    private final MasterItemService masterItemService;

    @Autowired
    public ItemController(MasterItemService masterItemService) {
        this.masterItemService = masterItemService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String item(@RequestParam(value = "category", required = false) String category, Model model) {
        model.addAttribute("invOpen", "open");
        model.addAttribute("iukOpen", "open");
        model.addAttribute("itemActive", "active");
        model.addAttribute("category", this.masterItemService.findAllCategory());
        model.addAttribute("unit", this.masterItemService.findAllUnit());
        if(category != null){
            model.addAttribute("itemList", masterItemService.findAllByItemCategory(category));
            return "inventory/item";
        }
        model.addAttribute("itemList", masterItemService.findAll());
        return "inventory/item";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String item(@ModelAttribute("item")MasterItem masterItem, Model model) {
        model.addAttribute("item", masterItem);
        masterItemService.saveItem(masterItem);
        return "redirect:/app/inv/item/";
    }

}
