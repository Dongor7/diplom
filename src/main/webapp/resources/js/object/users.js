
var Users = (function(){

    Users.ON_AFTER_USERS_LIST_WAS_LOADED = "AFTER_USERS_LIST_WAS_LOADED";

    Users.prototype.container       = null;
    Users.prototype.formContainer   = null;
    Users.prototype.addButton       = null;
    Users.prototype.editButton      = null;
    Users.prototype.data            = null;

    function Users(container, data){

        var self = this;

        data = data || {};

        self.container = container;
        self.overlay   = self.container.next();
        self.data      = data;

        self.init();
    }

    Users.prototype.init = function(){

        var self = this;


        self.overlay.show();

        jQuery.ajax({

            type:       "POST",
            url:        Application._base_url + "/configuration/user_management/load_users_list",
            context:    self.container,
            data:       self.data,

            error: function (xhr, status, error) {

                if (window.console) {
                    console.log(xhr.responseText, status, error);
                }

            },

            beforeSend: function () {

            },

            success: function(html) {

                self.container.html(html);

                $(document).trigger(Users.ON_AFTER_USERS_LIST_WAS_LOADED, [self.data]);
                self.registerEvents();
                self.overlay.hide();
            }

        });
    };

    Users.prototype.registerEvents = function(){

        var self = this;

        self.addButton      = self.container.find("#add-button");
        self.editButton     = self.container.find(".edit-button");
        self.formContainer  = self.container.find("#form-container");
        self.paginatedForm  = self.container.find("form.form-paginated");

        self.paginatedForm.submit(function(e) {
            e.preventDefault();
            new Users(self.container, self.paginatedForm.serialize());
        });

        self.addButton.off().click(function(){

            self.addButton.prop("disabled", true);

            var form = new UsersForm(self.formContainer);

            Application.scroll(self.formContainer);
            }
        );

        self.editButton.off().click(function(){

            self.addButton.prop("disabled", true);
            self.editButton.prop("disabled", true);

            var id = $(this).attr("data");
            var form = new UsersForm(self.formContainer, id);

            Application.scroll(self.formContainer);
        });
    };
    return Users;
})();

var UsersForm = (function(){

    UsersForm.ON_AFTER_USER_ADDED           = "AFTER_USER_ADDED";
    UsersForm.ON_AFTER_CANCEL_WAS_PUSHED    = "AFTER_CANCEL_WAS_PUSHED";

    UsersForm.prototype.submitButton        = null;
    UsersForm.prototype.formContainer       = null;
    UsersForm.prototype.form                = null;


    function UsersForm(container, id){

        id = id || 0;

        var path = "user_management/users_form";

        if(id != 0){
            path = path + "/" + id;
        }

        var self = this;

        self.formContainer = container;
        self.formContainer.load(
            path,function(){
                self.init();
            }
        )

    }

    UsersForm.prototype.init = function(){

        var self = this;

        self.form               = self.formContainer.find("form");
        self.submitButton       = self.formContainer.find('button[type="submit"]');
        self.errorBlock         = self.formContainer.find("#add-users-error");
        self.cancelButton       = self.formContainer.find("#cancel-button");
        self.registerEvents();
    };

    UsersForm.prototype.registerEvents = function(){

        var self = this;

        self.cancelButton.off().click(function(){

            $(document).trigger(UsersForm.ON_AFTER_CANCEL_WAS_PUSHED);

        });


        self.form.submit(function(e) {
            e.preventDefault();
            self.submitForm();
        });
    };

    UsersForm.prototype.submitForm = function(){

        var self = this;

        Application.ajaxForm(
            self.formContainer,
            self.form, {
                onError: function(xhr, status, error) {
                   self.errorBlock.html("Unexpected error occurred.");
                },
                onBeforeSend: function () {
                    self.errorBlock.html("");
                    self.form.find("input, button").prop("disabled", true);
                },
                onSuccess: function(data) {
                    if(data.code == 0) {
                        $(document).trigger(UsersForm.ON_AFTER_USER_ADDED)
                    } else {
                        self.form.find("input, button").prop("disabled", false);
                        self.errorBlock.html(data.message);
                    }
                }
            }
        );
    };

    return UsersForm;
})();
