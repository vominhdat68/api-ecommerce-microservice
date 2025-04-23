package com.ecommerce.shared_libs.util;


import java.security.SecureRandom;

public class OTPUtil {

	private static final int MAX_OTP = 1_000_000; // 10^6
	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generateOtp() {
		int otp = secureRandom.nextInt(MAX_OTP);
		return String.format("%06d", otp);
	}


}