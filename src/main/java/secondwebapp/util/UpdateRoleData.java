package secondwebapp.util;

public class UpdateRoleData {

	public String username;
	public String usernameToChange;
	public long role;

	
	public UpdateRoleData(){
		
	}
	
	public UpdateRoleData(String username, String usernameToChange, Long role){
		
		
		this.username = username;
		this.usernameToChange = usernameToChange;
		this.role = role;

	}

}
