package secondwebapp.util;

public class UpdateLotData {

	public String username;
	
	public String idLot;
	public String registerUsername;
	public boolean verificado;
	
	public UpdateLotData() {
		
	}

	public UpdateLotData(String username, String idLot, String registerUsername, boolean verificado) {
		this.username = username;
		
		this.idLot = idLot;
		this.registerUsername = registerUsername;
		this.verificado = verificado;
	}
	
	
}
