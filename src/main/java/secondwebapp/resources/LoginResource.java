package secondwebapp.resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
//import com.sun.tools.javac.util.List;
import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;

import secondwebapp.util.AuthToken;
import secondwebapp.util.LoginData;
//import sun.util.calendar.BaseCalendar.Date;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LoginResource {

	/*
	 * A Logger object
	 */
	private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	private final Gson g = new Gson();

	public LoginResource() {
	}





	@POST
	@Path("/v1")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response doLogin1(LoginData data) {
		LOG.fine("Login attempt by user: " + data.username);

		Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
		
		Entity user = datastore.get(userKey);
		if (user != null) {

			Value<String> password = user.getValue("user_pwd");
			if (DigestUtils.sha512Hex(data.password).equals(password.get())) {
				AuthToken at = new AuthToken(data.username, user.getLong("Role"));
				Key tokKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
                        .newKey(at.username);
                Entity token = Entity.newBuilder(tokKey)
                		.set("tokenID", at.tokenID)
                		.set("Creation_time", Timestamp.now())
                		.set("expiration_time", at.expirationData)
                        .build();
                datastore.put(token);
				LOG.info("User " + data.username + " logged in sucessfully.");
				return Response.ok(g.toJson(at)).build();

			} else {
				LOG.warning("Wrong password for username: " + data.username);
				return Response.status(Status.FORBIDDEN).entity("Wrong password").build();
			}

		} else {
			LOG.warning("Failed login attempt for username: " + data.username);
			return Response.status(Status.FORBIDDEN).entity("Failed login attempt").build();

		}
	}
	

}
