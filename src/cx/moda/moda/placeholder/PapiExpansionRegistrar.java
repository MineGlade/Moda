package cx.moda.moda.placeholder;

// This class needs to exist, because any PlaceholderAPI only APIs can not be used in the Main class
// or there will be a NoClassDefFound error when you don't have PAPI installed.
public class PapiExpansionRegistrar {

	public static void register() {
		new PapiExpansion().register();
	}

}
