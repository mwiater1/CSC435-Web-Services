package actions;

import models.User;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import services.Status;
import views.html.JsonAuthView;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RestfulAuth extends Action.Simple {
    @Override
    public CompletionStage<Result> call(Http.Context ctx) {
        final String apiKeyGetParam = ctx.request().getQueryString("apiKey");
        final String apiKey;

        if(apiKeyGetParam != null) {
            apiKey = apiKeyGetParam;
        } else {
            apiKey = "";
        }

        try {
            final Optional<User> user = User.getUser(UUID.fromString(apiKey));
            if(user.isPresent()) {
                return delegate.call(ctx);
            } else {
                return CompletableFuture.supplyAsync(() -> status(400, JsonAuthView.render(Status.FAIL, "Unable to authenticate user. Check APIKey and try again.")));
            }
        } catch (IllegalArgumentException e) {
            return CompletableFuture.supplyAsync(() -> status(400, JsonAuthView.render(Status.FAIL, "Unable to authenticate user. Check APIKey and try again.")));
        }
    }
}
