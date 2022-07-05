package farlands.infocollector.listeners;


import farlands.infocollector.Infocollector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.sql.Types.INTEGER;

public class joinListener implements Listener {
    public Connection conn;
    public Infocollector plugin;

    public joinListener(Infocollector plugin, Connection conn){
        this.plugin = plugin;
        this.conn = conn;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = (Player) e.getPlayer();
        String uuid = p.getUniqueId().toString();
        String name = p.getName();
        String query = "{? = call getClientidByUUID(?)}";
        int client_recNum = getClientID(uuid,query);
        if(client_recNum == -1){
            //If we dont get a client record number for the uuid it is a new player
            query = "Insert into clients (client_id, playeruuid, name) values (DEFAULT, '" + uuid + "','" + name + "')";
            sqlexecuteUpdate(query);
        }else{
            //In this case we got a client record number we just update the name regardless
            //UUID returned record number -> set name of the uuid to the playername
            query = "UPDATE clients SET name = '" + name + "' where client_id = " + client_recNum;
            sqlexecuteUpdate(query);
        }
    }

    public int getClientID(String nameORuuid, String query){
        CallableStatement cst = null;
        int ret = -1;
        try {
            cst = conn.prepareCall(query);
            cst.setString(2, nameORuuid);
            cst.registerOutParameter(1, INTEGER);
            cst.execute();
            ret = cst.getInt(1);
            return ret;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return ret;
        }
    }

    public void sqlexecuteUpdate(String q){
        try{
            Statement st = conn.createStatement();
            st.executeUpdate(q);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
