package secondwebapp.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import secondwebapp.util.AuthToken;
import secondwebapp.util.LotData;

@Path("/registerLot")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegisterLotResource {

	private static final Logger LOG = Logger.getLogger(RegisterLotResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	private final Gson g = new Gson();
	
	public RegisterLotResource() {
		
	}
	
	@POST
	@Path("/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doRegisterLotResource(LotData data) {
		LOG.fine("Register lot attempt by user: " + data.username);

		Transaction txn = datastore.newTransaction();
		try{
			//user que ta a meker
		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
		Entity user = txn.get(userKey);
			
			//token de quem ta login a mexer
		Key TokenKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
				.newKey(data.username);
		Entity userlog = txn.get(TokenKey);
		
		//ver quanto a isto
		Key lotKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Lot")
				.newKey(data.idLot);
		
		Entity lot = txn.get(lotKey);
				
				if (userlog != null && userlog.getLong("expiration_time") > System.currentTimeMillis()) {
					
					if(user.getLong("Role") == 0L) {
						//criar lot nao verificado
						lot = Entity.newBuilder(lotKey)
								.set("registerUsername", userlog.getString("username"))	//pode ser usado user.getString ("username");
								.set("rustico", data.rustico)
								.set("nameOwner", data.nameOwner)
								.set("nacionalidade", data.nacionalidade)
								.set("tipoDoc", data.tipoDoc)
								.set("dataDoc", data.dataDoc)
								.set("NIF", data.NIF)
								.set("verificado", false)			//na verdade e sempre inicializado a false 
								.set("upRightLat", data.upRightLat)
								.set("upRightLong", data.upRightLong)
								.set("downLeftLat", data.downLeftLat)
								.set("downLeftLong", data.downLeftLong)
								.build();
						
						txn.add(lot);
						LOG.info("Lot registered " + data.idLot);
						txn.commit();
						return Response.ok("{ok}").build();
					}else {
						
						lot = Entity.newBuilder(lotKey)
								.set("rustico", data.rustico)
								.set("nameOwner", data.nameOwner)
								.set("nacionalidade", data.nacionalidade)
								.set("tipoDoc", data.tipoDoc)
								.set("dataDoc", data.dataDoc)
								.set("NIF", data.NIF)
								.set("verificado", data.verificado)
								.set("upRightLat", data.upRightLat)
								.set("upRightLong", data.upRightLong)
								.set("downLeftLat", data.downLeftLat)
								.set("downLeftLong", data.downLeftLong)
								.build();
						
						txn.add(lot);
						LOG.info("Lot registered " + data.idLot);
						txn.commit();
						return Response.ok("{ok}").build();
					}
				} else {
					txn.rollback();
					LOG.warning("User: " + data.username + " not logged");
					return Response.status(Status.BAD_REQUEST).entity("User not logged").build();
				}
		
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}

		}
	}
}
