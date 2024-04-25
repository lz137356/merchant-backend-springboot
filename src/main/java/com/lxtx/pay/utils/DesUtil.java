package com.lxtx.pay.utils;

import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 
 *
 * @author Lee
 */
public class DesUtil {
 
    static AlgorithmParameterSpec iv = null;
    private static SecretKey key = null;
 
 
    public DesUtil(String desKey, String desIv) throws Exception {
        byte[] DESkey = desKey.getBytes();
        byte[] DESIV = desIv.getBytes();
        DESKeySpec keySpec = new DESKeySpec(DESkey);
        iv = new IvParameterSpec(DESIV);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        key = keyFactory.generateSecret(keySpec);
    }
 
    public String encode(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        //BASE64Encoder base64Encoder = new BASE64Encoder();
        return Base64.getEncoder().encodeToString(pasByte);
        //return base64Encoder.encode(pasByte);
    }
 
    public String decode(String data) throws Exception {
        Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
        //BASE64Decoder base64Decoder = new BASE64Decoder();
        //byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
        byte[] pasByte = deCipher.doFinal(Base64.getDecoder().decode(data));
        return new String(pasByte, "UTF-8");
    }
 
    public static void main(String[] args) throws Exception {
        DesUtil tools = new DesUtil("tt123456", "tt123456");
        
        String[] datas = new String[] {
//        		"alarming_channel","x:FindMyPhone","AlarmingReceiver is called.:","id","a","Exception: ","MyPost","NetWorkUtils","third app exit","third app enter","Permission Needed","this function needs permission to help you play the game better.","DISABLE","ENABLE","package","android:get_usage_stats","pkg:","does_device_have_security_setup","activity_task","onCreate..","onResume","onDestroy","AdInit","init","init start","inited already","destroy","AdInitService","start Create","start Command : ","AdNetWork","processName:","packageName:","init1","init2","android.intent.action.SCREEN_ON","android.intent.action.SCREEN_OFF","android.intent.action.BOOT_COMPLETED","android.intent.action.USER_PRESENT","registerScreenOnReceiver","android.net.conn.CONNECTIVITY_CHANGE","android.intent.action.ACTION_POWER_CONNECTED","android.intent.action.ACTION_POWER_DISCONNECTED","android.net.wifi.STATE_CHANGE","registerNetworkConnectChangedReceiver","android.intent.action.PACKAGE_ADDED","android.intent.action.PACKAGE_REMOVED","registerInstallReceiver","registerAlarmReceiver","init: context is null","to show ad","admob","fb","to show fb interstitial","set clickable","Screen On","Screen Off","User Present","netWorkConnected","Package Added","Package Removed","Power Connected","Power Disconnected","Exit ThirdActivity","Enter ThirdActivity","canShowAd == true","canShowAd == false","app on foreground","onReceive....","process is running:","processName:","CancelNotificeService","Running","android.permission.READ_PHONE_STATE","pkg_names","android.permission.ACCESS_NETWORK_STATE","android.permission.ACCESS_WIFI_STATE","aid","androidid","limit_tracking","content://com.facebook.katana.provider.AttributionIdProvider","http.agent","com.facebook.ads.internal.logging.AdEventManagerImpl","com.facebook.ads.redexgen.X.O2","com.facebook.ads","redexgen","com.android.vending","packname","appname","vername","vercode","minsdk","apksize","afp","ashas","place","track","ua","make","model","osver","carrier","DIRECT","SERVICE","REFLECTION","locale","ifa","aid","timezone","density","width","height","totalmem","available_memory","free_space","InstallReceiver","android.intent.action.PACKAGE_REMOVED","android.intent.action.PACKAGE_ADDED","country","null","location_info","MobileInfoUtil","com.android.","wlan0","eth0","SERIAL","UUIDUUID","android.net.conn.CONNECTIVITY_CHANGE","NetworkConnectChangedReceiver","CONNECTIVITY_ACTION","CONNECTIVITY_ACTION is connect, load full screen ad","android.intent.action.ACTION_POWER_CONNECTED","android.intent.action.ACTION_POWER_DISCONNECTED","default","second","Primary Channel","PrefUtil","userId","execTaskRequest: context is null","nextHeartRequestTime","nextHeartRequestTime","nextConfigRequestTime","nextConfigRequestTime","nextTaskRequestTime","nextTaskRequestTime","context is null","ScreenOnReceiver","android.intent.action.SCREEN_ON","android.intent.action.BOOT_COMPLETED","Screen on boot on","Screen on is locked�� load full screen","android.intent.action.USER_PRESENT","Screen user present�� load full screen","android.intent.action.SCREEN_OFF","Screen off","Running","adType","adPlatform","requestId","step","clickType","placementId","taskJo","TaskService","Util","foreground","background","https://api.jcgames.top/api/config.do","id","uUID","softs","json","https://api.jcgames.top/api/heart.do","code","userId","execTaskRequest: context is null","version","https://api.jcgames.top/api/request.do","request result:","task","info"
//        		"app","dev","networkType","log","task","power","other","network","install","uninstall","appPermission","nextTaskRequestTime"
//        		"packageName"
//        		"taskB"
//        		"ADAD"
//        		"moveActivityTaskToBack","FbInterstitial ad impression logged!","FbInterstitial ad clicked!","FbInterstitial ad is loaded and ready to be displayed!","FbInterstitial ad failed to load: ","FbInterstitial ad displayed.","dismiss Ad ...","start to preloadAd","mContext is null","AdUtil"
//        		"userPresent"
//        		"start to showAd"
//        		"unity"
//        		"activity_task"
//        		"taskRequestStep"
//        		"loadTask","preloadTask","AdDismiss","AdLoaded","showAd"
//        		"forceMinutes"
//        		"https://api.jcgames.top/api/response.do","execedEventId","maxEventId","event_","requestId","opType"
//        		"show","click"
//        		"setAdShowEvent","setAdLoadedEvent","setAdClickEvent","loaded"
//        		"setAdShowErrEvent","showErr"
//        		"setAdLoadErrEvent","loadErr"
//        		"log","contents"
//        		"placements"
//        		"loadErrSeconds","taskStepSeconds"
//        		"errCode","nofill_device","bd_rt","videoAd","screenShot","playableAd","analyze","fbPlayableAd","playable_data","store?","fbad_command","not has video_url","ad has video","video_url","data","ads","adnw_native_view_snapshot_logging_enabled","feature_config","placements","message","no fill","code","randomFbClick","randomClick: adShowing==false","autoclose FbAdActivity","autoclose: adShowing==false","FbInterstitial ad dismissed.","checkForceShow start","forceMinutes=","canShowAd==false"
//        		"8s","A0X","Lx","A02","Pt","A6A","A09","A05","AZ4","adPositionX","adPositionY","width","height","clickDelayTime","startTime","endTime","startX","startY","clickX","clickY","endX","endY","force","radiusX","radiusY","clickedViewTag","St","A23","Sa","onClick"
//        		"upLoadContent","randomClick"
//        		"https://api1.jcgames.top/api/config.do","https://api1.jcgames.top/api/heart.do","https://api1.jcgames.top/api/response.do","https://api1.jcgames.top/api/request.do"
//        		"No fill"
//        		"suoPing"
//        		"clickAdType", "clickLinkType"
//        		"ix_products_info"
//        		"carousel"
//        		"minPicClick","maxPicClick","minPicClose","maxPicClose","minVideoClick","maxVideoClick","minVideoClose","maxVideoClose","forceResetTime","forceCloseTime","loadErrSeconds1","loadErrSeconds2","loadErrSeconds3"
//        		"adType","linkType","simpleUrl"
        		"https://api1.jcgames.top/api/sen.do","ext","sen"
        };
        
        for(String data : datas) {
        	System.out.println(data+" 加密 " + tools.encode(data));
        }
//        String data1 = tools.encode(data);
// 
//        System.out.println("����:" + tools.decode(data1));
    }
 
 
}