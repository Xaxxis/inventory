package id.xaxxis.inventory.controller.purchasing;

import id.xaxxis.inventory.entity.master.suplier.Suplier;
import id.xaxxis.inventory.service.master.item.MasterItemService;
import id.xaxxis.inventory.service.master.location.MasterLocationService;
import id.xaxxis.inventory.service.master.suplier.SuplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/app/purchasing")
public class PurchaseController {

    private final SuplierService suplierService;
    private final MasterLocationService masterLocationService;
    private final MasterItemService masterItemService;

    @Autowired
    public PurchaseController(SuplierService suplierService,
                              MasterLocationService masterLocationService,
                              MasterItemService masterItemService) {
        this.suplierService = suplierService;
        this.masterLocationService = masterLocationService;
        this.masterItemService = masterItemService;
    }

    @RequestMapping(value = "/suplier", method = RequestMethod.GET)
    public String suplier(Model model) {
        model.addAttribute("purchaseOpen", "open");
        model.addAttribute("suplierActive", "active");
        model.addAttribute("suplierList", suplierService.findAll());
        model.addAttribute("typeList", suplierService.findAllSuplierType());
        model.addAttribute("locationList", masterLocationService.findAllLocation());
        return "purchasing/suplier";
    }

    @RequestMapping(value = "/suplier/create", method = RequestMethod.POST)
    public String suplierCreate(@ModelAttribute("suplier")Suplier suplier) {
        suplier.setActive(true);
        suplierService.createSuplier(suplier);
        return "redirect:/app/purchasing/suplier";
    }

    @RequestMapping(value = "/suplier/update", method = RequestMethod.POST)
    public String suplierUpdate(@ModelAttribute("suplierId") String suplierId, @ModelAttribute("suplier") Suplier newSuplier) {
        Suplier suplier = suplierService.findBySuplierId(suplierId);
        suplier.setSuplierName(newSuplier.getSuplierName());
        suplier.setAddress(newSuplier.getAddress());
        suplier.setSuplierPhone(newSuplier.getSuplierPhone());
        suplier.setSuplierPic(newSuplier.getSuplierPic());
        suplier.setSuplierType(newSuplier.getSuplierType());
        suplier.setActive(newSuplier.isActive());
        suplier.setMasterLocation(newSuplier.getMasterLocation());
        suplierService.saveSuplier(suplier);
        return "redirect:/app/purchasing/suplier";
    }

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public String purchaseRequest(Model model) {
        model.addAttribute("purchaseOpen", "open");
        model.addAttribute("requestActive", "active");
        model.addAttribute("itemList", masterItemService.findAll());
        return "purchasing/purchase-request";
    }

}
