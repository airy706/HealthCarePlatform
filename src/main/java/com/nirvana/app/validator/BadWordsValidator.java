package com.nirvana.app.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BadWordsValidator implements ConstraintValidator<BadWords, String> {

	private static final String[] BAD_WORDS={"卧槽","你麻痹","fuck"};
	
	private boolean checkValid(String val){
		for (String str:BAD_WORDS){
			if (val.contains(str)) return false;
		}
		return true;
	}
	
	public void initialize(BadWords arg0) {
		
	}

	public boolean isValid(String val, ConstraintValidatorContext arg1) {
		return checkValid(val);
	}
	
}
