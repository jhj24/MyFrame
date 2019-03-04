package com.zgdj.djframe.utils;


import com.zgdj.djframe.interf.INotifyListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 监听管理类,取代广播
 * Created by zr on 2016/10/14.
 */

public class NotifyListenerMangager {

    public static NotifyListenerMangager manager;
    private List<INotifyListener> listeners = new ArrayList<INotifyListener>();
    private Map<String,INotifyListener> maps = new HashMap<String,INotifyListener>();

    /**
     * 获得实例
     * @return
     */
    public static NotifyListenerMangager getInstance(){
        if(manager == null){
            manager = new NotifyListenerMangager();
        }
        return manager;
    }

    /**
     * 注册监听
     * @param lister
     * @param tag 为context 的 tag
     */
    public void registerListener(INotifyListener lister,String tag){
        if(listeners.contains(lister)) return;
        listeners.add(lister);
        maps.put(tag,lister);
    }

    /**
     * 去除监听
     * @param lister
     */
    public void unRegisterListener(INotifyListener lister){
        if(listeners.contains(lister)){
            listeners.remove(lister);
        }
        if(maps.get(lister) != null){
            maps.remove(lister);
        }
    }


    /**
     * 通知全部页面
     * @param obj
     */
    public void nofityAllContext(Object obj){
        for (INotifyListener lister : listeners) {
            lister.notifyContext(obj);
        }
    }


    /**
     * 通知某个页面
     * @param obj
     * tag 为context 的 tag
     */
    public void nofityContext(Object obj,String tag){
        INotifyListener lister = maps.get(tag);
        if(lister != null){
            lister.notifyContext(obj);
        }
    }

    /**
     * 去除所有监听,建议系统退出时
     */
    public void removeAllListener(){
        listeners.clear();
        maps.clear();
    }

}
