package com.example.foragentss.rooms;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.foragentss.constants.Constants;
import com.example.foragentss.rooms.DAO.AgentPartnerDAO;
import com.example.foragentss.rooms.DAO.CardTypeDAO;
import com.example.foragentss.rooms.DAO.CardsDAO;
import com.example.foragentss.rooms.DAO.DeviceDAO;
import com.example.foragentss.rooms.DAO.DownloadCardDAO;
import com.example.foragentss.rooms.DAO.PrintingDAO;
import com.example.foragentss.rooms.DAO.RetailersDAO;
import com.example.foragentss.rooms.DAO.UsersDAO;
import com.example.foragentss.rooms.entity.AgentAndPartner;
import com.example.foragentss.rooms.entity.CardRoom;
import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.entity.DeviceRoom;
import com.example.foragentss.rooms.entity.DownloadCardRoom;
import com.example.foragentss.rooms.entity.Print;
import com.example.foragentss.rooms.entity.Retailer;
import com.example.foragentss.rooms.entity.UserRoom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {UserRoom.class, CardRoom.class, DeviceRoom.class, CardTypeRoom.class,
        AgentAndPartner.class, Retailer.class, DownloadCardRoom.class, Print.class},
        version = 15,
        exportSchema = false)
public abstract class ApplicationRoomDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THEARD = 4;
    private static volatile ApplicationRoomDatabase INSTANCE;
    public static final ExecutorService dbExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THEARD);
    public static synchronized ApplicationRoomDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        ApplicationRoomDatabase.class, Constants.getDbName())
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return INSTANCE;
    }

    public abstract UsersDAO usersDAO();

    public abstract CardsDAO cardsDAO();

    public abstract DeviceDAO deviceDAO();

    public abstract CardTypeDAO cardTypeDAO();

    public abstract AgentPartnerDAO agentPartnerDAO();

    public abstract RetailersDAO retailersDAO();

    public abstract PrintingDAO printingDAO();

    public abstract DownloadCardDAO downloadCardDAO();
}
