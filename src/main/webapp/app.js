var xmlhttp = new XMLHttpRequest();

var logInfo;
const URLx3 = "https://second-project-349311.appspot.com/"
const URLx4="https://second-project-349311.oa.r.appspot.com/"
const URLx="https://bamboo-archery-349311.appspot.com/"
const URLx2 = "http://localhost:8080/"




function newPasswordStart() {

	const form = document.querySelector('form');
	form.addEventListener('submit', handleNewPwd);

}


function updateLotStart(){
	const form = document.querySelector('form');
	form.addEventListener('submit', processInputUpdateLot);
}

function updateStart() {

	const form = document.querySelector('form');
	form.addEventListener('submit', processInputUpdate);

}

function registerLotStart(){
	const form = document.querySelector('form');
	form.addEventListener('submit', processRegisterLot);
}


function updateStatusStart() {

	const form = document.querySelector('form');
	form.addEventListener('submit', processInputUpdateStatus);

}


function updateRoleStart() {

	const form = document.querySelector('form');
	form.addEventListener('submit', processInputUpdateRole);

}
function removeStart() {

		const form = document.querySelector('form');
		form.addEventListener('submit', handleRemove);

	}
	function registerStart() {

		const form = document.querySelector('form');
		form.addEventListener('submit', processInputRegister);

	}


	function logoutStart() {

		const btn = document.querySelector('input');
		btn.addEventListener('click', handleLogout);

	}

function getHomeData() {
		var log = window.localStorage.getItem("Role");
        var rank;
        if(log==0){
            rank = "USER";
        }else if(log == 10){
            rank = "GBO";
        }else if(log == 20){
            rank = "GS";
        }else if(log == 30){
            rank = "SU";
        }

		var user = window.localStorage.getItem("username");

		document.getElementById("Role").textContent = "Role: " + log;
		document.getElementById("welcomeMessage").textContent = "Welcome, " + user;
		//document.getElementById("logoutStartButton").addEventListener("click",logoutStart())


	}



function handleNewPwd(event) {
	event.preventDefault();
	const data = new FormData(event.target);
	const value = Object.fromEntries(data.entries());
	if (xmlhttp) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {

				window.location = URLx3 + "login.html";
				alert(xmlhttp.responseText);


			} else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403 || xmlhttp.status === 400)) {
				alert(xmlhttp.responseText);


			}
		}
		var u_username = window.localStorage.getItem("username");
		var u_password = new String(value["password"]);
		var u_newpassword = new String(value["newpassword"]);
		var u_confnewpassword = new String(value["newpwdconfirmation"]);



		var obj = JSON.stringify({ "username": u_username, "password": u_password, "newpassword": u_newpassword, "confirmation": u_confnewpassword });

		xmlhttp.open("POST", URLx3 + "rest/Change/Password");
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(obj);

	}
}
function verifyNull(info) {
	if (info == null) {
		let result = "";
		return result;

	} else return info;



}

function processInputUpdate(event) {

	event.preventDefault();
	const data = new FormData(event.target);
	const value = Object.fromEntries(data.entries());

	if (xmlhttp) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
				window.location = URLx3 + "home.html";
				alert(xmlhttp.responseText);


			} else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403 || xmlhttp.status === 400)) {
				alert(xmlhttp.responseText);


			}
		}
		var u_username = window.localStorage.getItem("username");
		var u_username_to_change = new String(value["username"]);

		var u_email = verifyNull(new String(value["email"]));
		var u_name = verifyNull(new String(value["name"]));
		var u_perfil = new String(value["perfil"]);
		var u_telfixo = verifyNull(new String(value["tel_fixo"]));
		var u_telmv = verifyNull(new String(value["telmv"]));
		var u_nif = verifyNull(new String(value["nif"]));


		var obj = JSON.stringify({
			"username": u_username,
			"usernameToChange": u_username_to_change,
			"email": u_email,
			"name": u_name,
			"perfil": u_perfil,
			"tel_fixo": u_telfixo,
			"telmv": u_telmv,
			"NIF": u_nif
		});

		xmlhttp.open("POST", URLx3 + "rest/Change/attributes/");
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(obj);

	}
}


function processInputUpdateStatus(event) {

	event.preventDefault();
	const data = new FormData(event.target);
	const value = Object.fromEntries(data.entries());

	if (xmlhttp) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
				window.location = URLx3 + "home.html";
				alert(xmlhttp.responseText);


			} else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403 || xmlhttp.status === 400)) {
				alert(xmlhttp.responseText);


			}
		}
		var u_username = window.localStorage.getItem("username");
		var u_username_to_change = new String(value["username"]);
		var u_status = new String(value["status"]);
		


		var obj = JSON.stringify({
			"username": u_username,
			"usernameToChange": u_username_to_change,
			"status": u_status,
			
		});

		xmlhttp.open("POST", URLx3 + "rest/Change/status/");
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(obj);

	}
}


function processInputUpdateRole(event) {

	event.preventDefault();
	const data = new FormData(event.target);
	const value = Object.fromEntries(data.entries());

	if (xmlhttp) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
				window.location = URLx3 + "home.html";
				alert(xmlhttp.responseText);


			} else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403 || xmlhttp.status === 400)) {
				alert(xmlhttp.responseText);


			}
		}
		var u_username = window.localStorage.getItem("username");
		var u_username_to_change = new String(value["username"]);
		var u_role = new String(value["role"]);
		


		var obj = JSON.stringify({
			"username": u_username,
			"usernameToChange": u_username_to_change,
			"role": u_role,
			
		});

		xmlhttp.open("POST", URLx3 + "rest/Change/role/");
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(obj);

	}
}
	
	function handleRemove(event) {
		event.preventDefault();
		const data = new FormData(event.target);
		const value = Object.fromEntries(data.entries());
		if (xmlhttp) {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
					window.location = URLx3 + "home.html";
					alert(xmlhttp.responseText)




				} else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403||xmlhttp.status === 400)) {
					alert(xmlhttp.responseText);


				}
			}
			var u_username = window.localStorage.getItem("username");
			var u_username_To_Remove = new String(value["username"]);



			var obj = JSON.stringify({ "username": u_username, "usernameToDelete": u_username_To_Remove });

			xmlhttp.open("POST", URLx3 + "rest/Remove/");
			xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			xmlhttp.send(obj);

		}
	}


function loginStart() {

	const form = document.querySelector('form');
	form.addEventListener('submit', handleLogin);

}

	function handleLogout() {

		if (xmlhttp) {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
					window.location = URLx3;
					window.localStorage.clear();



				} else if (xmlhttp.readyState === 4 && xmlhttp.status === 403) {
					alert(xmlhttp.responseText);


				}
			}
			var u_username = window.localStorage.getItem("username");


			var obj = JSON.stringify({ "username": u_username });

			xmlhttp.open("POST", URLx3 + "rest/logout/");
			xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			xmlhttp.send(obj);

		}
	}

	
function processInputRegister(event) {

		event.preventDefault();
		const data = new FormData(event.target);
		const value = Object.fromEntries(data.entries());

		if (xmlhttp) {
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
					window.location = URLx3 + "home.html";

				} else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403 || xmlhttp.status === 400)) {
					alert(xmlhttp.responseText);


				}
			}
			var u_username = new String(value["username"]);
			var u_email = new String(value["email"]);
			var u_password = new String(value["pwd"]);
			var u_pwdconfirmation = new String(value["pwdconfirmation"]);
			var u_name = new String(value["name"]);
			var u_perfil = new String(value["perfil"]);
			var u_telfixo = new String(value["tel_fixo"]);
			var u_telmv = new String(value["telmv"]);
			var u_nif = new String(value["nif"]);
			
			var u_address1 = new String(value["address1"]);
			var u_address2 = new String(value["address2"]);
			var u_locality = new String(value["locality"]);


			var obj = JSON.stringify({
				"username": u_username,
				"password": u_password,
				"confirmation": u_pwdconfirmation,
				"email": u_email,
				"name": u_name,
				"perfil": u_perfil,
				"tel_fixo": u_telfixo,
				"telmv": u_telmv,
				"NIF": u_nif,
				"address1" : u_address1,
				"address2" : u_address2,
				"locality" : u_locality 
			});

			xmlhttp.open("POST", URLx3 + "rest/Register/v3");
			xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
			xmlhttp.send(obj);

		}
}





function handleLogin(event) {
	event.preventDefault();
	const data = new FormData(event.target);
	const value = Object.fromEntries(data.entries());
	if (xmlhttp) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
				logInfo = JSON.parse(xmlhttp.responseText);
				console.log(logInfo.role);
				window.localStorage.setItem('logInfo', logInfo);
				window.localStorage.setItem('token', "Username: " + logInfo.username + "\n" + "Role: " + logInfo.role + "\n" + "TokenID: " + logInfo.tokenID + "\n" + "CreationData: " + logInfo.creationData + "\n" + "ExpirationData: " + logInfo.expirationData);

				window.localStorage.setItem('role', logInfo.role);
				window.localStorage.setItem('username', new String(value["username"]));


				window.location = URLx3 + "home.html";


			} else if (xmlhttp.readyState === 4 && xmlhttp.status === 403) {
				alert(xmlhttp.responseText);


			}
		}
		var u_username = new String(value["username"]);
		var u_password = new String(value["password"]);

		var obj = JSON.stringify({ "username": u_username, "password": u_password });

		xmlhttp.open("POST", URLx3 + "rest/login/v1");
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(obj);

	}
}

function getToken() {
	alert(window.localStorage.getItem("token"));





}

function getList() {
	if (xmlhttp) {
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
				logInfo = JSON.parse(xmlhttp.responseText);
				console.log(logInfo.role);

				window.location = URLx3 + "home.html";
				alert(logInfo);


			} else if (xmlhttp.readyState === 4 && xmlhttp.status === 403) {
				alert(xmlhttp.responseText);


			}
		}
		var u_username = window.localStorage.getItem("username");

		var obj = JSON.stringify({ "username": u_username });

		xmlhttp.open("POST", URLx3 + "rest/List");
		xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		xmlhttp.send(obj);




	}
	







}
 function getListLotLong(){
        if (xmlhttp) {
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    logInfo = JSON.parse(xmlhttp.responseText);
                    console.log(logInfo.role);

                    window.location = URLx3 + "home.html";
                    alert(logInfo);


                } else if (xmlhttp.readyState === 4 && xmlhttp.status === 403) {
                    alert(xmlhttp.responseText);


                }
            }
			var token = window.localStorage.getItem('logInfo', logInfo);

            var u_upRightLong = new Long(value["upRightLong"]);
            var u_downLeftLong = new Long(value["downLeftLong"]);

            var obj = JSON.stringify({ 
                "upRightLong": u_upRightLong,
                 "downLeftLong":u_downLeftLong,
                 "token" : token
                 
            });

            xmlhttp.open("POST", URLx3 + "listLot/long");
            xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xmlhttp.send(obj);
        }

    }
    
     function getListLotLat(){
        if (xmlhttp) {
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    logInfo = JSON.parse(xmlhttp.responseText);
                    console.log(logInfo.role);

                    window.location = URLx3 + "home.html";
                    alert(logInfo);


                } else if (xmlhttp.readyState === 4 && xmlhttp.status === 403) {
                    alert(xmlhttp.responseText);


                }
            }


			var token = window.localStorage.getItem('logInfo', logInfo);
            var u_upRightLat = new Long(value["upRightLat"]);
            var u_downLeftLat = new Long(value["downLeftLat"]);

            var obj = JSON.stringify({ 
                "upRightLat": u_upRightLat,
                 "downLeftLat":u_downLeftLat,
                 "token" : token
                 
            });

            xmlhttp.open("POST", URLx3 + "listLot/lat");
            xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xmlhttp.send(obj);
        }

    }
    
       function getList() {
        if (xmlhttp) {
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    logInfo = JSON.parse(xmlhttp.responseText);
                    console.log(logInfo.role);

                    window.location = URLx3 + "home.html";
                    alert(logInfo);


                } else if (xmlhttp.readyState === 4 && xmlhttp.status === 403) {
                    alert(xmlhttp.responseText);


                }
            }
            var u_username = window.localStorage.getItem("username");

            var obj = JSON.stringify({ "username": u_username });

            xmlhttp.open("POST", URLx3 + "rest/List");
            xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xmlhttp.send(obj);




        }
    }

	function processRegisterLot(event){
        event.preventDefault();
        const data = new FormData(event.target);
        const value = Object.fromEntries(data.entries());

        if (xmlhttp) {
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    window.location = URLx + "home.html";

                } else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403 || xmlhttp.status === 400)) {
                    alert(xmlhttp.responseText);


                }

            }

            var u_idLot = new String(value["idLot"]);
            var u_rustico = new Boolean(value["rustico"]);
            var u_nameOwner = new String(value["nameOwner"]);
            var u_nacionalidade = new String(value["nacionalidade"]);
            var u_tipoDoc = new String(value["tipoDoc"]);
            var u_dataDoc = new String(value["dataDoc"]);
            var u_upRightLat = new Long(value["upRightLat"]);
            var u_upRightLong = new Long(value["upRightLong"]);
            var u_downLeftLat = new Long(value["downLeftLat"]);
            var u_downLeftLat = new Long(value["downLeftLong"]);
            
            var obj = JSON.stringify({
                "idLot" : u_idLot,
                "rustico" : u_rustico,
            "nameOwner" : u_nameOwner,
                "nacionalidade" : u_nacionalidade,
                "tipoDoc" : u_tipoDoc ,
                "dataDoc" : u_dataDoc,
                "upRightLat" :  u_upRightLat,
                "upRightLong" : u_upRightLong,
                "downLeftLat" : u_downLeftLat,
                "downLeftLat" : u_downLeftLat
            });

            xmlhttp.open("POST", URLx + "rest/registerLot/v1");
            xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xmlhttp.send(obj);
        }   

    }

	function processInputUpdateLot(event){
        event.preventDefault();
        const data = new FormData(event.target);
        const value = Object.fromEntries(data.entries());

        if (xmlhttp) {
            xmlhttp.onreadystatechange = function() {
                if (xmlhttp.readyState === 4 && xmlhttp.status === 200) {
                    window.location = URLx + "home.html";
                    alert(xmlhttp.responseText);


                } else if (xmlhttp.readyState === 4 && (xmlhttp.status === 403 || xmlhttp.status === 400)) {
                    alert(xmlhttp.responseText);


                }
            }

            var u_idLot = new String(value["idLot"]);
            var u_registerUsername = new String(value["registerUsername"]);
            var u_verificado = new Boolean(value["verificado"]);

            var obj = JSON.stringify({
                "idLot": u_idLot,
                "registerUsername": u_registerUsername,
                "verificado": u_verificado,
                
            });

            xmlhttp.open("POST", URLx + "rest/Update/lot/");
            xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
            xmlhttp.send(obj);

        }
        
    }
