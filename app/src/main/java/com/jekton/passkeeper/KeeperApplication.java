package com.jekton.passkeeper;

import android.app.Application;

import com.jekton.passkeeper.util.log.MyDiskLogStrategy;
import com.jekton.passkeeper.util.log.TimestampStrategy;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;


/**
 * @author Jekton
 */

public class KeeperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }


    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
        LogStrategy logStrategy = TimestampStrategy.newBuilder()
                .logStrategy(new MyDiskLogStrategy("passkeeper"))
                .build();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .logStrategy(logStrategy)
                .build();
        Logger.addLogAdapter(new DiskLogAdapter(formatStrategy));
    }
}
