package com.banco.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private static final Logger logger = LoggerFactory.getLogger(CustomException.class);

	public CustomException(String message) {
		logger.error("CustomException: {}", message);
        super(message);
    }
}
