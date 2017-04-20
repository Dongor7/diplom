/**
 * @type {Function}
 */
var Authentication = (function() {

    Authentication.prototype.container      = null;

    Authentication.prototype._csrf          = null;
    Authentication.prototype._csrf_header   = null;

    Authentication.prototype.usernameInput  = null;
    Authentication.prototype.passwordInput  = null;
    Authentication.prototype.submitButton   = null;
    Authentication.prototype.errorContainer = null;

    /**
     * @param container An jQuery object
     *
     * @constructor
     */
    function Authentication(container) {
        this.container = container;
    }

    /**
     * Initializes login page
     */
    Authentication.prototype.init = function () {

        this._csrf         = $("meta[name='_csrf']").attr("content");
        this._csrf_header  = $("meta[name='_csrf_header']").attr("content");

        this.usernameInput  = this.container.find("input[name='username']");
        this.passwordInput  = this.container.find("input[name='password']");
        this.submitButton   = this.container.find("button[type='submit']");
        this.errorContainer = this.container.find("p.error-text");

        this.usernameInput.focus();

        this.registerEvents();

    };

    Authentication.prototype.registerEvents = function () {

        var self = this;

        self.submitButton.off().on("click", function(e) {
            e.preventDefault();
            self.logIn();
        });

    };

    /**
     * Switches login form on and off
     */
    Authentication.prototype.switchForm = function()
    {
        var self = this;

        var direction = true;
        if (self.submitButton.prop("disabled")) {
            direction = false;
        }

        switch (direction) {
            case true:
                self.container.find("button, input").prop("disabled", direction);
                break;
            default:
                self.container.find("button, input").prop("disabled", direction);
        }

    };

    /**
     * Actual ajax log in
     */
    Authentication.prototype.logIn = function() {

        var self = this;

        self.errorContainer.html("");

        var username = self.usernameInput.val();
        var password = self.passwordInput.val();

        jQuery.ajax({

            type:     "POST",
            dataType: "JSON",

            data: {
                username: username,
                password: password
            },

            error: function(xhr, status, error) {
                if (window.console) {
                    console.log(xhr.responseText, status, error);
                }
                self.switchForm();
                self.errorContainer.html("Unexpected error occurred please contact developers.");
                self.usernameInput.focus();
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader(self._csrf_header, self._csrf);
                xhr.setRequestHeader("Auth-Request", "1");
                self.switchForm();
            },
            success: function(data) {
                // Success?
                if (data.code == 0) {
                    if (data.message == "") {
                        data.message = "/";
                    }
                   document.location.href = data.message;
                }
                else {
                    self.switchForm();
                    self.errorContainer.html(data.message);
                    self.usernameInput.focus();
                }
            }
        });

    };

    return Authentication;

})();