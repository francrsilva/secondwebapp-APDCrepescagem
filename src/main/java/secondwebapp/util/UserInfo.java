package secondwebapp.util;

public class UserInfo {
	public String username;
	public String email;
	public String name;
	public String perfil;
	public String tel_fixo;
	public String telmv;
	public String address1;
	public String address2;
	public String locality;
	public String NIF;
	public String Status;
	private long role;
	
	public UserInfo(
		String username,
		String email,
		String name,
		String perfil,
		String tel_fixo,
		String telmv,
		String address1,
		String address2,
		String locality,
		String NIF,
		String Status,
		long role
			) {
		this.username = username;
		this.email = email;
		this.name = name;
		this.perfil = perfil;
		this.tel_fixo = tel_fixo;
		this.telmv = telmv;
		this.address1 = address1;
		this.address2 = address2;
		this.locality = locality;
		this.NIF = NIF;
		this.Status = Status;
		this.role =  role;

	}
	
}
