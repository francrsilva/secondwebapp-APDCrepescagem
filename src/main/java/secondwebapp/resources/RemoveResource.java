package secondwebapp.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

@Path("/delete")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RemoveResource {
	
private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private static final Logger LOG = Logger.getLogger(RemoveResource.class.getName());
	
	private final Gson g = new Gson();
	
	@POST
	@Path("/{userId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("userId") String userId, AuthToken token) {
		String username = token.username;
		
		if(username == null || username.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid username").build();
		}
		
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(token.username);
		
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", token.username))
				.setKind("Token")
				.newKey(token.username);
		
		Key deleteKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(userId);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			Entity user = txn.get(userKey);
			
			if(user == null) {
				return Response.status(Status.NOT_FOUND).entity("User does not exist").build();
			}
						
			Entity tokenEntity = txn.get(tokenKey);
			
			if(tokenEntity == null) {
				return Response.status(Status.NOT_FOUND).entity("Token doesn't exist").build();
			}else if(tokenEntity.getLong("expirationDate") < System.currentTimeMillis()) {
				return Response.status(Status.FORBIDDEN).entity("Token expired").build();
			}
			
			Entity deleteEntity = txn.get(deleteKey);
			
			txn.commit();
			
			if(user.getLong("Role") <= deleteEntity.getLong("Role") && user.getLong("Role") != 30) {
				return Response.status(Status.FORBIDDEN).entity("Role level not high enough").build();
			}
			
			datastore.delete(deleteKey);
			
			return Response.ok().build();
			
		}catch(Exception e) {
			if(txn.isActive()) {
				txn.rollback();
			}
			LOG.warning(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}finally {
			if(txn.isActive()) {
				txn.rollback();
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			}
		}	
	}
}
