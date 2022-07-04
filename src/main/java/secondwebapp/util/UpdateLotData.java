package secondwebapp.util;

public class UpdateLotData {

	public String username;
	
	public int idLot;
	
	public boolean verificado;
	
	public UpdateLotData() {
		
	}

	public UpdateLotData(String username, String idLot,  boolean verificado) {
		this.username = username;
		
		this.idLot = Integer.parseInt(idLot);
		this.verificado = verificado;
	}
	
	
}
