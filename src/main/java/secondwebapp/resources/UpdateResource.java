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

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.PathElement;
import com.google.cloud.datastore.Transaction;
import com.google.gson.Gson;

import secondwebapp.util.ChangePasswordData;
import secondwebapp.util.RegisterData;
import secondwebapp.util.UpdateData;
import secondwebapp.util.UpdateLotData;
import secondwebapp.util.UpdateRoleData;
import secondwebapp.util.UpdateStatusData;

@Path("/Change")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UpdateResource {

	
	
	private static final Logger LOG = Logger.getLogger(UpdateResource.class.getName());
	private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
	
	private final Gson g = new Gson();

	public UpdateResource() {
			
	}

	
	@POST
	@Path("/Password")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doChangePassword(ChangePasswordData data) {
		LOG.fine("Change password attempt by user: " + data.username);

		Transaction txn = datastore.newTransaction();
		try {
			Key TokenKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
					.newKey(data.username);
			Entity userlog = txn.get(TokenKey);

			Key userKey2 = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity user = txn.get(userKey2);

			if (userlog != null && userlog.getLong("expiration_time") > System.currentTimeMillis()) {
				if (!data.validPassword() || !data.validUpdate()) {
					txn.rollback();
					return Response.status(Status.BAD_REQUEST).entity(
							"Your password must have, at least, one numeric character, one lowercase, one uppercase character, a special symbol anda length between 7 and 16. ")
							.build();
				}
				if (!data.newpassword.equals(data.confirmation)) {
					txn.rollback();
					return Response.status(Status.BAD_REQUEST).entity("Passwords not match").build();

				}
				String email = user.getString("email");
				String name = user.getString("name");
				String perfil = user.getString("perfil");
				String tel_fixo = user.getString("tel_fixo");
				String telmv = user.getString("telmv");
				String address1 = user.getString("address1");
				String address2 =user.getString("address2");;
				String locality=user.getString("locality");
				String NIF = user.getString("NIF");
				Long Role = user.getLong("Role");

				String Status = user.getString("Status");

				user = Entity.newBuilder(userKey2)
						.set("user_pwd", DigestUtils.sha512Hex(data.newpassword))
						.set("email", email)
						.set("name", name)
						.set("perfil", perfil)
						.set("tel_fixo", tel_fixo)
						.set("telmv", telmv)
						.set("NIF", NIF)
						.set("address1", address1)
						.set("address2", address2)
						.set("locality", locality)
						.set("Role", Role)
						.set("Status", Status)
						.build();
				
				txn.put(user);
				txn.commit();
				LOG.info(" Password changed sucessfully.");
				return Response.ok("Password changed").build();

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
	

	@POST
	@Path("/attributes/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doChangeAttributes(UpdateData data) {
		LOG.fine("Change attributes attempt by user: " + data.username);

		Transaction txn = datastore.newTransaction();
		try {
			Key TokenKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
					.newKey(data.username);
			Entity userlog = txn.get(TokenKey);
			Key userKey1 = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity user = txn.get(userKey1);


			Key userKey2 = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);
			Entity userToChange = txn.get(userKey2);

			if (userlog != null && userlog.getLong("expiration_time") > System.currentTimeMillis()) {
				
				 
					
					if(user.getLong("Role") == 0L && data.username.equals(data.usernameToChange)) {
						
						String email = userToChange.getString("email");
						String name = userToChange.getString("name");
						String user_pwd = userToChange.getString("user_pwd");

						
						//data.perfil.equals("PUBLICO") || data.perfil.equals("PRIVADO") ? data.perfil : perfil)
						String perfil = userToChange.getString("perfil");
						String tel_fixo = userToChange.getString("tel_fixo");
						String telmv = userToChange.getString("telmv");
						String NIF = userToChange.getString("NIF");
						String address1 = userToChange.getString("address1");
						String address2 = userToChange.getString("address2");
						String locality = userToChange.getString("locality");

						Long Role = userToChange.getLong("Role");

						String Status = userToChange.getString("Status");
						
						userToChange=Entity.newBuilder(userKey2)
								.set("email", email)
								.set("name", name)
								.set("user_pwd", user_pwd)
								.set("perfil", data.perfil.equals("PUBLICO") || data.perfil.equals("PRIVADO") ? data.perfil : perfil)
								.set("tel_fixo",data.tel_fixo.equals("") ? tel_fixo:data.tel_fixo)
								.set("telmv", data.telmv.equals("") ? telmv:data.telmv)
								.set("NIF",data.NIF.equals("") ? NIF:data.NIF)
								.set("address1",data.address1.equals("") ? address1:data.address1)
								.set("address2",data.address2.equals("") ? address2:data.address2)
								.set("locality",data.locality.equals("") ? locality:data.locality)
								.set("Role", Role)
								.set("Status", Status)
								.build();
						

						
						
					}else if(user.getLong("Role") > userToChange.getLong("Role") || (data.username.equals(data.usernameToChange) && (user.getLong("Role")>=10L) )) {
					
						String email = userToChange.getString("email");
						String name = userToChange.getString("name");
						String user_pwd = userToChange.getString("user_pwd");
	
						String perfil = userToChange.getString("perfil");
						String tel_fixo = userToChange.getString("tel_fixo");
						String telmv = userToChange.getString("telmv");
						String NIF = userToChange.getString("NIF");
						Long Role = userToChange.getLong("Role");
						String address1 = userToChange.getString("address1");
						String address2 = userToChange.getString("address2");
						String locality = userToChange.getString("locality");
	
						String Status = userToChange.getString("Status");
						
						userToChange = Entity.newBuilder(userKey2)
								.set("user_pwd", user_pwd)
								.set("email", data.email.equals("") ? email : data.email)
								.set("name", data.name.equals("") ? name : data.name)
								.set("perfil", data.perfil.equals("PUBLICO") || data.perfil.equals("PRIVADO") ? data.perfil : perfil)
								.set("tel_fixo",data.tel_fixo.equals("") ? tel_fixo : data.tel_fixo)
								.set("telmv", data.telmv.equals("") ? telmv : data.telmv)
								.set("NIF",data.NIF.equals("") ? NIF : data.NIF)
								.set("address1",data.address1.equals("") ? address1:data.address1)
								.set("address2",data.address2.equals("") ? address2:data.address2)
								.set("locality",data.locality.equals("") ? locality:data.locality)
								.set("Role", Role)
								.set("Status", Status)
								.build();
					
					
					}else {
						txn.rollback();
						LOG.warning("User: " + data.username + " don't have permission");
						return Response.status(Status.BAD_REQUEST).entity("User without permission!").build();
						
					}
				
				
				

				txn.put(userToChange);
				txn.commit();
				LOG.info(" Attributes changed sucessfully.");
				return Response.ok("Attributes changed").build();

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
	

	@POST
	@Path("/role/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doChangeRoles(UpdateRoleData data) {
		LOG.fine("Change role attempt by user: " + data.username);

		Transaction txn = datastore.newTransaction();
		try {
			Key TokenKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
					.newKey(data.username);
			Entity userlog = txn.get(TokenKey);
			
			Key userKey1 = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity user = txn.get(userKey1);


			Key userKey2 = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);
			Entity userToChange = txn.get(userKey2);

			if (userlog != null && userlog.getLong("expiration_time") > System.currentTimeMillis()) {
				
				String email = userToChange.getString("email");
				String name = userToChange.getString("name");
				String user_pwd = userToChange.getString("user_pwd");
				String perfil = userToChange.getString("perfil");
				String tel_fixo = userToChange.getString("tel_fixo");
				String telmv = userToChange.getString("telmv");
				String NIF = userToChange.getString("NIF");
				String address1 = userToChange.getString("address1");
				String address2 = userToChange.getString("address2");
				String locality = userToChange.getString("locality");
				Long Role = userToChange.getLong("Role");

				String status = userToChange.getString("Status");
					
					if(user.getLong("Role") == 30L) {
						
					
						userToChange=Entity.newBuilder(userKey2)
								
								.set("user_pwd", user_pwd)
								.set("email", email)
								.set("name", name)
								.set("perfil",perfil)
								.set("tel_fixo",tel_fixo)
								.set("telmv", telmv)
								.set("NIF", NIF)
								.set("address1", address1)
								.set("address2", address2)
								.set("locality", locality)
								.set("Role", data.role)
								.set("Status", status)
								.build();
						

						
						
					}else if(user.getLong("Role") == 20L && Role == 0L && data.role == 10L) {
						
						userToChange = Entity.newBuilder(userKey2)

								.set("user_pwd", user_pwd)
								.set("email", email)
								.set("name", name)
								.set("perfil", perfil)
								.set("tel_fixo", tel_fixo)
								.set("telmv", telmv)
								.set("NIF", NIF)
								.set("address1", address1)
								.set("address2", address2)
								.set("locality", locality)
								.set("Role", data.role)
								.set("Status", status)
								.build();
					
						
						
						
						
						
					
				
					
					}else {
						txn.rollback();
						LOG.warning("User: " + data.username + " don't have permission");
						return Response.status(Status.BAD_REQUEST).entity("User without permission!").build();
						
					}
				
				
				

				txn.put(userToChange);
				txn.commit();
				LOG.info(" Role changed sucessfully.");
				return Response.ok("Role changed").build();

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
	
	
	@POST
	@Path("/status/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response doChangeStatus(UpdateStatusData data) {
		LOG.fine("Change status attempt by user: " + data.username);

		Transaction txn = datastore.newTransaction();
		try {
			Key TokenKey = datastore.newKeyFactory().addAncestor(PathElement.of("User", data.username)).setKind("Token")
					.newKey(data.username);
			Entity userlog = txn.get(TokenKey);
			
			Key userKey1 = datastore.newKeyFactory().setKind("User").newKey(data.username);
			Entity user = txn.get(userKey1);


			Key userKey2 = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);
			Entity userToChange = txn.get(userKey2);

			if (userlog != null && userlog.getLong("expiration_time") > System.currentTimeMillis()) {
				
				String email = userToChange.getString("email");
				String name = userToChange.getString("name");
				String user_pwd = userToChange.getString("user_pwd");
				String perfil = userToChange.getString("perfil");
				String tel_fixo = userToChange.getString("tel_fixo");
				String telmv = userToChange.getString("telmv");
				String NIF = userToChange.getString("NIF");
				String address1 = userToChange.getString("address1");
				String address2 = userToChange.getString("address2");
				String locality = userToChange.getString("locality");
				Long Role = userToChange.getLong("Role");

				String status = userToChange.getString("Status");
				
				
				//nao faz parte dos de cima
				//pode se fazer a mesma coisa para o perfil "PUBLICO" ou "PRIVADO"
				//String newStatus = data.status.equals("ATIVO") || data.status.equals("INATIVO") ? data.status :status;
					
					if(user.getLong("Role") == 30L) {
						
						userToChange=Entity.newBuilder(userKey2)
								.set("user_pwd", user_pwd)
								.set("email", email)
								.set("name", name)
								.set("perfil",perfil)
								.set("tel_fixo",tel_fixo)
								.set("telmv", telmv)
								.set("NIF", NIF)
								.set("address1", address1)
								.set("address2", address2)
								.set("locality", locality)
								.set("Role", Role)
								.set("Status", data.status.equals("ATIVO") || data.status.equals("INATIVO") ? data.status :status)
								.build();
						

						
						
					}else if(user.getLong("Role") == 20L  && (user.getLong("Role")>Role) ||
							(data.username.equals(data.usernameToChange) && user.getLong("Role") == 20L) ) {
					
						userToChange=Entity.newBuilder(userKey2)
								.set("Status",data.status.equals("ATIVO") || data.status.equals("INATIVO") ? data.status :status)
								.build();
				
						//valera a pena o ultimo user.getLong("Role") == 10L , sendo que ja ta a ser testado
					}else if(user.getLong("Role") == 10L && (user.getLong("Role")>Role) ||
							(data.username.equals(data.usernameToChange) && user.getLong("Role") == 10L) ) {
					
						userToChange=Entity.newBuilder(userKey2)
								.set("Status", data.status.equals("ATIVO") || data.status.equals("INATIVO") ? data.status :status)
								.build();
				
					
					}else if(data.username.equals(data.usernameToChange) && user.getLong("Role") == 0L) {
						userToChange=Entity.newBuilder(userKey2)
								.set("Status", data.status.equals("ATIVO") || data.status.equals("INATIVO") ? data.status :status)
								.build();
						
					}else{
						txn.rollback();
						LOG.warning("User: " + data.username + " don't have permission");
						return Response.status(Status.BAD_REQUEST).entity("User without permission!").build();
						
					}
				
				
				

				txn.put(userToChange);
				txn.commit();
				LOG.info(" Status changed sucessfully.");
				return Response.ok("Status changed").build();

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
