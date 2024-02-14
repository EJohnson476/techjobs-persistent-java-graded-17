package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("skill")
public class SkillController {
    @Autowired
    SkillRepository skillRepository;

    @GetMapping("skill")
    public String index (Model model)
    {
        model.addAttribute("jobs", skillRepository.findAll() );
        return "skill/index";
    }

    @GetMapping("add")
    public String displayAddEmployerForm(Model model) {
        model.addAttribute(new Skill());
        return "skill/add";
    }

    @PostMapping("add")
    public String processAddEmployerForm(@ModelAttribute @Valid Skill newSkill,
                                         Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "skill/add";
        }
                skillRepository.save(newSkill);
                return "redirect:view";
    }

    @GetMapping("view/{employerId}")
    public String displayViewEmployer(Model model, @PathVariable int skillId) {

        Optional optSkill = skillRepository.findById(skillId);
        if (optSkill.isPresent()) {
            Skill skl  = (Skill) optSkill.get();
            model.addAttribute("skill", skl);
            return "skill/view";
        } else {
            return "redirect:../";
        }

    }
}
