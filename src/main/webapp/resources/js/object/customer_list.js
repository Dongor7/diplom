/**
 * @type {Function}
 */
var CustomerList = (function() {

    CustomerList.prototype.container = null;
    CustomerList.prototype.overlay   = null;

    function CustomerList(container){
        
        this.container = container;
        this.overlay   = this.container.next();
        
        this.container.load(
            Application._base_url + "/customers/list",
            this.init()
        );
    }

    CustomerList.prototype.init = function(){
        this.overlay.hide();
    };

    return CustomerList;
})();