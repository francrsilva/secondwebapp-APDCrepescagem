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
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;


import secondwebapp.util.AuthToken;
import secondwebapp.util.ListLotLatData;
import secondwebapp.util.ListLotLongData;
import secondwebapp.util.LotData;
import secondwebapp.util.LotDataObj;

@Path("/listlot")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ListLotResource {

	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private static final Logger LOG = Logger.getLogger(ListLotResource.class.getName());
	
	private final Gson g = new Gson();
	
	@POST
	@Path("/lat")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listLotsLat(ListLotLatData data) {
		LOG.fine("List Lat Lots attempt by user: " + data.username);
				
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(data.username);
		
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", data.username))
				.setKind("Token")
				.newKey(data.username);
		
		Transaction txn = datastore.newTransaction();
		try {
			Entity tokenEntity = txn.get(tokenKey);
			txn.commit();
			
			Entity user = txn.get(userKey);
			
			if(user == null) {
				return Response.status(Status.NOT_FOUND).entity("User does not exist").build();
			}
			
			if(tokenEntity == null) {
				return Response.status(Status.NOT_FOUND).entity("AuthToken doesn't exist").build();
			}else if(tokenEntity.getLong("expiration_time") < System.currentTimeMillis()) {
				return Response.status(Status.FORBIDDEN).entity("AuthToken expired").build();
			}
			
				
			//
			Query<Entity> query = Query.newEntityQueryBuilder().setKind("Lot")
					.setFilter(CompositeFilter.and(PropertyFilter.le("upRightLat", data.upRightLat),
		    				PropertyFilter.ge("downLeftLat", data.downLeftLat)))
					.build();

			QueryResults<Entity> lotsR = datastore.run(query);

			List<String> listLots = new ArrayList();
			
			lotsR.forEachRemaining(lots -> {
				listLots.add(lots.getKey().getName() + " " + lots.getBoolean("rustico") + " "
						+ lots.getString("nameOwner") + " "+ lots.getString("nacionalidade")+" "+lots.getString("tipoDoc")
						+" "+lots.getString("dataDoc")+" "+lots.getString("NIF")+" "+lots.getBoolean("verificado")+" "
						+" "+lots.getDouble("upRightLat")+" "+lots.getDouble("upRightLong")+" "+lots.getDouble("downLeftLat")
						+" "+lots.getDouble("downLeftLong")+ "/n");
			});
			return Response.ok(g.toJson(listLots)).build();
			
			//EntityQuery.Builder query = Query.newEntityQueryBuilder()
		/*		ProjectionEntityQuery query = Query.newProjectionEntityQueryBuilder()
				        .setKind("Lot")
				        .setFilter(
				        		CompositeFilter.and(
				        				PropertyFilter.le("upRightLat", data.upRightLat),
				        				PropertyFilter.ge("downLeftLat", data.downLeftLat)
				        		))
				        .build();
			*/
		}catch(Exception e) {
			if(txn.isActive()) {
				txn.rollback();
			}
			LOG.warning(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}

		}

	}
	
	
	
	
	
	@POST
	@Path("/long")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listLotsLong(ListLotLongData data) {
		LOG.fine("List Lat Lots attempt by user: " + data.username);
		
		Key userKey = datastore.newKeyFactory()
				.setKind("User")
				.newKey(data.username);
		
		Key tokenKey = datastore.newKeyFactory()
				.addAncestor(PathElement.of("User", data.username))
				.setKind("Token")
				.newKey(data.username);
		
		Transaction txn = datastore.newTransaction();
		try {
			Entity tokenEntity = txn.get(tokenKey);
			txn.commit();
			
			Entity user = txn.get(userKey);
			
			if(user == null) {
				return Response.status(Status.NOT_FOUND).entity("User does not exist").build();
			}
			
			if(tokenEntity == null) {
				return Response.status(Status.NOT_FOUND).entity("AuthToken doesn't exist").build();
			}else if(tokenEntity.getLong("expiration_time") < System.currentTimeMillis()) {
				return Response.status(Status.FORBIDDEN).entity("AuthToken expired").build();
			}
			
			
			//
			Query<Entity> query = Query.newEntityQueryBuilder().setKind("Lot")
					.setFilter(CompositeFilter.and(PropertyFilter.le("upRightLong", data.upRightLong),
		    				PropertyFilter.ge("downLeftLong", data.downLeftLong)))
					.build();

			QueryResults<Entity> lotsR = datastore.run(query);

			List<String> listLots = new ArrayList();
			
			lotsR.forEachRemaining(lots -> {
				listLots.add(lots.getKey().getName() + " " + lots.getBoolean("rustico") + " "
						+ lots.getString("nameOwner") + " "+ lots.getString("nacionalidade")+" "+lots.getString("tipoDoc")
						+" "+lots.getString("dataDoc")+" "+lots.getString("NIF")+" "+lots.getBoolean("verificado")+" "
						+" "+lots.getDouble("upRightLat")+" "+lots.getDouble("upRightLong")+" "+lots.getDouble("downLeftLat")
						+" "+lots.getDouble("downLeftLong")+ "/n");
			});
			return Response.ok(g.toJson(listLots)).build();
			//
		
			//EntityQuery.Builder query = Query.newEntityQueryBuilder()
		/*		ProjectionEntityQuery query = Query.newProjectionEntityQueryBuilder()
				        .setKind("Lot")
				        .setFilter(
				        		CompositeFilter.and(
				        				PropertyFilter.le("upRightLat", data.upRightLat),
				        				PropertyFilter.ge("downLeftLat", data.downLeftLat)
				        		))
				        .build();
			*/
		}catch(Exception e) {
			if(txn.isActive()) {
				txn.rollback();
			}
			LOG.warning(e.getMessage());
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}

		}
	}
}
