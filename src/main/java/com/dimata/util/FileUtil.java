package com.dimata.util;


import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

import java.io.IOException;
import java.io.InputStream;

public interface FileUtil {

    /**
     * Validasi object dengan validator.
     *
     * @param obj obj yang ingin divalidasi
     * @param validator validator. NOTE. gunakan inject untuk ambil validator.
     *
     * @throws ConstraintViolationException jika tidak valid.e
     */
    static void validateObject(Object obj, Validator validator) {
        var constraint = validator.validate(obj);
        if (!constraint.isEmpty()) {
            throw new ConstraintViolationException(constraint);
        }
    }


    static InputStream getFileByFromResources(String fileName) throws IOException {
        var is = FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
        	throw new IllegalArgumentException(fileName + " not found in resource folder");
        }
        return is;
    }

    static byte[] getByteByFromResources(String fileName) throws IOException {
        try(var is = FileUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
            	throw new IllegalArgumentException(fileName + " not found in resource folder");
            }
            return is.readAllBytes();
        }
    }
}
