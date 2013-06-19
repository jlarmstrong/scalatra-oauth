package name.jaredarmstrong.authexample

import org.scalatra._
import scalate.ScalateSupport

class MyScalatraServlet extends MyScalatraWebAppStack {

  def redirectIfNotAuthenticated = {
    //scentry.authenticate('RememberMe, 'UserPassword)

    if (!isAuthenticated){
      redirect("/login")
    }
  }

  def redirectIfAuthenticated = {
      if (isAuthenticated){
      redirect("/")
    }
  }


  get("/") {
  	redirectIfNotAuthenticated

    <html>
      <body>
        <h1>You logged in with oAuth</h1>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/login"){
  	val tUrl = OauthStrategy.getRequestTokenUrl(OauthStrategy.getRequestToken)

	session("oauth_token") = OauthStrategy.oauth_token
	println(tUrl)
	println(session)
	println(session("oauth_token"))

    redirectIfAuthenticated

    contentType="text/html"

    "<html>" +
    "<body>" +
    "<a href=\""+tUrl+"\">Login</a>" +
    "</body>" +
    "</html>"
  }

  get("/logout"){

  }

  get("/oauth2callback"){
  	println(params)
  	session("oauth_verifier") = params("oauth_verifier")

  	scentry.authenticate()

  	if (isAuthenticated) {
  		println("**SUCCESS**")
  		redirectIfAuthenticated
  		}else{
  			println("FAILED")

  		}

  		redirectIfNotAuthenticated
  }
  
}
