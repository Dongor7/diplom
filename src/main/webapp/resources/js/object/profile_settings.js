var Settings = ( function(){

    Settings.ON_AFTER_SETTINGS_UPDATE = "AFTER_SETTINGS_UPDATE";

    Settings.prototype.container    = null;
    Settings.prototype.form         = null;
    Settings.prototype.submitButton = null;
    Settings.prototype.errorBlock   = null;
    Settings.prototype.overlay      = null;


    function Settings(container){

        var self = this;

        self.container      = container;
        self.overlay        = self.container.next();
        self.overlay.show();

        self.container.load("profile/interface_settings", function(){
            self.init()
        });
    }

    Settings.prototype.init = function(){

        var self = this;

        self.errorBlock = self.container.find("#error-block");
        self.form = self.container.find("form");
        self.submitButton = self.container.find('button[type="submit"]');
        self.overlay.hide();

        self.registerEvents();
    };

    Settings.prototype.registerEvents = function(){

        var self = this;

        self.form.submit(function(e){
            e.preventDefault();
            self.submitForm();
        });

        var skinPicker = $("input[name='theme']");
        skinPicker.off().on("change", function() {
            var value = $("input[name='theme']:checked").attr("data");
            self.changeTheme(value);
        });
    };

    Settings.prototype.submitForm = function(){

        var self = this;

        jQuery.ajax({

            type:       "POST",
            dataType:   "JSON",
            url:        self.form.attr("action"),
            data:       self.form.serialize(),
            context:    self.container,
            handleForm: true,

            error: function (xhr, status, error) {

                self.errorBlock.html("Unexpected error occurred.");

                if (window.console) {
                    console.log(xhr.responseText, status, error);
                }

            },

            beforeSend: function () {

                self.form.find("input, button").prop("disabled", true);
            },

            success: function(data) {
                self.form.find("input, button").prop("disabled", false);
                if(data.code == 0) {
                    $(document).trigger(Settings.ON_AFTER_SETTINGS_UPDATE)
                }
            }

        });
    };

    Settings.prototype.changeTheme = function(value) {
        $("body").attr("class", "skin-" + value);
    };

    return Settings;
})();