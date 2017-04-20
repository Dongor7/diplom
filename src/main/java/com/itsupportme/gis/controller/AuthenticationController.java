package com.itsupportme.gis.controller;


import com.itsupportme.gis.component.response.AjaxResponse;
import com.itsupportme.gis.component.user.SSOAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping(value = "/login")
public class AuthenticationController {

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private SSOAuthenticator ssoAuthenticator;

    @RequestMapping(value = "/error", method = RequestMethod.GET, headers = "Auth-Request=1")
    @ResponseBody
    public AjaxResponse errorLogin()
    {
        return new AjaxResponse(1, messageSource.getMessage("login.sign_in_failure", new Object[]{}, Locale.US));
    }

    @RequestMapping(value = "/success", method = RequestMethod.GET, headers = "Auth-Request=1")
    @ResponseBody
    public AjaxResponse successAjaxLogin(HttpServletRequest request)
    {
        String url = request.getContextPath();
        return new AjaxResponse(AjaxResponse.CODE_SUCCESS, url);
    }

    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public String sso(HttpServletRequest request) throws UnsupportedEncodingException {

        String login      = "redirect:/login";
        String workspace  = "redirect:/workspace";
        String profile    = "redirect:/user/profile";

        String ticket   = request.getParameter("ticket");
        Integer result  = ssoAuthenticator.doSSO(ticket);

        if (Objects.equals(result, SSOAuthenticator.SSO_SUCCESS)) {
            return workspace;
        }
        if (Objects.equals(result, SSOAuthenticator.SSO_SUCCESS_NEW_USER)) {
            return profile;
        }

        return login;
    }
}
