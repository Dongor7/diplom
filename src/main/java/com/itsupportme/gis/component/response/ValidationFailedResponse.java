package com.itsupportme.gis.component.response;

import org.springframework.validation.ObjectError;

import java.util.List;

public class ValidationFailedResponse extends AjaxResponse {
    public ValidationFailedResponse(List<ObjectError> errors) {
        super(1, "Validation Failed", errors);
    }
}
