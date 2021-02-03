package com.skillbox.redisdemo;

import com.fasterxml.jackson.core.PrettyPrinter;
import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class RedisStorage {

    // Объект для работы с Redis
    private RedissonClient redisson;

    private ArrayList<Integer> listPayUsers = new ArrayList<>();

    // Объект для работы с ключами
    private RKeys rKeys;

    // Объект для работы с Sorted Set'ом
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "ONLINE_USERS";

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    // Пример вывода всех ключей
    public void listKeys() {
        Iterable<String> keys = rKeys.getKeys();
        for (String key : keys) {
            out.println("KEY: " + key + ", type:" + rKeys.getType(key));
        }
    }

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            redisson = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Не удалось подключиться к Redis");
            out.println(Exc.getMessage());
        }
        rKeys = redisson.getKeys();
        onlineUsers = redisson.getScoredSortedSet(KEY);
        rKeys.delete(KEY);
    }

    void shutdown() {
        redisson.shutdown();
    }

    // Фиксирует посещение пользователем страницы
    void logPageVisit(int user_id) {
        //ZADD ONLINE_USERS
        onlineUsers.add(getTs(), String.valueOf(user_id));
    }

    // Удаляет
    void deleteOldEntries(int secondsAgo) {
        //ZREVRANGEBYSCORE ONLINE_USERS 0 <time_5_seconds_ago>
        onlineUsers.removeRangeByScore(0, true, getTs() - secondsAgo, true);


    }

    int calculateUsersNumber() {
        //ZCOUNT ONLINE_USERS
        return onlineUsers.count(Double.NEGATIVE_INFINITY, true, Double.POSITIVE_INFINITY, true);
    }

    public int nextPayUser(int userId, int request) {
        HashMap<String, Integer> range = getRange(userId, RedisTest.getUSERS());
        int end = range.get("end");
        int payUserId = -1;
        boolean payIsRange = isPayInRange(range);
        if (!payIsRange && (randomBoolean() || request % 10 == 0)) {
            payUserId = getNextPayUser(userId, end);
            listPayUsers.add(payUserId);
        }
        return payUserId;
    }

    private HashMap<String, Integer> getRange(int userId, int fullCount) {
        HashMap<String, Integer> range = new HashMap<>();
        for (int i = 0; i < fullCount; i = i + 10) {
            int end = i + 10 >= fullCount ? fullCount : i + 9;
            if (userId >= i && userId <= end) {
                range.put("start", i);
                range.put("end", end);
                return range;
            }
        }
        return range;
    }

    private boolean isPayInRange(HashMap<String, Integer> range) {
        int start = range.get("start");
        int end = range.get("end");
        for (int payUserId : listPayUsers) {
            if (payUserId >= start && payUserId <= end) {
                return true;
            }
        }
        return false;
    }

    private int getNextPayUser(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start) + start;
    }

    private boolean randomBoolean() {
        return Math.random() < 0.5;
    }

    public boolean cleanPayList()    {
        try {
            listPayUsers = new ArrayList<>();
            return true;
        }
        catch (Exception ex)    {
            return false;
        }
    }
}
