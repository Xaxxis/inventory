package id.xaxxis.inventory.controller.admin;

import id.xaxxis.inventory.entity.master.user.User;
import id.xaxxis.inventory.service.master.location.MasterLocationService;
import id.xaxxis.inventory.service.master.user.RoleService;
import id.xaxxis.inventory.service.master.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/app/user")
public class UserController {
    private final UserService userService;

    private final RoleService roleService;

    private final MasterLocationService masterLocationService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, MasterLocationService masterLocationService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.masterLocationService = masterLocationService;
        this.passwordEncoder = passwordEncoder;
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerUser(Model model) {
        model.addAttribute("activeUser", "active");
        model.addAttribute("openUser", "open");
        model.addAttribute("roleList", roleService.findAll());
        model.addAttribute("locationList", masterLocationService.findAll());
        model.addAttribute("outletList", masterLocationService.findAllOutlet());
        return "admin/user/user-signup";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("user")User user, Model model) {
    //    model.addAttribute("user",user);
        byte[] image = user.getImage();
        user.setImage(image);
        userService.createUser(user);
        return "redirect:/app/user/register";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUser(Model model) {
        model.addAttribute("openUser", "open");
        model.addAttribute("userListActive", "active");
        model.addAttribute("userList", userService.findAll());
        return "admin/user/user-list";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editUser(Model model, @PathVariable String id) {
        User user = userService.findByUserId(id);
        model.addAttribute("openUser", "open");
        model.addAttribute("userListActive", "active");
        model.addAttribute("user", user);
        model.addAttribute("allRole", this.roleService.findAll());
        model.addAttribute("allLocation", this.masterLocationService.findAllLocation());
        return "admin/user/user-update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User newUser) {
        User user = userService.findByUserId(newUser.getUserId());
        user.setRoles(newUser.getRoles());
        user.setMasterLocation(newUser.getMasterLocation());
        user.setOutlet(newUser.getOutlet());
        user.setEnabled(newUser.isEnabled());
        userService.saveUser(user);
        return "redirect:/app/user/list";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profileUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "admin/user/user-profile";
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public String changePassword(Principal principal, @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("password") String password) {
        User user = userService.findByUsername(principal.getName());
        if(userService.checkOldPassword(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(password));
            userService.saveUser(user);
            return "redirect:/logout";
        } else
        return "redirect:/app/user/profile";
    }
}
