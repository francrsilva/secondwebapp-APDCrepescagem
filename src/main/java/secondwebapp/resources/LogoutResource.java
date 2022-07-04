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

import secondwebapp.util.AuthToken;
import secondwebapp.util.RemoveData;

@Path("/logout")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LogoutResource {

private static final Logger LOG = Logger.getLogger(LogoutResource.class.getName());
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
		
	public LogoutResource() { }
	
	@POST
	@Path("/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout(RemoveData data) {
		String username = data.username;
		
		if(username == null || username.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid username").build();
		}
		
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(data.username);
		
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", data.username))
				.setKind("Token")
				.newKey(data.username);
		
		Transaction txn = datastore.newTransaction();
		
		try {
			Entity user = txn.get(userKey);
			
			if(user == null) {
				return Response.status(Status.NOT_FOUND).entity("User does not exist").build();
			}
						
			Entity tokenEntity = txn.get(tokenKey);
			txn.commit();
			
			if(tokenEntity == null) {
				return Response.status(Status.NOT_FOUND).entity("Token doesn't exist").build();
			}else if(tokenEntity.getLong("expiration_time") < System.currentTimeMillis()) {
				return Response.status(Status.FORBIDDEN).entity("User already logged out (Token expired)").build();
			}
			
			datastore.delete(tokenKey);			
			
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
