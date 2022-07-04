package secondwebapp.resources;

import java.util.ArrayList;
import java.util.List;
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
import com.google.cloud.datastore.EntityQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.ProjectionEntity;
import com.google.cloud.datastore.ProjectionEntityQuery;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;

//import secondwebapp.resources.UserResource;
import secondwebapp.util.AuthToken;
import secondwebapp.util.DisplayUser;
import secondwebapp.util.LogoutData;
import secondwebapp.util.UserInfo;

@Path("/List")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ListResource {

	private static final String ATIVO = "ATIVO";
	private static final String PUBLICO = "Publico";
	
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private static final Logger LOG = Logger.getLogger(ListResource.class.getName());
	
	private final Gson g = new Gson();
	
	@POST
	@Path("/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listUsers(LogoutData data) {
		LOG.fine("Attempt list users by user: " + data.username);
		String username = data.username;
		
		if(username == null || username.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid username").build();
		}
		
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(data.username);
		
		//nao tenho a certeza se aqui e setKind "Token" ou "AuthToken"
		//tambem nao sei se e token.username ou token.tokenID
		//talvez ate criar uma classe de data
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", data.username))
				.setKind("Token")
				.newKey(data.username);
		
		/*
		 * Key TokenKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
				.newKey(data.username);
		
		*/
		
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
				return Response.status(Status.FORBIDDEN).entity("Token expired").build();
			}
			
			if(user.getLong("Role") == 0L) {
				// USER
				
			
				Query<Entity> query = Query.newEntityQueryBuilder().setKind("User")
						.setFilter(CompositeFilter.and(PropertyFilter.eq("Role", 0L),
								PropertyFilter.eq("Status", ATIVO), PropertyFilter.eq("perfil", PUBLICO)))
						.build();

				QueryResults<Entity> usersR = datastore.run(query);

				List<String> listUsers = new ArrayList();
				
				usersR.forEachRemaining(users -> {
					listUsers.add(users.getKey().getName() + " " + users.getKey().getName() + " "
							+ users.getString("email") + " ");
				});
				return Response.ok(g.toJson(listUsers)).build();
				
				
			}else {
				Query<Entity> query;
				if(user.getLong("Role") < 30L){	
					 query = Query.newEntityQueryBuilder().setKind("User")
							.setFilter(PropertyFilter.lt("Role", user.getLong("Role"))).build();
				}else {
					query = Query.newEntityQueryBuilder().setKind("User")
							.build();
				}
				QueryResults<Entity> usersR = datastore.run(query);

				List<String> listUsers = new ArrayList();
				usersR.forEachRemaining(users -> {
					listUsers.add(users.getLong("Role") + " " + users.getKey().getName() + " "
							+ users.getString("name") + " " + users.getString("email") + " " + users.getString("perfil")
							+ " " + users.getString("tel_fixo") + " " + users.getString("telmv") + " "
							+ users.getString("NIF") + " " + users.getString("Status") + "\n ");
				});
				return Response.ok(g.toJson(listUsers)).build();
				
			}

			
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
