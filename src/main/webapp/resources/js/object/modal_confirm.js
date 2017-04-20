var ConfirmModal = (function() {

    ConfirmModal.prototype.confirmButtonClicked     = false;
    ConfirmModal.prototype.container                = null;
    ConfirmModal.prototype.button                   = null;
    ConfirmModal.prototype.confirmButton            = null;
    ConfirmModal.prototype.errorBlock               = null;
    ConfirmModal.prototype.callback                 = function() {};


    function ConfirmModal(modal, callback) {

        var self = this;

        self.confirmButtonClicked = false;

        if (modal.length == 0) {
            alert("ConfirmModal: Please implement container #modal-confirm to use modal confirm functionality");
        }

        if (typeof(callback) === "function") {
            self.callback  = callback;
        } else {
            alert("ConfirmModal: Invalid 'callback' argument provided, default empty function is used");
        }


        self.modal          = modal;
        self.container      = self.modal.find("#modal-confirm-container");
        self.confirmButton  = self.container.find("button#confirm-button");

        self.modal.modal({
            show: false
        });

        self.modal.modal("show");
        self.init();
    }


    ConfirmModal.prototype.init = function() {

        var self = this;
        self.registerEvents();
    };

    ConfirmModal.prototype.registerEvents = function() {

        var self = this;

        self.modal.on("hidden.bs.modal", function (e) {
            if (self.confirmButtonClicked == true) {
                self.callback();
            }
        });

        self.confirmButton.off().click(function() {
            self.confirmButtonClicked = true;
            self.modal.modal("hide");
        });
    };

    return ConfirmModal;
})();