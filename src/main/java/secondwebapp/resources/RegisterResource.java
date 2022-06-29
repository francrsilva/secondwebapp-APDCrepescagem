package secondwebapp.resources;

import java.util.logging.Logger;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import secondwebapp.util.RegisterData;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;

@Path("/Register")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegisterResource {

	/*
	 * A Logger object
	 */
	private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());
	private static final String INDEFINIDO = "UNDEFINED";
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

	public RegisterResource() {
	}

	

	@POST
	@Path("/v3")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doRegistartion3(RegisterData data) {
		LOG.fine("Attempt to register user: " + data.username);

		if (!data.validRegistration())
			return Response.status(Status.BAD_REQUEST).entity("Missing or wrong parameter").build();

		Transaction txn = datastore.newTransaction();
		try {
			Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity user = txn.get(userKey);
			if (user != null) {
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("User already exists. ").build();
			}
			if(!data.validPassword()) {
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("Your password must have, at least, one numeric character, one lowercase, one uppercase character, a special symbol and a length between 7 and 16. ").build();

			}
			if(!data.password.equals(data.confirmation)) {
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity("Passwords not match").build();
				
			}
			if(!data.validEmail()) {
				txn.rollback();
				return Response.status(Status.BAD_REQUEST).entity(" Email invalid.").build();
				
			}
			
			else {
				
			
				user = Entity.newBuilder(userKey)
						.set("user_pwd", DigestUtils.sha512Hex(data.password))
						.set("email", data.email)
						.set("name", data.name)
						.set("perfil", data.perfil )
						.set("tel_fixo",data.tel_fixo.equals("") ? INDEFINIDO:data.tel_fixo)
						.set("telmv", data.telmv.equals("") ? INDEFINIDO:data.telmv)
						.set("NIF",data.NIF.equals("") ? INDEFINIDO:data.NIF)
						.set("address1",data.address1.equals("") ? INDEFINIDO:data.address1)
						.set("address2",data.address2.equals("") ? INDEFINIDO:data.address2)
						.set("locality",data.locality.equals("") ? INDEFINIDO:data.locality)
						.set("Role", 0)
						.set("Status", "INATIVO")
						.build();
			

				txn.add(user);
				LOG.info("User registered " + data.username);
				txn.commit();
				return Response.ok("{ok}").build();
			}
		} finally {
			if(txn.isActive()) {
				txn.rollback();
			}

		}

	}

}
