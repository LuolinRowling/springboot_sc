package com.pku.system.util;

public class Constant {
    //设备管理常量
    public static String OPEN = "open";
    public static String CLOSE = "close";
    public static String OPENALL = "open_all";
    public static String CLOSEALL = "close_all";
    public static String DETECTONLINE = "detect_online";

    //推拉流常量
    public static String STARTPULL = "start_pull";
    public static String STOPTPULL = "stop_pull";
    public static String STARTPUSH = "start_push";
    public static String STOPTPUSH= "stop_push";
    public static String STARTPUSHBROADCAST = "start_push_broadcast";
    public static String STOPTPUSHBROADCAST= "stop_push_broadcast";

    public static String FAILPUSHCONNECTSERVER = "连接服务器失败，导致推流失败";
    public static String FAILPUSHOPENMIC = "打开麦克风失败，导致推流失败";
    public static String FAILPUSHOPENCAMERA = "打开摄像头失败，导致推流失败";
    public static String FAILPUSHPREPARERECORDER= "录音失败，导致推流失败";

    public static String FAILPUSHBROADCASTCONNECTSERVER = "连接服务器失败，导致广播失败";
    public static String FAILPUSHBROADCASTOPENMIC = "打开麦克风失败，导致广播失败";
    public static String FAILPUSHBROADCASTOPENCAMERA = "打开摄像头失败，导致广播失败";
    public static String FAILPUSHBROADCASTPREPARERECORDER= "录音失败，导致广播失败";

    public static String FAILPUSHDISCONNECTSERVER = "与服务器断开连接失败，导致推流失败";
    public static String FAILPUSHUNKNOWN = "未知错误，导致推流失败";
    public static String FAILPUSHSERVER = "服务器异常，导致推流失败";
    public static String FAILPUSHWEAKCONNECT= "网络信号不稳定，导致推流失败";
    public static String FAILPUSHTIMEOUT = "连接超时，导致推流失败";
    public static String FAILSTOPPUSH = "停止推流失败";

    public static String FAILPUSHBROADCASTDISCONNECTSERVER = "与服务器断开连接失败，导致广播失败";
    public static String FAILPUSHBROADCASTUNKNOWN = "未知错误，导致广播失败";
    public static String FAILPUSHBROADCASTSERVER = "服务器异常，导致广播失败";
    public static String FAILPUSHBROADCASTWEAKCONNECT= "网络信号不稳定，导致广播失败";
    public static String FAILPUSHBROADCASTTIMEOUT = "连接超时，导致广播失败";
    public static String FAILSTOPPUSHBROADCAST = "停止广播失败";

    public static String FAILPULLDISCONNECTSERVER = "与服务器断开连接失败，导致拉流失败";
    public static String FAILPULLUNKNOWN= "未知错误，导致拉流失败";
    public static String FAILPULLSERVER = "服务器异常，导致拉流失败";
    public static String FAILPULLWEAKCONNECT = "网络信号不稳定，导致拉流失败";
    public static String FAILPULLTIMEOUT = "连接超时，导致拉流失败";
    public static String FAILSTOPPULL = "停止拉流失败";

    public static String PUSHHEADER = "rtmp://media.mytorchwood.com/publish/";
    public static String PUSHFOOTER = "?auth=32578623-f138-479b-923b-87a381cddd84";
    public static String PULLHEADER = "rtmp://media.mytorchwood.com/live/";
    public static String PULLFOOTER = "?auth=371ac5fa-2634-4652-95e5-016b7f79b0c8";

    public static String PUSHHEADERBAIDU = "rtmp://push.bcelive.com/live/";
    public static String PULLHEADERBAIDU = "rtmp://play.bcelive.com/live/";

    public static String STREAMADDRESS = "rtmp://video.airforceuav.com:1935/live/";

    public static long MESSAGETIMEOUT = 60*1000;
}
