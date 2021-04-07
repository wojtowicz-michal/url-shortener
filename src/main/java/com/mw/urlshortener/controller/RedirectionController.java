package com.mw.urlshortener.controller;

import com.mw.urlshortener.dto.RedirectionDto;
import com.mw.urlshortener.service.RedirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static com.mw.urlshortener.util.Constants.*;

@Controller
public class RedirectionController {
    private final RedirectionService redirectionService;

    @Autowired
    public RedirectionController(RedirectionService redirectionService) {
        this.redirectionService = redirectionService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute(DTO, new RedirectionDto());
        model.addAttribute(URL_LIST, redirectionService.getShortenedUrls());
        return INDEX;
    }

    @PostMapping
    public String shortenUrl(RedirectionDto redirectionDto, Model model) {
        redirectionService.shortenUrl(redirectionDto);
        model.addAttribute(DTO, redirectionDto);
        return PROCESSED;
    }

    @ResponseBody
    @GetMapping("/{alias}")
    public String redirect(@PathVariable String alias, HttpServletResponse httpServletResponse) {
        return redirectionService.redirect(alias, httpServletResponse);
    }
}
