package com.hafidtech.api_gunungcondongcom.exception;

import com.hafidtech.api_gunungcondongcom.response.CommonResponseBean;
import com.hafidtech.api_gunungcondongcom.response.ErrorBean;
import com.hafidtech.api_gunungcondongcom.utils.MessageUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class CommonExceptionHandlers {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CommonResponseBean> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorBean> message = new ArrayList<>();
        // Get field name
        ex.getFieldErrors().forEach(error -> {
                    if (null == error.getDefaultMessage()) return;
                    message.add(new ErrorBean("E0001",error.getField() +", "+ error.getDefaultMessage()));
                }
        );
        CommonResponseBean res = new CommonResponseBean();
        res.setTimestamp(LocalDateTime.now());
        res.setErrors(message);
        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
    }
}