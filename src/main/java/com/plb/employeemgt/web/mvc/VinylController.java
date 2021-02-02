package com.plb.employeemgt.web.mvc;

import com.plb.employeemgt.entity.Vinyl;
import com.plb.employeemgt.service.VinylService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/vinyls")
public class VinylController {

    private final VinylService vinylService;

    public VinylController(VinylService vinylService) {
        this.vinylService = vinylService;
    }

    @GetMapping
    public String vinyls(Model model) {
        List<Vinyl> vinyls = vinylService.getVinylsByUser("toto@toto.com");
        model.addAttribute("vinyls", vinyls);
        return "vinyls";
    }
}
