package com.distributed.core.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
 
 
public class IOSEmojiUtil {
     
    public static String[] ios5emoji ;
    public static String[] ios4emoji ;
    public static String[] androidnullemoji ;
    public static String[] adsbuniemoji;
     
    public static void initios5emoji(String[] i5emj,String[] i4emj,String[] adnullemoji,String[] adsbemoji){
        ios5emoji = i5emj;
        ios4emoji = i4emj;
        androidnullemoji = adnullemoji;
        adsbuniemoji = adsbemoji;
    }
     
    //在ios上将ios5转换为ios4编码
    public static String transToIOS4emoji(String src) {
        return StringUtils.replaceEach(src, ios5emoji, ios4emoji);
    }
    //在ios上将ios4转换为ios5编码
    public static String transToIOS5emoji(String src) {
        return StringUtils.replaceEach(src, ios4emoji, ios5emoji);
    }
    //在android上将ios5的表情符替换为空
    public static String transToAndroidemojiNull(String src) {
        return StringUtils.replaceEach(src, ios5emoji, androidnullemoji);
    }
     
    //在android上将ios5的表情符替换为SBUNICODE
    public static String transToAndroidemojiSB(String src) {
        return StringUtils.replaceEach(src, ios5emoji, adsbuniemoji);
    }
     
    //在android上将SBUNICODE的表情符替换为ios5
    public static String transSBToIOS5emoji(String src) {
        return StringUtils.replaceEach(src, adsbuniemoji, ios5emoji);
    }
     
    //eg. param: 0xF0 0x9F 0x8F 0x80
    public static String hexstr2String(String hexstr) throws UnsupportedEncodingException{
        byte[] b = hexstr2bytes(hexstr);
        return new String(b, "UTF-8");
    }
     
    //eg. param: E018
    public static String sbunicode2utfString(String sbhexstr) throws UnsupportedEncodingException{
        byte[] b = sbunicode2utfbytes(sbhexstr);
        return new String(b, "UTF-8");
    }
     
    //eg. param: 0xF0 0x9F 0x8F 0x80
    public static byte[] hexstr2bytes(String hexstr){
        String[] hexstrs = hexstr.split(" ");
        byte[] b = new byte[hexstrs.length];
         
        for(int i=0;i<hexstrs.length;i++){
            b[i] = hexStringToByte(hexstrs[i].substring(2))[0];
        }
        return b;
    }
     
    //eg. param: E018
    public static byte[] sbunicode2utfbytes(String sbhexstr) throws UnsupportedEncodingException{
        int inthex = Integer.parseInt(sbhexstr, 16);
        char[] schar = {(char)inthex};
        byte[] b = (new String(schar)).getBytes("UTF-8");
        return b;
    }
     
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }
 
 
    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
    
    /**
     * 将emoji表情替换成*
     * 
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
        }else{
            return source;
        }
    }
     
    public static void main(String[] args) throws UnsupportedEncodingException {
    	
    	 try{
             String text = "This is a smiley \uD83C\uDFA6 face\uD860\uDD5D \uD860\uDE07 \uD860\uDEE2 \uD863\uDCCA \uD863\uDCCD \uD863\uDCD2 \uD867\uDD98 ";
             System.out.println(text);
             System.out.println(text.length());
             System.out.println(text.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]", "*"));
             System.out.println(filterEmoji(text));
         }catch (Exception ex){
             ex.printStackTrace();
         }
    	
        byte[] b1 = {-30,-102,-67}; //ios5 //0xE2 0x9A 0xBD     
        byte[] b2 = {-18,-128,-104}; //ios4 //"E018"
         
        //-------------------------------------
         
        byte[] b3 = {-16,-97,-113,-128}; //0xF0 0x9F 0x8F 0x80       
        byte[] b4 = {-18,-112,-86};         //E42A 
         
         
        ios5emoji = new String[]{new String(b1,"utf-8"),new String(b3,"utf-8")};
        ios4emoji = new String[]{new String(b2,"utf-8"),new String(b4,"utf-8")};  
         
         
        //测试字符串
        byte[] testbytes = {105,111,115,-30,-102,-67,32,36,-18,-128,-104,32,36,-16,-97,-113,-128,32,36,-18,-112,-86};
        String tmpstr = new String(testbytes,"utf-8");
        System.out.println(tmpstr);
         
         
        //转成ios4的表情
        String ios4str = transToIOS5emoji(tmpstr);
        byte[] tmp = ios4str.getBytes();
        //System.out.print(new String(tmp,"utf-8"));      
        for(byte b:tmp){
            System.out.print(b);
            System.out.print(" ");
        }
    }
     
}
 
