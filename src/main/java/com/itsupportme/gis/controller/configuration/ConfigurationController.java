package com.itsupportme.gis.controller.configuration;

import com.itsupportme.gis.component.RoleRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/configuration")
@Secured({RoleRepository.ROLE_MANAGER, RoleRepository.ROLE_DEVELOPER})
public class ConfigurationController {

}
