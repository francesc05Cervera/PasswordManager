package com.francesco.passwordmanager2026.Service;

import com.francesco.passwordmanager2026.entity.AccountUtente;

public class DataCheck 
{
	
	public static boolean VerifyInput_5String(String s1, String s2, String s3, String s4, String s5)
	{
		if(s1.isBlank() || s2.isBlank() || s3.isBlank() || s4.isBlank() || s5.isBlank())
			return false; 
		
		return true; 
	}
	
	public static boolean VerifyInput_1StringAND1User(AccountUtente account, String stringa)
	{
		if(account == null || stringa == null) { return false; } 
		return true;
	}
	
	public static boolean VerifyInput_2String(String stringa1, String stringa2)
	{
		if(stringa1.isBlank() || stringa2.isBlank()) { return false; } 
		return true;
	}
	
	public static boolean VerifyInput_4StringAND1Boolean(String s1, String s2, String s3, String s4, boolean b1) {

	    if (s1 == null || s1.isBlank()) return false;
	    if (s2 == null || s2.isBlank()) return false;
	    if (s3 == null || s3.isBlank()) return false;
	    if (s4 == null || s4.isBlank()) return false;

	    // Il booleano è sempre valido, quindi non serve controllo
	    return true;
	}

	public static boolean VerifyInput_1String(String nuovaPassword) {
		
		if(nuovaPassword.isBlank()) { return false; }
		
		return true;
	}

	public static boolean VerifyInput_1Int(int idCredenziale) {
		// TODO Auto-generated method stub
		return idCredenziale > 0;
	}

	
}
