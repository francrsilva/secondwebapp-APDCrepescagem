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

import secondwebapp.util.UpdateLotData;


@Path("/Update")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UpdateLotResource {


	
	private static final Logger LOG = Logger.getLogger(UpdateLotResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();

	public UpdateLotResource() {
			
	}
	
	@POST
	@Path("/lot/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doLotChange(UpdateLotData data) {
		LOG.fine("Change lot status attempt by user: " + data.username);

		Transaction txn = datastore.newTransaction();
		try {
			Key TokenKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
					.newKey(data.username);
			Entity userlog = txn.get(TokenKey);
			
			Key userKey1 = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity user = txn.get(userKey1);
			
			
			//nao usado. pensar em tira lo daqui e das classes lotData e updateLotData
			Key userKey2 = datastore.newKeyFactory().setKind("User").newKey(data.registerUsername);
			Entity userRegisterLot = txn.get(userKey2);
			
			
			Key lotKey = datastore.newKeyFactory().setKind("Lot").newKey(data.idLot);
			Entity lot = txn.get(lotKey);

			if (userlog != null && userlog.getLong("expiration_time") > System.currentTimeMillis()) {
				if(user.getLong("Role") >= 10L) {
					
					String idLot = lot.getString("idLot");
					boolean rustico = lot.getBoolean("rustico");
					String nameOwner = lot.getString("nameOwner");
					String nacionalidade = lot.getString("nacionalidade");
					String tipoDoc = lot.getString("tipoDoc");
					String dataDoc = lot.getString("dataDoc");
					String NIF = lot.getString("NIF");
					boolean verificado = lot.getBoolean("verificado");
					long upRight = lot.getLong("upRight");
					long downLeft = lot.getLong("downLeft");
					
					lot = Entity.newBuilder(lotKey)
							.set("idLot",idLot)
							.set("rustico", rustico)
							.set("nameOwner", nameOwner)
							.set("nacionalidade", nacionalidade)
							.set("tipoDoc", tipoDoc)
							.set("dataDoc", dataDoc)
							.set("NIF", NIF)
							.set("verificado", data.verificado)
							.set("upRight", upRight)
							.set("downLeft", downLeft)
							.build();
					
				}else {
					txn.rollback();
					LOG.warning("User: " + data.username + " don't have permission");
					return Response.status(Status.BAD_REQUEST).entity("User without permission!").build();
					
				}
				
			} else {
				txn.rollback();
				LOG.warning("User: " + data.username + " not logged");
				return Response.status(Status.BAD_REQUEST).entity("User not logged").build();

			}
			
			txn.put(lot);
			txn.commit();
			LOG.info("Lot status changed sucessfully.");
			return Response.ok("Lot status changed").build();
		
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
}
