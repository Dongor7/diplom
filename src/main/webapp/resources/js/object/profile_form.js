var ProfileForm = (function(){

    ProfileForm.ON_AFTER_PROFILE_UPDATE = "AFTER_PROFILE_UPDATE";

    ProfileForm.prototype.container     = null;
    ProfileForm.prototype.submitButton  = null;
    ProfileForm.prototype.overlay       = null;
    ProfileForm.prototype.form          = null;

    function ProfileForm(container){

        var self = this;

        self.container = container;
        self.overlay = self.container.next();
        self.overlay.show();
        self.container.html("");

        self.container.load("profile/profile_form", function(){
            self.init();
        })
    }


    ProfileForm.prototype.init = function(){

        var self = this;

        self.submitButton   = self.container.find('button[type="submit"]');
        self.errorBlock     = self.container.find("#error-block");
        self.form           = self.container.find("form");
        self.overlay.hide();

        self.registerEvents();
    };

    ProfileForm.prototype.registerEvents = function(){

        var self = this;

        self.form.submit(function(e){

            e.preventDefault();
            self.submitForm();
        });
    };

    ProfileForm.prototype.submitForm = function(){

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
                    $(document).trigger(ProfileForm.ON_AFTER_PROFILE_UPDATE)
                }
            }

        });

    };


    return ProfileForm;
})();
