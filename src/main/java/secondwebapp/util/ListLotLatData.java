package secondwebapp.util;

public class ListLotLatData {

	public long upRightLat;
	public long downLeftLat;
	public AuthToken token;
	

	public ListLotLatData() {
		
	}

	public ListLotLatData(long upRightLat, long downLeftLat, AuthToken token) {
		this.upRightLat = upRightLat;
		this.downLeftLat = downLeftLat;
		this.token = token;
	}
	
/*
	public ListLotLatData(long upRightLat, long upRightLong, long downLeftLat, long downLeftLong) {
		this.upRightLat = upRightLat;
		this.upRightLong = upRightLong;
		this.downLeftLat = downLeftLat;
		this.downLeftLong = downLeftLong;
	}
	
*/	
	
	
}
