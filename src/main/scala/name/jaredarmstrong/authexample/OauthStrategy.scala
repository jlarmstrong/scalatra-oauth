package name.jaredarmstrong.authexample

import java.util._

import org.scribe.builder._
import org.scribe.builder.api._
import org.scribe.builder.api.GoogleApi
import org.scribe.model._
import org.scribe.oauth._
import javax.servlet.http.{HttpServletResponse, HttpServletRequest}

//import net.liftweb.common.{Box, Empty, Failure, Full}
import org.scalatra.auth.ScentryStrategy
import org.slf4j.LoggerFactory
import org.scalatra._


object OauthStrategy {

val NETWORK_NAME = "Google"
val AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token="
val PROTECTED_RESOURCE_URL = "https://docs.google.com/feeds/default/private/full/"
val SCOPE = "https://docs.google.com/feeds/"

val service = new ServiceBuilder()
                                  .provider(classOf[GoogleApi])
                                  .apiKey("anonymous")
                                  .apiSecret("anonymous")
                                  .scope(SCOPE)
                                  .callback("http://localhost:8080/oauth2callback")
                                  .build();

var oauth_token = ""

  def getRequestToken()={
    service.getRequestToken();	
  }

  def getRequestTokenUrl(requestToken: Token)={
  	oauth_token=requestToken.getToken()

  	AUTHORIZE_URL + requestToken.getToken()
  }

}

class OurOAuthStrategy(protected val app: ScalatraBase) extends ScentryStrategy[User]
{

	val COOKIE_KEY = "rememberMe"

  private def remoteAddress = {
    val proxied = app.request.getHeader("X-FORWARDED-FOR")
    val res = if (proxied != "" ) proxied else app.request.getRemoteAddr

    res
  }

  /**
   * Authenticates a user by validating the username (or email) and password request params.
   */
  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
  	var ret: Option[User] = None

println("param: "+app.params("oauth_token") +" save t: " + app.session("oauth_token"))
  	if(app.params("oauth_token") == app.session("oauth_token")){
  		ret = Some(User.byId("1"))
  	}


  	ret
  }

  /**
   * Clears the remember-me cookie for the specified user.
   */
  override def beforeLogout(u: User)(implicit request: HttpServletRequest, response: HttpServletResponse) = {

    app.session.invalidate()

    app.cookies.get(COOKIE_KEY) foreach {
      _ => app.cookies.update(COOKIE_KEY, null)
    }
  }

}