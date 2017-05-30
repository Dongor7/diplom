/**
 * @type {Function}
 */
var CustomerList = (function() {

    CustomerList.ON_AFTER_CUSTOMER_SELECTED = "ON_AFTER_CUSTOMER_LIST_CUSTOMER_SELECTED";

    CustomerList.prototype.container = null;
    CustomerList.prototype.overlay   = null;
    CustomerList.prototype.customers = null;

    function CustomerList(container){

        var self = this;

        this.container = container;
        this.overlay   = this.container.next();

        this.container.load(
            Application._base_url + "/customers/list",
            function() {
                self.init();
            }
        );
    }

    CustomerList.prototype.init = function(){
        this.customers = this.container.find(".customer-row");
        this.overlay.hide();
        this.registerEvents();
    };

    CustomerList.prototype.registerEvents = function() {
        var self = this;

        self.customers.off().on("click", function() {
            var customerId = $(this).attr("data");
            $(document).trigger(CustomerList.ON_AFTER_CUSTOMER_SELECTED, [customerId]);
        });
    };

    return CustomerList;
})();