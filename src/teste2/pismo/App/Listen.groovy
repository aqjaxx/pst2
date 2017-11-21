
package teste2.pismo.App

import groovy.transform.Field
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import teste2.pismo.App.*

def AccountsGet(routingContext) {
  def comand = routingContext.request().getParam("comand")
  def response = routingContext.response()
  Accounts acco = new Accounts()
  def resul = acco.Get(comand)
  if(resul.charAt(0)=='#') {
	  this.sendError(404, response)
  } else {
	  response.putHeader("content-type", "application/json").end( resul )
  }
}

def AccountsPatch(routingContext) {
	def id = routingContext.request().getParam("id")
	def response = routingContext.response()
	if (id == null) {
	  this.sendError(400, response)
	} else {
	  Accounts acco = new Accounts()
 	  def resul = acco.Patch( id , routingContext.getBodyAsJson())
	}
}

def doTransactions(routingContext) {
	def response = routingContext.response()
	Transactions transacao = new Transactions();
	response.putHeader("content-type", "application/json").end( transacao.Post("transactions", routingContext.getBodyAsJson()) )
}

def doPayments(routingContext) {
	def response = routingContext.response()
	Transactions transacao = new Transactions();
	response.putHeader("content-type", "application/json").end( transacao.Post("payments", routingContext.getBodyAsJson()) )
}

def sendError(statusCode, response) {
  response.setStatusCode(statusCode).end()
}

def router = Router.router(vertx)

router.route().handler(BodyHandler.create())
router.get("/v1/accounts/:comand").handler(this.&AccountsGet)
router.patch("/v1/accounts/:id").handler(this.&AccountsPatch)
router.post("/v1/transactions").handler(this.&doTransactions)
router.post("/v1/payments").handler(this.&doPayments)

vertx.createHttpServer().requestHandler(router.&accept).listen(8080)
