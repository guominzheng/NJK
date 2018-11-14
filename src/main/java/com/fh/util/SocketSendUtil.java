package com.fh.util;

import com.alibaba.fastjson.JSONArray;
import com.fh.entity.dto.MessageDto;
import com.fh.entity.vo.MessageVo;
import com.fh.service.wx_classRoom.WxClassRoomUserService;
import com.fh.util.Websocket.SpringWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.util.concurrent.locks.ReentrantLock;

public class SocketSendUtil {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void send(MessageDto messageDto, JSONArray jsonArray, WxClassRoomUserService wxClassRoomUserService) {
        MessageVo messageVo = null;
        try {
            lock.lock();
            messageVo = new MessageVo();
            messageVo.setAudio_index(messageDto.getAudio_index());
            messageVo.setMessage(messageDto.getMessage());
            PageData pd = wxClassRoomUserService.findUser(messageDto);
            messageVo.setUserName(pd.getString("cr_userName"));
            messageVo.setImg(pd.getString("cr_userImg"));
            messageVo.setSize(messageDto.getSize());
            messageVo.setCreateTime(DateUtil.getTime());
            messageVo.setTeacher(messageDto.getTeacher());
            SpringWebSocketHandler.sendMessageToUser(messageDto.getLiveId(),new TextMessage(jsonArray.toJSONString(messageVo)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
