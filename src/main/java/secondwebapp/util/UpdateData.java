package secondwebapp.util;

public class UpdateData {
		
		
		public String username;
		public String usernameToChange;
		public String email;
		public String name;
		public String tel_fixo;
		public String telmv;
		public String NIF;
		public String address1;
		public String address2;
		public String locality;
		public String perfil;
		public long role;
		private static final String INDEFINIDO = "INDEFINIDO";

		public UpdateData(){
			
		}
		
		public UpdateData(String username){
			
			
			this.username = username;
		}
		
		
		public UpdateData(String username,String usernameToChange, String email,long role, String perfil, String name, String tel_fixo, String telmv, String NIF, String address1,
		 String address2
		, String locality){
			
			this.name=name;
			this.username = username;
			this.usernameToChange=usernameToChange;
			this.email = email;
			this.role= 0L;
				
			this.perfil = perfil.equals("") ? INDEFINIDO : perfil; 
			this.tel_fixo = tel_fixo.equals("") ? INDEFINIDO : tel_fixo; 
			this.perfil = perfil.equals("") ? INDEFINIDO : perfil; 
			this.telmv = telmv.equals("") ? INDEFINIDO : telmv;	
			this.NIF = NIF.equals("") ? INDEFINIDO : NIF;
			this.address1 = address1.equals("") ? INDEFINIDO : address1;
			this.address2 = address2.equals("") ? INDEFINIDO : address2;
			this.locality = locality.equals("") ? INDEFINIDO : locality;

		}
		
		public boolean validValue(String value) {
			// TODO Auto-generated method stub
			if (value!=null && !value.isBlank())
					return true;
			return false;
		}
		public boolean validRegistration() {
			// TODO Auto-generated method stub
			//if (username!=null  && password!=null && confirmation!=null && password.equals(confirmation) && email!=null && name!=null)
			return (validValue(username)&&validValue(email));
				//	return true;
			//return false;
		}

		public boolean validEmail() {
			String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			boolean result=email.matches(EMAIL_REGEX);
			return result;
					
		}

}
