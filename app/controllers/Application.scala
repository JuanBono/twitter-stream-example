package controllers

import actors.TwitterStreamer
import play.api.libs.iteratee._
import play.api.libs.json.{JsValue, JsObject}
import play.api._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._

class Application extends Controller {

  val loggingIteratee = Iteratee.foreach[JsObject] { value =>
    Logger.info(value.toString)
  }

  def index = Action { implicit request =>
    Ok(views.html.index("Tweets"))
  }

  def tweets = WebSocket.acceptWithActor[String, JsValue] {
    request => out => TwitterStreamer.props(out)
  }
}
