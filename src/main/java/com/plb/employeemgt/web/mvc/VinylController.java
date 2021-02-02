package com.plb.employeemgt.web.mvc;

import com.plb.employeemgt.entity.Vinyl;
import com.plb.employeemgt.service.UserService;
import com.plb.employeemgt.service.VinylService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/vinyls")
public class VinylController {

    private final VinylService vinylService;
    private final UserService userService;

    public VinylController(VinylService vinylService, UserService userService) {
        this.vinylService = vinylService;
        this.userService = userService;
    }

    @GetMapping
    public String vinyls(Model model) {
        List<Vinyl> vinyls = vinylService.getVinylsByUser("toto@toto.com");
        model.addAttribute("vinyls", vinyls);
        return "vinyls";
    }

    @GetMapping("/new")
    public String initNewViynl(Model model) {
        model.addAttribute("vinyl", new Vinyl());
        model.addAttribute("users", userService.getAll());
        return "new-vinyl";
    }

    @PostMapping
    public ModelAndView createNewVinyl(Vinyl vinylToCreate) {
        vinylService.save(vinylToCreate);
        return new ModelAndView("redirect:/vinyls");
    }
}
