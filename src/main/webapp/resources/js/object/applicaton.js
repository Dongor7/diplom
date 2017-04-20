/**
 *
 */
var Application = (function() {
    Application._base_url                = $("meta[name='_base_url']").attr("content");
    Application.prototype.container      = null;

    Application.prototype._csrf          = null;
    Application.prototype._csrf_header   = null;
    Application.prototype._base_url      = null;

    /**
     * Static method to bind pagination
     * @param context
     */
    Application.bindPagination = function(context) {

        var link = context.find("ul.pagination li a");
        var form = context.find(".form-paginated");

        if (form.length == 0) {
            alert("Please implement form.form-paginated to use pagination functionality")
        }

        link.off().on("click", function(e) {

            e.preventDefault();

            var pageInput    = form.find("input[name='page']");
            var pageSelected = $(this).attr("href").replace("#", "");

            pageInput.val(pageSelected);
            form.submit();
        });
    };

    /**
     * Static method to scroll window to the provided element.
     * @param element
     */
    Application.scroll = function(element) {
        $("html, body").animate({
            scrollTop: element.offset().top
        }, 1000);
    };


    /**
     * @type {{}}
     * @namespace
     */
    var ajaxOptions = {
        onError:      function() {},
        onBeforeSend: function() {},
        onSuccess:    function() {}
    };

    /**waterproof_9
     *
     * Ajax form submit, including validation, and different ajax stuff
     *
     * @param container
     * @param form
     * @param ajaxOptions
     */
    Application.ajaxForm = function(container, form, ajaxOptions) {

        jQuery.ajax({
            type:       "POST",
            dataType:   "JSON",
            url:        form.attr("action"),
            data:       form.serialize(),
            context:    container,
            handleForm: true,

            error: function (xhr, status, error) {
                ajaxOptions.onError(xhr, status, error);
                if (window.console) {
                    console.log(xhr.responseText, status, error);
                }

            },
            beforeSend: function () {
                ajaxOptions.onBeforeSend();
            },
            success: function (data) {
               ajaxOptions.onSuccess(data);
            }
        });
    };

    /**
     * @param container An jQuery object
     *
     * @constructor
     */
    function Application(container) {
        this.container = container;
    }

    /**
     * Initializes login page
     */
    Application.prototype.init = function () {

        this._csrf         = $("meta[name='_csrf']").attr("content");
        this._csrf_header  = $("meta[name='_csrf_header']").attr("content");

        this.registerEvents();
    };

    Application.prototype.registerEvents = function () {

        var self = this;

        // Remove event from pre-expanded menu element
        // self.container.find(".close-forbidden").off();

        self.createPreFilter();
        self.bindAjaxComplete();
    };

    Application.prototype.bindAjaxComplete = function() {

        var self = this;

        // This should create automatic form error handling
        // noinspection JSUnresolvedFunction
        $(document).ajaxComplete(function(event, xhr, settings) {
            if (settings.dataType != undefined && settings.dataType.toLowerCase() === "json" && settings.handleForm === true) {

                var data    = JSON.parse(xhr.responseText);
                var context = settings.context;
                if (data.code == 1 && data.message == "Validation Failed" && context != undefined) {
                    self.bindErrors(context, data.object)
                }
            }
        });
    };

    Application.prototype.createPreFilter = function() {

        var self = this;

        // Remove errors before sending ajax
        // noinspection JSUnresolvedFunction
        $.ajaxPrefilter(function(options, originalOptions, jqXHR) {
            if (options.dataType != undefined && options.dataType.toLowerCase() === "json" && options.handleForm === true) {

                var context = options.context;
                context.find(".validation-help-block").remove();
                context.find(".form-group").removeClass("has-error").removeClass("validation-has-error");
            }

            // Add token if it is not there
            if (options.type.toLowerCase() === "post" && options.data.indexOf("_csrf=") == -1) {

                // add leading ampersand if `data` is non-empty
                options.data += options.data ? "&": "";
                // add _token entry
                options.data += "_csrf=" + self._csrf;
            }
        });
    };

    Application.prototype.bindErrors = function(context, errors) {
        for (var i = 0; i < errors.length; i++) {

            /** @namespace errors.field */
            /** @namespace errors.defaultMessage */
            var errorFields = context.find("[name='" + errors[i].field  + "']");
            var formGroups  = errorFields.parents(".form-group");

            formGroups.addClass("has-error validation-has-error");

            if (errorFields.length == 1) {
                // Hack for unsupported validation problems
                if (errors[i].defaultMessage.indexOf("Failed to convert property value of type") > -1) {
                    errors[i].defaultMessage = "please check highlighted field"
                }

                // Actual binding
                formGroups.append("<span class='help-block validation-help-block'>" + errors[i].defaultMessage + "</span>");
            }
        }
    };

    return Application;

})();