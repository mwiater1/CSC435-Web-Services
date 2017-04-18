package actions;

import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class Auth extends Action.Simple {

    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        if(ctx.session().get("apiKey") != null) {
            return delegate.call(ctx);
        } else {
            return CompletableFuture.supplyAsync(() -> redirect(controllers.routes.UserController.doGet()));
        }
    }
}
