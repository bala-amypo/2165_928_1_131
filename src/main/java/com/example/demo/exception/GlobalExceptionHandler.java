package com.example.demo.exception;



import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;



import java.util.HashMap;

import java.util.Map;



@RestControllerAdvice

public class GlobalExceptionHandler {



    @ExceptionHandler(IllegalArgumentException.class)

    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public Map<String, String> handleIllegalArgument(IllegalArgumentException ex) {

        Map<String, String> error = new HashMap<>();

        error.put("error", ex.getMessage());

        return error;

        }



        @ExceptionHandler(ResourceNotFoundException.class)

        @ResponseStatus(HttpStatus.NOT_FOUND)

        public Map<String, String> handleNotFound(ResourceNotFoundException ex) {

            Map<String, String> error = new HashMap<>();

            error.put("error", ex.getMessage());

            return error;

            }



            @ExceptionHandler(Exception.class)

            @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

            public Map<String, String> handleOther(Exception ex) {

                Map<String, String> error = new HashMap<>();

                error.put("error", "Internal Server Error");

                return error;

                }

                }

                