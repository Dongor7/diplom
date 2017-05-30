/**
 * @type {Function}
 */
var CustomerForm = (function() {

    CustomerForm.ON_AFTER_CUSTOMER_ADDED = "CUSTOMER_FORM_ON_AFTER_CUSTOMER_ADDED";

    CustomerForm.prototype.container  = null;
    CustomerForm.prototype.overlay    = null;
    CustomerForm.prototype.form       = null;

    function CustomerForm(container){


        var self = this;

        self.container  = container;
        self.overlay    = self.container.next();

        self.overlay.show();

        self.container.load(
            Application._base_url + "/customers/form/", function() {
                self.init();
            }
        );
    }

    CustomerForm.prototype.init = function(){

        var self  = this;
        self.form = self.container.find("#customer-form");

        self.registerEvents();
        self.overlay.hide();
    };

    CustomerForm.prototype.registerEvents = function() {

        var self  = this;

        self.form.submit(function(e) {
            e.preventDefault();
            self.submit();
        });
    };

    CustomerForm.prototype.submit = function() {

        var self  = this;

        $.ajax({

            type:       "POST",
            dataType:   "JSON",
            url:        self.form.attr("action"),
            data:       self.form.serialize(),
            context:    self.container,
            handleForm: true,

            error: function (xhr, status, error) {
                self.overlay.hide();
                if (window.console) {
                    console.log(xhr.responseText, status, error);
                }
            },

            beforeSend: function () {
                self.overlay.show();
            },

            success: function(data) {
                $(document).trigger(CustomerForm.ON_AFTER_CUSTOMER_ADDED);
                self.overlay.hide();
            }
        });
    };

    return CustomerForm;
})();