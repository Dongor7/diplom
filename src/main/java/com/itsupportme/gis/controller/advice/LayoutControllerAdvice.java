package com.itsupportme.gis.controller.advice;

import com.itsupportme.gis.component.util.UrlParser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class LayoutControllerAdvice {

    @ModelAttribute("BaseURL")
    public String baseUrl(HttpServletRequest request)
    {
        return UrlParser.getRootUrl(request);
    }
}
