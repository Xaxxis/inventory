package id.xaxxis.inventory.controller.purchasing;

import id.xaxxis.inventory.dao.purchasing.PurchaseRequestDao;
import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.master.suplier.Suplier;
import id.xaxxis.inventory.entity.purchasing.PurchaseRequest;
import id.xaxxis.inventory.entity.purchasing.PurchaseRequestItem;
import id.xaxxis.inventory.service.master.item.MasterItemService;
import id.xaxxis.inventory.service.master.location.MasterLocationService;
import id.xaxxis.inventory.service.master.suplier.SuplierService;
import id.xaxxis.inventory.service.purchasing.pr.PurchaseRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/app/purchasing")
public class PurchaseController {

    private final SuplierService suplierService;
    private final MasterLocationService masterLocationService;
    private final MasterItemService masterItemService;
    private final PurchaseRequestService purchaseRequestService;
    private final PurchaseRequestDao purchaseRequestDao;
    @Autowired
    public PurchaseController(SuplierService suplierService,
                              MasterLocationService masterLocationService,
                              MasterItemService masterItemService,
                              PurchaseRequestService purchaseRequestService,
                              PurchaseRequestDao purchaseRequestDao) {
        this.suplierService = suplierService;
        this.masterLocationService = masterLocationService;
        this.masterItemService = masterItemService;
        this.purchaseRequestService = purchaseRequestService;
        this.purchaseRequestDao = purchaseRequestDao;
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
    public ModelAndView purchaseRequest() {
        ModelAndView mv = new ModelAndView("purchasing/purchase-request");
        mv.addObject("purchaseOpen", "open");
        mv.addObject("requestActive", "active");
        mv.addObject("itemList", masterItemService.findAll());
        mv.addObject("itemCart", purchaseRequestService.getItemInCart());
        return mv;
    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    public ModelAndView invoices(@RequestParam List<MasterItem> itemIds) {

        return purchaseRequest();
    }

    @GetMapping(value = "/request/addItem/{itemId}")
    public ModelAndView addItem(@PathVariable("itemId") String itemId,
                                Optional<MasterItem> masterItem) {
        masterItemService.findByItemId(itemId).ifPresent(purchaseRequestService::addItem);
        return purchaseRequest();
    }

    @RequestMapping(value = "/request/updateItem/{itemId}", method = RequestMethod.GET)
    public ModelAndView updateItem(@PathVariable("itemId") String itemId,
                                   HttpSession session){
        int quantity = 6;
        MasterItem masterItem = masterItemService.findByItemBarcode(itemId);
        purchaseRequestService.updateItem(masterItem,quantity);


        return purchaseRequest();
    }

    @GetMapping(value = "/request/removeItem/{itemId}")
    public ModelAndView removeItem(@PathVariable("itemId") String itemId) {
        masterItemService.findByItemId(itemId).ifPresent(purchaseRequestService::removeItem);
        return purchaseRequest();
    }

    @RequestMapping(value = "/request/submit", method = RequestMethod.POST)
    public String createPr(PurchaseRequest purchaseRequest, PurchaseRequestItem purchaseRequestItem, Model model) {
        model.addAttribute("purchaseRequest", purchaseRequest);
        model.addAttribute("purchaseRequestItem", purchaseRequestItem);
        purchaseRequestService.createPurchaseRequest(purchaseRequest, purchaseRequestItem);
        return "redirect:/app/purchasing/request";
    }
}
