package com.itsupportme.gis.controller.configuration;


import com.itsupportme.gis.component.RoleRepository;
import com.itsupportme.gis.component.form.UserManagementForm;
import com.itsupportme.gis.component.locker.LockManager;
import com.itsupportme.gis.component.pagination.Unit;
import com.itsupportme.gis.component.response.AjaxResponse;
import com.itsupportme.gis.component.response.ObjectIsLockedResponse;
import com.itsupportme.gis.component.response.ValidationFailedResponse;
import com.itsupportme.gis.component.user.UserBuilder;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.UserRole;
import com.itsupportme.gis.entity.dao.UserDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@Controller
public class UserManagementController extends ConfigurationController {

    @Autowired
    UserDao userDao;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    UserBuilder userBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LockManager lockManager;

    @Secured({RoleRepository.ROLE_DEVELOPER})
    @RequestMapping("/user_management")
    public String main(){

        return "global/users";
    }

    @Secured({RoleRepository.ROLE_DEVELOPER})
    @RequestMapping(value = "/user_management/load_users_list", method = RequestMethod.POST)
    public String usersList(@Valid @ModelAttribute("UserManagementForm") UserManagementForm form,
                            BindingResult bindingResult, Model model) {

        String template = "module/user_management/users_list";

        if (bindingResult.hasErrors()) {
            return template;
        }

        Integer itemsTotal = userDao.countAll(form.getUsername(),form.getFirst(), form.getLast());
        Unit unit          = new Unit(itemsTotal, form.getItemsPerPage(), form.getPage(), 3);

        List<User> usersList = userDao.findAll(
                                                form.getUsername(),
                                                form.getFirst(),
                                                form.getLast(),
                                                unit.getFirstResult(),
                                                form.getItemsPerPage()
        );

        model.addAttribute("Unit", unit);
        model.addAttribute("UsersList", usersList);

        return template;
    }

    @Secured({RoleRepository.ROLE_DEVELOPER})
    @RequestMapping(value = "/user_management/users_form", method = RequestMethod.GET)
    public String usersForm(Model model){

        model.addAttribute("ActiveRoles", new ArrayList<>());
        model.addAttribute("UserRole",    RoleRepository.getRoles());
        model.addAttribute("UserForm",    new User());

        return "module/user_management/users_form";
    }

    @Secured({RoleRepository.ROLE_DEVELOPER})
    @RequestMapping(value = "/user_management/users_form/{id}", method = RequestMethod.GET)
    @Transactional
    public String usersForm(@PathVariable Integer id, Model model){

        User currentUser = (User) model.asMap().get("User");
        User user        = userDao.findUser(id);
        String template  = "module/user_management/users_form";

        if (user == null) {
            model.addAttribute("error", "No users with id " + Integer.toString(id) + " found");
            return template;
        }

        if (!lockManager.lock(user, currentUser)) {
            model.addAttribute("IsLocked", true);
            model.addAttribute("LockDetails", lockManager.getLockInfo(user));
        }

        List<String> activeRoles = new ArrayList<>();
        for (UserRole role : user.getUserRole()) {
            activeRoles.add(role.getRole());
        }

        model.addAttribute("ActiveRoles", activeRoles);
        model.addAttribute("UserRole",    RoleRepository.getRoles());
        model.addAttribute("UserForm",    user);

        return template;
    }

    @Secured({RoleRepository.ROLE_DEVELOPER})
    @RequestMapping(value = "/user_management/users_form", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public AjaxResponse submitUsersForm(
            @RequestParam(name = "role", required = false) Set<String> role,
            @Valid User user,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            return new ValidationFailedResponse(bindingResult.getAllErrors());
        }

        String password = user.getPassword();
        Session session = sessionFactory.getCurrentSession();

        if (user.getId() == null) {
            user = userBuilder.build(user, password);
            session.persist(user);
            session.flush();
        } else {

            User currentUser = (User) model.asMap().get("User");
            User originalUser = userDao.findUser(user.getId());
            if (!lockManager.lock(user, currentUser)) {
                return new ObjectIsLockedResponse();
            }

            if (Objects.equals(password, "")) {
                user.setPassword(originalUser.getPassword());
            } else {
                user.setPassword(passwordEncoder.encode(password));
            }
            session.clear();
            session.update(user);

            // Delete existent roles
            Query query = session
                    .createQuery("delete UserRole where user = :user")
                    .setParameter("user", user);

            Integer result = query.executeUpdate();
        }

        if (role == null) {
            role = new HashSet<>();
        }

        role.add(RoleRepository.ROLE_USER);

        for (String givenRole : role) {

            UserRole userRole = new UserRole();
            userRole
                    .setUser(user)
                    .setRole(givenRole);

            session.persist(userRole);
        }

        session.flush();

        return new AjaxResponse();
    }

}
