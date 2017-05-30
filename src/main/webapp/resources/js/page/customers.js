$(document).ready(function() {

    var customerContainer = $("#customer-container");
    var customerList      = new CustomerList(customerContainer);

    var customerFormContainer = $("#customer-form-container");
    var customerForm          = new CustomerForm(customerFormContainer);

    $(document).on(CustomerForm.ON_AFTER_CUSTOMER_ADDED, function(e){
        customerList = new CustomerList(customerContainer);
    });

    $(document).on(CustomerList.ON_AFTER_CUSTOMER_SELECTED, function(e){
        customerForm      = new CustomerForm(customerFormContainer);
    });
});