package secondwebapp.util;

public class LotData {

	//devo conseguir obter a partir do token
	//public String registerUsername;
	
	public Integer idLot;
	public boolean rustico;
	public String nameOwner;
	public String nacionalidade;
	public String tipoDoc;
	public String dataDoc;
	public String NIF;
	public boolean verificado;
	public Double upRightLat;
	public Double upRightLong;
	public Double downLeftLat;
	public Double downLeftLong;
	
	public String username;
	
	//podia meter aqui o role como e feito em AuthToken mas ia dar muito trabalho nas outras classes
	
	public LotData() {
		
	}



	public LotData(String idLot, Boolean rustico, String nameOwner, String nacionalidade, String tipoDoc,
			String dataDoc, String NIF, String upRightLat, String upRightLong, String downLeftLat,
			String downLeftLong, String username) {
		
		this.idLot = Integer.parseInt(idLot);
		this.rustico = rustico;
		this.nameOwner = nameOwner;
		this.nacionalidade = nacionalidade;
		this.tipoDoc = tipoDoc;
		this.dataDoc = dataDoc;
		this.NIF = NIF;
		this.upRightLat = Double.parseDouble(username);
		this.upRightLong = Double.parseDouble(upRightLong);
		this.downLeftLat = Double.parseDouble(downLeftLat);
		this.downLeftLong = Double.parseDouble(downLeftLong);
		this.verificado = false;
		this.username = username;
		
	}


/*
	public LotData(//String registerUsername,
			boolean rustico, String nameOwner, String nacionalidade, String tipoDoc, String dataDoc, String NIF,
			boolean verificado, long upRightLat, long upRightLong, long downLeftLat, long downLeftLong, String idLot ) {
		
		//this.registerUsername=; acho que nao preciso disto aqui uma vez que nao quero mudar mas quero aceder
		
		this.idLot = idLot;
		this.rustico = rustico;
		this.nameOwner = nameOwner;
		this.nacionalidade = nacionalidade;
		this.tipoDoc = tipoDoc;
		this.dataDoc = dataDoc;
		this.NIF = NIF;
		this.verificado = verificado;
		this.upRightLat = upRightLat;
		this.upRightLong = upRightLong;
		this.downLeftLat = downLeftLat;
		this.downLeftLong = downLeftLong;
	}
*/	
	
}
