package com.projectalpha.ricettarioonline.utils;

import java.util.regex.Pattern;

public class StringUtility {
	
	public static boolean containsOnlyLetters(String input){
		return Pattern.compile("^[a-zA-ZàèéìòùäöüÄÖÜ]*$").matcher(input).find();
	}
	
	public static boolean containsOnlyLettersAndNumbers(String input){
		return Pattern.compile("^[a-zA-Z0-9àèéìòùäöüÄÖÜ]*$").matcher(input).find();
	}
	
	public static boolean isValidEmail(String input){
		return Pattern.compile(
				"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
				.matcher(input).find();
	}
	
	

}
