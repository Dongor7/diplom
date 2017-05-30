package com.itsupportme.gis.controller.customer;

import com.itsupportme.gis.component.response.AjaxResponse;
import com.itsupportme.gis.component.response.ValidationFailedResponse;
import com.itsupportme.gis.entity.Contacts;

import com.itsupportme.gis.entity.Customer;
import com.itsupportme.gis.entity.User;
import com.itsupportme.gis.entity.dao.ContactsDao;
import com.itsupportme.gis.entity.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/customers")
public class CustomerFormController {

    @Autowired
    private CustomerDao customerDao;

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String load( Model model) {

        Customer customer = new Customer();


        model.addAttribute("Customer", customer);

        return "/module/customer/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse post(@Valid @ModelAttribute Customer customer, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return new ValidationFailedResponse(bindingResult.getAllErrors());
        }

        User user = (User) model.asMap().get("User");

        if (customer.getId() == null) {
            customerDao.save(customer, user);
        } else {
            Customer existingCustomer = customerDao.findById(customer.getId());
            existingCustomer.setName(customer.getName());
            customerDao.update(existingCustomer, user);
        }

        return new AjaxResponse();
    }
}
