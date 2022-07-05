package farlands.infocollector;

import farlands.infocollector.listeners.joinListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Infocollector extends JavaPlugin {
    public Connection conn;
    public joinListener jl;
    @Override
    public void onEnable() {
        try {
            conn = DriverManager.getConnection("","","");
        } catch (SQLException e){
            //do nothing for now I have no ideas
        }
        jl = new joinListener(this, conn);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
