package entity.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginManager {
    private Map<String, PluginInterface> loaders = new HashMap<>();

    public PluginManager() {
        // Register the default loader
        // loaders.put("txt", new TxtConfigLoader());
    }

    public void loadPlugin(String jarPath, String className, String fileType) {
        try {
            File jarFile = new File(jarPath);
            URL jarURL = jarFile.toURI().toURL();
            URLClassLoader loader = URLClassLoader.newInstance(new URL[]{jarURL});
            Class<?> clazz = Class.forName(className, true, loader);
            PluginInterface configLoader = (PluginInterface) clazz.getDeclaredConstructor().newInstance();
            addLoader(fileType, configLoader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLoader(String fileType, PluginInterface loader) {
        loaders.put(fileType, loader);
    }

    public PluginInterface getLoader(String fileType) {
        return loaders.get(fileType);
    }
}
