package edu.spsu.swe2313.group7.library.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Robert Whitaker
 */
public class PasswordHash {

	public String passwordToHash(String incomingPassword) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA256");
			md.update(incomingPassword.getBytes());
			byte[] bytes = md.digest();
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			return builder.toString();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(PasswordHash.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}

	}
}
