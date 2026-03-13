package com.dimata.util;

import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

/**
 * Hashing implementation using Bcrypt Algorithm.
 *
 * @author Hariyogi
 */
@ApplicationScoped
public class BcryptHashImpl {

	public boolean isPasswordMatch(String inputPassword, String hashingPassword) {
		if (StringUtils.isAllBlank(inputPassword, hashingPassword)) {
			throw new IllegalArgumentException("inputPassword and hashingPassword is required");
		}

		return BcryptUtil.matches(inputPassword, hashingPassword);
	}

	public String hashPasword(String password) {
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("password is blank");
		}

		return BcryptUtil.bcryptHash(password);
	}
}
