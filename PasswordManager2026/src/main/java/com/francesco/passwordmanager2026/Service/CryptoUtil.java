package com.francesco.passwordmanager2026.Service;

import org.mindrot.jbcrypt.BCrypt;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CryptoUtil {
    
    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    
    // ===== BCRYPT per password AccountUtente =====
    
    /**
     * Hash password di accesso usando BCrypt
     */
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    /**
     * Verifica password contro hash BCrypt
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
    
    // ===== AES-256 per CredenzialiAccesso =====
    
    /**
     * Deriva chiave AES-256 dalla password utente
     * @param userPassword password dell'utente loggato
     * @param userEmail email usata come salt (univoca per utente)
     */
    public static SecretKey deriveKey(String userPassword, String userEmail) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(
            userPassword.toCharArray(), 
            userEmail.getBytes(), 
            100000, 
            256
        );
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }
    
    /**
     * Cifra password credenziale
     * @return Base64(IV + ciphertext)
     */
    public static String encryptPassword(String plainPassword, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        
        // Genera IV casuale da 16 bytes
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(plainPassword.getBytes("UTF-8"));
        
        // Concatena IV + ciphertext
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        
        return Base64.getEncoder().encodeToString(combined);
    }
    
    /**
     * Decifra password credenziale
     */
    public static String decryptPassword(String encryptedPassword, SecretKey key) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedPassword);
        
        // Estrai IV (primi 16 bytes) e ciphertext (resto)
        byte[] iv = new byte[16];
        byte[] encrypted = new byte[combined.length - 16];
        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, encrypted, 0, encrypted.length);
        
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, "UTF-8");
    }
}
