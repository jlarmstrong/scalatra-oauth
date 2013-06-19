package name.jaredarmstrong.authexample

case class User(id: String,email:String, name: String)

object User {

	def byId(id: String)={
		if(id=="1"){
		  User("1","jarmstrong@omnispear.com","Jared Armstrong")
		}else{
			null
		}
	}

	def loginByEmail(e: String): Option[User] = {
		if(e.contains("jarmstrong@omnispear.com")){
			Some(User("1","jarmstrong@omnispear.com","Jared Armstrong"))
		}else{
			None
		}
	}

	def login(u: String, p: String){

	}

}