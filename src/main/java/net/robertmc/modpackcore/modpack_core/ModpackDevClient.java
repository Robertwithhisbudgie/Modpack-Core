package net.robertmc.modpackcore.modpack_core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ModpackDevClient implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("SimpleConfig");
    public static String window_title = "";
    public static boolean customendingimpl;
    public static boolean docustomtitle;
    public static String endstyle;
    public static SimpleConfig config;

    public static String bottomleft;
    public static String bottomleftaction;
    public static String bottomleftdata;

    public static String bottomright;
    public static String bottomrightaction;
    public static String bottomrightdata;

    public static String topleft;
    public static String topleftaction;
    public static String topleftdata;

    public static String topright;
    public static String toprightaction;
    public static String toprightdata;


    private String provider( String filename ) {
        return """
                # {mcversion} = minecraft version (e.g 1.21)
                # Custom variables are supported. They have to not start with core and have to be strings!
                # e.g. myvar.heh=hahahhaha
                # they can be used by using {myvar.heh} in a variable
                modpack.mainvers=1.0
                modpack.vers={mcversion}-{modpack.mainvers}
                # modpack.vers is 1.21-1.0 in this test case
                
                # custom title enabled - true or false
                core.window.title.custom=false
                core.window.title=Minecraft {mcversion} - {modpack.vers}
                
                # Use a custom ending implementation
                # do not touch if you don't understand
                core.window.title.custendingimpl=false
                core.window.title.endingstyle={core.window.title} - {ending}
                
                # you can write text in the corners of the screen
                # you can run an action
                # leave empty for just plain text
                # "openurl" - opens the url
                # invalid values are ignored
                # there is also data that goes with the action. For example what url to open
                
                core.window.top.left=
                core.window.top.left.action=
                core.window.top.left.data=
                
                core.window.top.right=
                core.window.top.right.action=
                core.window.top.right.data=
                
                core.window.bottom.left=
                core.window.bottom.left.action=
                core.window.bottom.left.data=
                
                core.window.bottom.right=
                core.window.bottom.right.action=
                core.window.bottom.right.data=
                """;
    }
    @Override
    public void onInitializeClient() {
        SimpleConfig.ConfigRequest x = SimpleConfig.of(".modpackcore");
        x.provider(this::provider);
        String minecraftVersion = FabricLoader.getInstance().getModContainer("minecraft")
                .map(modContainer -> modContainer.getMetadata().getVersion().getFriendlyString())
                .orElse("Unknown version");
        SimpleConfig config = x.request();

        docustomtitle = config.getOrDefault("core.window.title.custom",false);
        customendingimpl = config.getOrDefault("core.window.title.custendingimpl",false);
        endstyle = config.getOrDefault("core.window.title.endingstyle","{core.window.title} - {ending}");
        ModpackDevClient.config = config;
        if (docustomtitle)
        {
            config.config.put("mcversion",minecraftVersion);
            for (Map.Entry<String, String> set :
                    config.config.entrySet()) {
                config.config.put(set.getKey(), process(set.getValue(),config));
            }
            window_title = config.getOrDefault("core.window.title","Minecraft {mcversion}");
        }
        bottomleft = config.getOrDefault("core.window.bottom.left","");
        bottomleftaction = config.getOrDefault("core.window.bottom.left.action","");
        bottomleftdata = config.getOrDefault("core.window.bottom.left.data","");

        bottomright = config.getOrDefault("core.window.bottom.right","");
        bottomrightaction = config.getOrDefault("core.window.bottom.right.action","");
        bottomrightdata = config.getOrDefault("core.window.bottom.right.data","");

        topleft = config.getOrDefault("core.window.top.left","");
        topleftaction = config.getOrDefault("core.window.top.left.action","");
        topleftdata = config.getOrDefault("core.window.top.left.data","");

        topright = config.getOrDefault("core.window.top.right","");
        toprightaction = config.getOrDefault("core.window.top.right.action","");
        toprightdata = config.getOrDefault("core.window.top.right.data","");
    }

    public static String process(String input, SimpleConfig config) {
        for (Map.Entry<String, String> set :
                config.config.entrySet()) {
            input = input.replace("{"+set.getKey().strip()+"}",set.getValue());
        }
        return input;
    }
}
