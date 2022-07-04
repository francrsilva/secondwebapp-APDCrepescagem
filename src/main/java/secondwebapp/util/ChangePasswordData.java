package secondwebapp.util;


public class ChangePasswordData {
	
		//ha um construtor deprecated nesta classe, fui eu que o meti, podes tirar de deprecated
	
		public String username;
		
		//nao tenho a certeza quanto a necessidade desta variavel
		public String password;
		
		
		public String newpassword;
		public String confirmation;
		private static final String INDEFINIDO = "INDEFINIDO";









		public ChangePasswordData(){
			
		}
		
		
		public ChangePasswordData(String username, String password){
			
			
			this.username = username;
			this.password = password;
		}
		
		
		public ChangePasswordData(String username, String password, String newpassword,String confirmation) {
			this.username = username;
			this.password = password;
			this.newpassword=newpassword;
			this.confirmation = confirmation;
			
			
		}
		public boolean validValue(String value) {
			// TODO Auto-generated method stub
			if (value!=null && !value.isEmpty())
					return true;
			return false;
		}
		public boolean validUpdate() {
			// TODO Auto-generated method stub
			//if (username!=null  && password!=null && confirmation!=null && password.equals(confirmation) && email!=null && name!=null)
			return (validValue(username)&&validValue(password)&&validValue(newpassword)&&validValue(confirmation));
				//	return true;
			//return false;
		}

		public boolean validPassword() {
			String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{7,16}$";
			boolean result=newpassword.matches(PASSWORD_REGEX);
			return result;
		}
	
		
		public boolean validRegistration2() {
			// TODO Auto-generated method stub
					return true;
		}
		

}


