package onlineShop.controller;

import onlineShop.constants.Pages;
import onlineShop.constants.PathConstants;
import onlineShop.dto.request.SearchRequest;
import onlineShop.service.PerfumeService;
import onlineShop.utils.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.PERFUME)
public class PerfumeController {

    private final PerfumeService perfumeService;
    private final ControllerUtils controllerUtils;

    @GetMapping("/{perfumeId}")
    public String getPerfumeById(@PathVariable Long perfumeId, Model model) {
        model.addAttribute("perfume", perfumeService.getPerfumeById(perfumeId));
        return Pages.PERFUME;
    }

    @GetMapping
    public String getPerfumesByFilterParams(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.getPerfumesByFilterParams(request, pageable));
        return Pages.PERFUMES;
    }

    @GetMapping("/search")
    public String searchPerfumes(SearchRequest request, Model model, Pageable pageable) {
        controllerUtils.addPagination(request, model, perfumeService.searchPerfumes(request, pageable));
        return Pages.PERFUMES;
    }
}
