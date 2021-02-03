package com.skillbox.redisdemo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class RedisTest {

    // Запуск докер-контейнера:
    // docker run --rm --name skill-redis -p 127.0.0.1:6379:6379/tcp -d redis

    // Для теста будем считать неактивными пользователей, которые не заходили 2 секунды
    private static final int DELETE_SECONDS_AGO = 2;

    // Допустим пользователи делают 500 запросов к сайту в секунду
    private static final int RPS = 500;

    // И всего на сайт заходило 1000 различных пользователей
    private static final int USERS = 20;

    // Также мы добавим задержку между посещениями
    private static final int SLEEP = 1; // 1 миллисекунда

    private static final SimpleDateFormat DF = new SimpleDateFormat("HH:mm:ss");

    private static void log(int UsersOnline) {
        String log = String.format("[%s] Пользователей онлайн: %d", DF.format(new Date()), UsersOnline);
        out.println(log);
    }

    public static void main(String[] args) throws InterruptedException {

        RedisStorage redis = new RedisStorage();
        redis.init();
        // Эмулируем 10 секунд работы сайта
        for(int seconds=0; seconds <= 10; seconds++) {
            // Выполним 500 запросов
            for(int request = 1; request <= RPS; request++) {
                int user_id = new Random().nextInt(USERS);
                int nextPayUser = redis.nextPayUser(user_id, request);
                if (nextPayUser != -1)   {
                    redis.logPageVisit(nextPayUser);
                    out.println("> Пользователь " + nextPayUser + " оплатил платную услугу");
                    out.println("- На главной странице показываем пользователя " + nextPayUser);
                }
                else {
                    redis.logPageVisit(user_id);
                    //out.println("- На главной странице показываем пользователя " + user_id);
                }
                Thread.sleep(SLEEP);
            }
            redis.deleteOldEntries(DELETE_SECONDS_AGO);
            int usersOnline = redis.calculateUsersNumber();
            log(usersOnline);
            redis.cleanPayList();
        }
        redis.shutdown();
    }

    public static int getUSERS() {
        return USERS;
    }
}
