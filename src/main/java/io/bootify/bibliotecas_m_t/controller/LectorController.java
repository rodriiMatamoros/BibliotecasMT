package io.bootify.bibliotecas_m_t.controller;

import io.bootify.bibliotecas_m_t.model.LectorDTO;
import io.bootify.bibliotecas_m_t.service.LectorService;
import io.bootify.bibliotecas_m_t.util.WebUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/lectors")
public class LectorController {

    private final LectorService lectorService;

    public LectorController(final LectorService lectorService) {
        this.lectorService = lectorService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("lectors", lectorService.findAll());
        return "lector/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("lector") final LectorDTO lectorDTO) {
        return "lector/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("lector") @Valid final LectorDTO lectorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("correo") && lectorService.correoExists(lectorDTO.getCorreo())) {
            bindingResult.rejectValue("correo", "Exists.lector.correo");
        }
        if (!bindingResult.hasFieldErrors("libro") && lectorDTO.getLibro() != null && lectorService.libroExists(lectorDTO.getLibro())) {
            bindingResult.rejectValue("libro", "Exists.lector.libro");
        }
        if (bindingResult.hasErrors()) {
            return "lector/add";
        }
        lectorService.create(lectorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lector.create.success"));
        return "redirect:/lectors";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("lector", lectorService.get(id));
        return "lector/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("lector") @Valid final LectorDTO lectorDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final LectorDTO currentLectorDTO = lectorService.get(id);
        if (!bindingResult.hasFieldErrors("correo") &&
                !lectorDTO.getCorreo().equalsIgnoreCase(currentLectorDTO.getCorreo()) &&
                lectorService.correoExists(lectorDTO.getCorreo())) {
            bindingResult.rejectValue("correo", "Exists.lector.correo");
        }
        if (!bindingResult.hasFieldErrors("libro") && lectorDTO.getLibro() != null &&
                !lectorDTO.getLibro().equalsIgnoreCase(currentLectorDTO.getLibro()) &&
                lectorService.libroExists(lectorDTO.getLibro())) {
            bindingResult.rejectValue("libro", "Exists.lector.libro");
        }
        if (bindingResult.hasErrors()) {
            return "lector/edit";
        }
        lectorService.update(id, lectorDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("lector.update.success"));
        return "redirect:/lectors";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = lectorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            lectorService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("lector.delete.success"));
        }
        return "redirect:/lectors";
    }

}
