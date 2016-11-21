package com.wangjunneil.schedule.entity.baidu;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.utility.StringUtil;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import  java.util.UUID;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class SysParams {
    private String cmd;      //命令
    private int timestamp;   //请求时间戳
    private int version = 2; //api版本号,当前为2
    private String ticket;   //请求唯一标识
    private  int source;     //合作方帐号
    private  String sign;    //签名
    private  Object body;    //请求参数


    public  void  setCmd(String cmd){
        this.cmd = cmd;
    }

    public String getCmd(){
        return  this.cmd;
    }

    public int getTimestamp(){
        if(timestamp == 0){
          this.timestamp = (int)(System.currentTimeMillis() / 1000);
        }
        return  timestamp;
    }

    public void setTimestamp(int timestamp){
        this.timestamp = timestamp;
    }

    public int getVersion(){
        return  this.version;
    }

    public  void setTicket(String ticket){

        this.ticket = ticket;
    }

    public String getTicket(){
        if(StringUtil.isEmpty(ticket)){ return UUID.randomUUID().toString().toUpperCase();}
        return  this.ticket;
    }

    public void setSource(int source){
       this.source = Constants.BAIDU_SOURCE;
    }

    public int getSource(){
        if(this.source == 0){this.source = Constants.BAIDU_SOURCE;}
        return  this.source;
    }

    public  void setSign(String sign){
         this.sign = sign;
    }

    public  String getSign(){
        return this.sign;
    }

    public  void setBody(Object object){
       this.body = object;
    }

    public Object getBody(){
        return this.body;
    }

    /**
     * 计算MD5
     * @param input
     * @return
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把中文转成Unicode码
     * @param str
     * @return
     */
    public static String chinaToUnicode(String str){
        String result="";
        for (int i = 0; i < str.length(); i++){
            int chr1 = (char) str.charAt(i);
            if(chr1>=19968&&chr1<=171941){//汉字范围 \u4e00-\u9fa5 (中文)
                result+="\\u" + Integer.toHexString(chr1);
            }else{
                result+=str.charAt(i);
            }
        }
        return result;
    }
}
