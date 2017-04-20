$(document).ready(function(){

    var usersContainer = $("#users-container");
    var users          = new Users(usersContainer);
    var filterData     = {};

    $(document).on(UsersForm.ON_AFTER_USER_ADDED, function(e){

        users = new Users(usersContainer,filterData);
    });

    $(document).on(UsersForm.ON_AFTER_CANCEL_WAS_PUSHED, function(e){
        users = new Users(usersContainer,filterData);
    });

    $(document).on(Users.ON_AFTER_USERS_LIST_WAS_LOADED, function(e, data) {
        Application.bindPagination(usersContainer);
        filterData = data;
    });
});
