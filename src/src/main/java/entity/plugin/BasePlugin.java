package entity.plugin;

import java.util.*;
import java.io.IOException;
import entity.*;

public abstract class BasePlugin {
    protected Map<String, String> keyToValueMap = new HashMap<>();

    public BasePlugin() {
        this.keyToValueMap.put("SIRIP_HIU", "Shark Fin");    
        this.keyToValueMap.put("Shark Fin", "SIRIP_HIU");
        this.keyToValueMap.put("HIU_DARAT", "Land Shark");
        this.keyToValueMap.put("Land Shark", "HIU_DARAT");
        this.keyToValueMap.put("SUSU", "Milk");    
        this.keyToValueMap.put("Milk", "SUSU");
        this.keyToValueMap.put("SAPI", "Cow");
        this.keyToValueMap.put("Cow", "SAPI");
        this.keyToValueMap.put("DAGING_DOMBA", "Sheep Meat");    
        this.keyToValueMap.put("Sheep Meat", "DAGING_DOMBA");    
        this.keyToValueMap.put("DAGING_KUDA", "Horse Meat");    
        this.keyToValueMap.put("Horse Meat", "DAGING_KUDA");
        this.keyToValueMap.put("KUDA", "Horse");
        this.keyToValueMap.put("Horse", "KUDA");
        this.keyToValueMap.put("DAGING_BERUANG", "Bear Meat");    
        this.keyToValueMap.put("Bear Meat", "DAGING_BERUANG");    
        this.keyToValueMap.put("BERUANG", "Bear");    
        this.keyToValueMap.put("Bear", "BERUANG");    
        this.keyToValueMap.put("TELUR", "Egg");    
        this.keyToValueMap.put("Egg", "TELUR");
        this.keyToValueMap.put("AYAM", "Chicken");
        this.keyToValueMap.put("Chicken", "AYAM");
        this.keyToValueMap.put("STROBERI", "Strawberry");    
        this.keyToValueMap.put("Strawberry", "STROBERI");    
        this.keyToValueMap.put("LABU", "Pumpkin");    
        this.keyToValueMap.put("Pumpkin", "LABU");    
        this.keyToValueMap.put("JAGUNG", "Corn");    
        this.keyToValueMap.put("Corn", "JAGUNG");
        this.keyToValueMap.put("DOMBA", "Sheep");
        this.keyToValueMap.put("Sheep", "DOMBA");
        this.keyToValueMap.put("ACCELERATE", "Accelerate");    
        this.keyToValueMap.put("DELAY", "Delay");    
        this.keyToValueMap.put("PROTECT", "Protect");    
        this.keyToValueMap.put("DESTROY", "Destroy");    
        this.keyToValueMap.put("TRAP", "Trap");    
        this.keyToValueMap.put("INSTANT_HARVEST", "Instant Harvest");    
        this.keyToValueMap.put("Accelerate", "ACCELERATE");    
        this.keyToValueMap.put("Delay", "DELAY");    
        this.keyToValueMap.put("Protect", "PROTECT");    
        this.keyToValueMap.put("Destroy", "DESTROY");    
        this.keyToValueMap.put("Trap", "TRAP");    
        this.keyToValueMap.put("Instant Harvest", "INSTANT_HARVEST");
        this.keyToValueMap.put("BIJI_LABU", "Pumpkin Seed");
        this.keyToValueMap.put("Pumpkin Seed", "BIJI_LABU");
        this.keyToValueMap.put("BIJI_JAGUNG", "Corn Seed");
        this.keyToValueMap.put("Corn Seed", "BIJI_JAGUNG");
        this.keyToValueMap.put("BIJI_STROBERI", "Strawberry Seed");
        this.keyToValueMap.put("Strawberry Seed", "BIJI_STROBERI");
    }
}
