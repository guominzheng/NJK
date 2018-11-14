package com.fh.util;

import com.fh.service.gameService.GameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class GameUserUtil implements Runnable {

    private GameUserService gameUserService;

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    public GameUserUtil(GameUserService gameUserService) {
        this.gameUserService = gameUserService;
    }

    public GameUserUtil() {

    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(20000);
                String time = sdf.format(new Date());
                if (time.equals("00:00")) {
                    gameUserService.updateAllDraw(new PageData());
                    System.out.println("=======================游戏用户数据清空！");
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }
}
