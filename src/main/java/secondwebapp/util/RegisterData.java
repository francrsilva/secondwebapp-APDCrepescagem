package secondwebapp.util;

//import secondwebapp.util.DataValidation;
//import secondwebapp.util.RegisterDataValidation;

public class RegisterData {
		
	public String username;
	public String password;
	public String confirmation;
	public String email;
	public String name;
	public String perfil;
	public String telmv;
	public String tel_fixo;
	public String address1;
	public String address2;
	public String locality;
	public String NIF;
	public long role;
	
	public RegisterData() {
		
	}
	
	public RegisterData(
			String username,
			String password,
			String confirmation,
			String email,
			String name,
			String perfil,
			String telmv,
			String tel_fixo,
			String address1,
			String address2,
			String locality,
			String NIF) {
		this.username = username;
		this.password = password;
		this.confirmation = confirmation;
		this.email = email;
		this.name = name;
		this.perfil = perfil;
		this.telmv = telmv.equals("") ? "INDEFINIDO" : telmv;
		this.tel_fixo = tel_fixo.equals("") ? "INDEFINIDO" : tel_fixo;
		this.address1 = address1.equals("") ? "INDEFINIDO" : address1;
		this.address2 = address2.equals("") ? "INDEFINIDO" : address2;
		this.locality = locality.equals("") ? "INDEFINIDO" : locality;
		this.NIF = NIF.equals("") ? "INDEFINIDO" : NIF;
		this.role = 0;
	}
	
	public String getTel_fixo() {
		return this.tel_fixo;
	}
	
	public void setTel_fixo(String tel_fixo) {
		this.tel_fixo = tel_fixo == null || tel_fixo.equals("") ? "INDEFINIDO" : tel_fixo;
	}
	
	public String getTelmv() {
		return this.telmv;
	}
	
	public void setTelmv(String telmv) {
		this.telmv = telmv == null || telmv.equals("") ? "INDEFINIDO" : telmv;
	}
	
	public String getAddress1() {
		return this.address1;
	}
	
	public void setAddress1(String address1) {
		this.address1 = address1 == null || address1.equals("") ? "INDEFINIDO" : address1;
	}
	
	public String getAddress2() {
		return this.address2;
	}
	
	public void setAddress2(String address2) {
		this.address2 = address2 == null || address2.equals("") ? "INDEFINIDO" : address2;
	}
	
	public String getLocality() {
		return this.locality;
	}
	
	public void setLocality(String locality) {
		this.locality = locality == null || locality.equals("") ? "INDEFINIDO" : locality;
	}
	
	public String getNif() {
		return this.NIF;
	}
	
	public void setNif(String nif) {
		this.NIF = NIF == null || NIF.equals("") ? "INDEFINIDO" : NIF;
	}
	/*
	public DataValidation validateData() {
		String errorMessage = null;
				
		boolean validUsername = username != null && !username.isEmpty();
		boolean validPassword = password!= null && password.length() >= 6;
		boolean validConfirmation = password != null && confirmation != null && password.equals(confirmation);
		boolean validEmail = validEmail();
		boolean validName = !name.isEmpty();
		boolean validVisibility = visibility.equals("public") || visibility.equals("private");
		boolean validTel_fixo = tel_fixo.equals("INDEFINIDO") || tel_fixo.length() > 0;
		boolean validTelmv = telmv.equals("INDEFINIDO") || telmv.length() > 0;
		boolean validAddress1 = address1.equals("INDEFINIDO") || address1.length() > 0;
		boolean validAddress2 = address2.equals("INDEFINIDO") || address2.length() > 0;
		boolean validLocality = locality.equals("INDEFINIDO") || locality.length() > 0;
		boolean validNif = nif.equals("INDEFINIDO") || nif.length() == 9;
		
		
		boolean valid = validUsername && validPassword && validConfirmation && validEmail && validVisibility 
				&& validTel_fixo && validTelmv && validAddress1 && validAddress2 && validLocality && validNif;
		
		if(!valid) {
			errorMessage = "";
			
			if(!validUsername) {
				errorMessage += "Username must be not null and not empty; ";
			}
			
			if(!validPassword) {
				errorMessage += "Password must have at least 6 characters; ";
			}
			
			if(!validConfirmation) {
				errorMessage += "Passwords don't match; ";
			}
			
			if(!validEmail) {
				errorMessage += "Email not valid; ";
			}
			
			if(!validName) {
				errorMessage += "Name must be not empty; ";
			}
			
			if(!validVisibility) {
				errorMessage += "Visibility must be either public or private; ";
			}
			
			if(!validTel_fixo) {
				errorMessage += "Phone number (opt) must be not empty; ";
			}
			
			if(!validTelmv) {
				errorMessage += "Cellphone number (opt) must be not empty; ";
			}
			
			if(!validAddress1) {
				errorMessage += "Address1 (opt) must be not empty; ";
			}
			
			if(!validAddress2) {
				errorMessage += "Address2 (opt) must be not empty; ";
			}
			
			if(!validLocality) {
				errorMessage += "Locality (opt) must be not empty; ";
			}
			
			
			if(!validNif) {
				errorMessage += "NIF (opt) must have 9 digits; ";
			}
		}
		
		DataValidation dataValidation = new RegisterDataValidation(valid, errorMessage);
		return dataValidation;
	}
*/


	public boolean validValue(String value) {
		// TODO Auto-generated method stub
		if (value!=null)
				return true;
		return false;
	}
	public boolean validRegistration() {
		// TODO Auto-generated method stub
		//if (username!=null  && password!=null && confirmation!=null && password.equals(confirmation) && email!=null && name!=null)
		return (validValue(username)&&validValue(password)&&validValue(email)&&validValue(confirmation));
			//	return true;
		//return false;
	}

	public boolean validEmail() {
		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		boolean result=email.matches(EMAIL_REGEX);
		return result;
				
	}
	public boolean validPassword() {
		String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{7,16}$";
		boolean result=password.matches(PASSWORD_REGEX);
		return result;
	}
	/*
	 * public boolean validRegistration2() {
		// TODO Auto-generated method stub
				return true;
	}
	*/
}
