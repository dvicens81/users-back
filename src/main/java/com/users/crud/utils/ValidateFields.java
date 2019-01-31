package com.users.crud.utils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Component;

import com.users.crud.dto.UserDTO;
import com.users.crud.error.MethodArgumentNotValid;

@Component
public class ValidateFields{
	
	public void validateFields (UserDTO user) throws MethodArgumentNotValid {
		validateEmail(user.getEmail());
	}
	
	private void validateEmail(String email)  {
		{
			try {
				InternetAddress internetAddress = new InternetAddress(email);
				internetAddress.validate();
			}catch(NullPointerException ex) {
				throw new MethodArgumentNotValid();
			}catch(AddressException ex) {
				throw new MethodArgumentNotValid();
			}
		}
	}

}
