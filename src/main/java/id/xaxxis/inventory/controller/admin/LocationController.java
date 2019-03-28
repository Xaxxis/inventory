package id.xaxxis.inventory.controller.admin;

import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.location.Outlet;
import id.xaxxis.inventory.service.master.location.MasterLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/app/location")
public class LocationController {

    private final MasterLocationService masterLocationService;

    @Autowired
    public LocationController(MasterLocationService masterLocationService){
        this.masterLocationService = masterLocationService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String location(Model model) {
        model.addAttribute("openUser", "open");
        model.addAttribute("locationActive", "active");
        model.addAttribute("locationList", masterLocationService.findAll());
        return "admin/location/location-create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createLocation(@ModelAttribute("masterLocation") MasterLocation masterLocation, Model model) {
        model.addAttribute("openUser", "open");
        model.addAttribute("locationActive", "active");
        model.addAttribute("masterLocation", masterLocation);
        masterLocationService.createLocation(masterLocation);
        return "redirect:/app/location/";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateLocation(@RequestParam("locationId")String locationId,
                                 @RequestParam("locationName")String locationName,
                                 @RequestParam("address")String address) {
        MasterLocation location = masterLocationService.findByLocationId(locationId);
        location.setLocationName(locationName);
        location.setAddress(address);
        masterLocationService.save(location);
        return "redirect:/app/location/";
    }

    @RequestMapping(value = "/outlet", method = RequestMethod.GET)
    public String outlet(Model model) {
        model.addAttribute("openUser", "open");
        model.addAttribute("outletActive", "active");
        model.addAttribute("locationList", masterLocationService.findAll());
        model.addAttribute("outletList", masterLocationService.findAllOutlet());
        return "admin/location/outlet";
    }

    @RequestMapping(value = "/outlet/create", method = RequestMethod.POST)
    public String outlateCreate(@ModelAttribute("outlet") Outlet outlet, Model model) {
        model.addAttribute("outlet", outlet);
        masterLocationService.saveOutlet(outlet);
        return "redirect:/app/location/outlet";
    }

    @RequestMapping(value = "/outlet/update", method = RequestMethod.POST)
    public String outletUpdate(@RequestParam("outletId") String outletId,
                               @RequestParam("outletName") String outletName,
                               @RequestParam("masterLocation") MasterLocation locationId) {
        Outlet outlet = masterLocationService.findByOutletId(outletId);
        outlet.setOutletName(outletName);
        outlet.setMasterLocation(locationId);
        masterLocationService.saveOutlet(outlet);
        return "redirect:/app/location/outlet";
    }

}
