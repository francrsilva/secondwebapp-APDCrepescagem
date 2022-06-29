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
	public Response listUsers(AuthToken token) {
		LOG.fine("Attempt list users by user: " + token.username);
		String username = token.username;
		
		if(username == null || username.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).entity("Invalid username").build();
		}
		
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(token.username);
		
		//nao tenho a certeza se aqui e setKind "Token" ou "AuthToken"
		//tambem nao sei se e token.username ou token.tokenID
		//talvez ate criar uma classe de data
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", token.username))
				.setKind("Token")
				.newKey(token.username);
		
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
			}else if(tokenEntity.getLong("expirationDate") < System.currentTimeMillis()) {
				return Response.status(Status.FORBIDDEN).entity("Token expired").build();
			}
			
			if(user.getLong("Role") == 0L) {
				// USER
								
				ProjectionEntityQuery query = Query.newProjectionEntityQueryBuilder()
				        .setKind("User")
				        .setFilter(
				        		CompositeFilter.and(
				        				PropertyFilter.eq("Role", 0L),
				        				PropertyFilter.eq("Status", ATIVO),
				        				PropertyFilter.eq("perfil", PUBLICO)
				        		))
				        .setProjection("email", "name")
				        .build();
				
				//TODO use cursor
				
				QueryResults<ProjectionEntity> result = datastore.run(query);
				List<DisplayUser> users = new ArrayList<>();
				
				result.forEachRemaining(displayUser -> {
					users.add(new DisplayUser(displayUser.getKey().getName(), displayUser.getString("email"), displayUser.getString("name")));
				});
				
				return Response.ok(g.toJson(users)).build();
			}else {
				EntityQuery.Builder builder = Query.newEntityQueryBuilder()
						.setKind("User");
						
				if(user.getLong("Role") < 30L){	
					// Not superuser
					builder.setFilter(PropertyFilter.lt("Role", user.getLong("Role")));
				}
				
				EntityQuery query = builder.build();
				
				QueryResults<Entity> result = datastore.run(query);
				List<UserInfo> users = new ArrayList<>();
				result.forEachRemaining(userInfo -> {
					users.add(new UserInfo(
							userInfo.getKey().getName(), 
							userInfo.getString("email"),
							userInfo.getString("name"),
							userInfo.getString("perfil"),
							userInfo.getString("tel_fixo"),
							userInfo.getString("telmv"), 
							userInfo.getString("address1"),
							userInfo.getString("address2"),
							userInfo.getString("locality"),
							userInfo.getString("NIF"),
							userInfo.getString("Status"),
							userInfo.getLong("Role")
							));
				});
				
				return Response.ok(g.toJson(users)).build();
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
