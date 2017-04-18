import com.google.inject.AbstractModule;
import play.Logger;
import services.Bootstrap;

public class OnStartModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Bootstrap.class).asEagerSingleton();
    }
}
