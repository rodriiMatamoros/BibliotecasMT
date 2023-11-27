package io.bootify.bibliotecas_m_t.controller;

import io.bootify.bibliotecas_m_t.model.BibliotecariosDTO;
import io.bootify.bibliotecas_m_t.service.BibliotecariosService;
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
@RequestMapping("/bibliotecarioss")
public class BibliotecariosController {

    private final BibliotecariosService bibliotecariosService;

    public BibliotecariosController(final BibliotecariosService bibliotecariosService) {
        this.bibliotecariosService = bibliotecariosService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("bibliotecarioses", bibliotecariosService.findAll());
        return "bibliotecarios/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("bibliotecarios") final BibliotecariosDTO bibliotecariosDTO) {
        return "bibliotecarios/add";
    }

    @PostMapping("/add")
    public String add(
            @ModelAttribute("bibliotecarios") @Valid final BibliotecariosDTO bibliotecariosDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasFieldErrors("gmail") && bibliotecariosService.gmailExists(bibliotecariosDTO.getGmail())) {
            bindingResult.rejectValue("gmail", "Exists.bibliotecarios.gmail");
        }
        if (!bindingResult.hasFieldErrors("password") && bibliotecariosService.passwordExists(bibliotecariosDTO.getPassword())) {
            bindingResult.rejectValue("password", "Exists.bibliotecarios.password");
        }
        if (bindingResult.hasErrors()) {
            return "bibliotecarios/add";
        }
        bibliotecariosService.create(bibliotecariosDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bibliotecarios.create.success"));
        return "redirect:/bibliotecarioss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id, final Model model) {
        model.addAttribute("bibliotecarios", bibliotecariosService.get(id));
        return "bibliotecarios/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") final Long id,
            @ModelAttribute("bibliotecarios") @Valid final BibliotecariosDTO bibliotecariosDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        final BibliotecariosDTO currentBibliotecariosDTO = bibliotecariosService.get(id);
        if (!bindingResult.hasFieldErrors("gmail") &&
                !bibliotecariosDTO.getGmail().equalsIgnoreCase(currentBibliotecariosDTO.getGmail()) &&
                bibliotecariosService.gmailExists(bibliotecariosDTO.getGmail())) {
            bindingResult.rejectValue("gmail", "Exists.bibliotecarios.gmail");
        }
        if (!bindingResult.hasFieldErrors("password") &&
                !bibliotecariosDTO.getPassword().equalsIgnoreCase(currentBibliotecariosDTO.getPassword()) &&
                bibliotecariosService.passwordExists(bibliotecariosDTO.getPassword())) {
            bindingResult.rejectValue("password", "Exists.bibliotecarios.password");
        }
        if (bindingResult.hasErrors()) {
            return "bibliotecarios/edit";
        }
        bibliotecariosService.update(id, bibliotecariosDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("bibliotecarios.update.success"));
        return "redirect:/bibliotecarioss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") final Long id,
            final RedirectAttributes redirectAttributes) {
        final String referencedWarning = bibliotecariosService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            bibliotecariosService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("bibliotecarios.delete.success"));
        }
        return "redirect:/bibliotecarioss";
    }

}
