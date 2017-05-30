package com.itsupportme.gis.controller.customer;


import com.itsupportme.gis.entity.Contacts;
import com.itsupportme.gis.entity.Customer;
import com.itsupportme.gis.entity.dao.ContactsDao;
import com.itsupportme.gis.entity.dao.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/customers")
public class CustomerListController {

    @Autowired
    private CustomerDao customerDao;

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String main() {

        return "global/customer_page";
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getCustomerList(Model model) {

        List<Customer> customerList = customerDao.findAll();

        model.addAttribute("customerList", customerList);

        return "module/customer/list";
    }
}
