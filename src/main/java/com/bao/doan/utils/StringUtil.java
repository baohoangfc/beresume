package com.bao.doan.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public final class StringUtil {

	/**
	 * 
	 * @param str
	 * @param maxLength
	 * @return
	 */
    public static String getShorterStringFront(String str, int maxLength) {
        if (str == null) return "";
        if (maxLength < 0) return str.trim();
        if (str.length() <= maxLength) return str;

        String s = str.substring(0, maxLength);
        char currentChar;
        int index;
        for (index = s.length() - 1; index >= 0; index--) {
            currentChar = s.charAt(index);
            if (Character.isWhitespace(currentChar)) {
                break;
            }
        }
        String shortString = s.substring(0, index + 1);
        return shortString + "...";
    }

    public static String getShorterStringBack(String str, int maxLength) {
        if (str == null) return "";
        if (maxLength < 0) return str.trim();
        if (str.length() <= maxLength) return str;
        String s = str.substring(str.length() - maxLength, str.length());
        char currentChar;
        int index;
        for (index = 0; index < s.length(); index++) {
            currentChar = s.charAt(index);
            if (Character.isWhitespace(currentChar)) {
                break;
            }
        }
        String shortString = s.substring(index + 1, s.length());
        return "..." + shortString;
    }

    public static String getShorterStringIgnoreSpace(String str, int maxLength) {
        if (str == null) return "";
        if (maxLength < 0) return str;
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength) + "...";
    }

    public static String SafeStringHTML(String str) {
        if (str == null) return new String();
        String temp = str;
        temp = temp.replaceAll("\'", "&#039;");
        temp = temp.replaceAll("\"", "&quot;");
        temp = temp.replaceAll("<", "&lt;");
        temp = temp.replaceAll(">", "&gt;");
        temp = temp.replaceAll("[\\\\]", "\\\\\\\\");
        return temp;
    }
   
    /**
     * Duyet query tu URL thanh cac cap tham so va gia tri
     */
    @SuppressWarnings("rawtypes")
	public static HashMap parseQuery(String query) throws Exception {
        if (query == null || "".equalsIgnoreCase(query)) return new HashMap(0);
        try {
            StringTokenizer sToken = new StringTokenizer(query, "&");
            String s = "", k = "", v = "";
            HashMap ret = new HashMap();
            for (; sToken.hasMoreTokens();) {
                s = (String) sToken.nextToken();
                if (s.indexOf("=") != -1) {
                    k = (s.substring(0, s.indexOf("="))).trim();
                    if (s.indexOf("=") < s.length() - 1)
                        v = s.substring(s.indexOf("=") + 1).trim();
                    else
                        v = "";
                    ret.put(k, v);
                } else {
                    ret.put(s, "");
                }
            }
            return ret;

        } catch (Exception e) {
            throw new Exception("Request invalid: " + query + " ERROR: "
                    + e.toString());
        }

    }
    
    public static String replaceAllString(String source, String substring, String replace)
	{
		//kiem tra neu ton tai xau null
		if (source == null || "".equals(source) 
			|| substring==null || "".equals(substring) 
			|| replace == null || "".equals(replace))
			return source;
		String strTemp = new String(source.trim());
		String strReturn= "";
		if (strTemp.indexOf(substring)==-1) 
		{
			return 	source;
		}
		//Ton tai xau con
		while (!strTemp.equals(""))	
		{
			int from = 0, to =strTemp.length();
			from = strTemp.indexOf(substring);
			if (from ==-1) 
			{
				return strReturn + strTemp;
			}
			strReturn += strTemp.substring(0,from);
			strReturn += replace;
			strTemp = strTemp.substring(from + substring.length(),to);
		}
		return strReturn;
	}

    public static String getLPaddingString(String source, int len, char padChar)
    {
    	if(source == null || "".equals(source.trim())) return null;
    	if(len < 1) return null;
    	
    	if(source.length() >= len) return source.substring(source.length() - len);
    	
    	String dest = source;
    	while (dest.length() < len){
    		dest = padChar + dest;
    	}
    	return dest;
    }
    
    /**
     * action dua chuoi ho ten ve dung dang: ho, ten dem cach nhau 1 khoang trong
     * @param hoVaTen
     * @return String
     * @author thachnn
     */
    public static String formatHoVaTen (String hoVaTen) {
    	String hvt = "";
    	String[] strArr = hoVaTen.trim().split(" ");
    	
    	if ( null != hoVaTen && !hoVaTen.isEmpty()) {
    		for (String string : strArr) {
    			if ( !"".equals(string.trim()) ) {
    				hvt += string+ " ";
    			}
    		}
    	}
    	
    	return hvt.trim();
    }
    
    /**
     * action dua chuoi string dung dang cach nhau 1 khoang trong
     * @param source
     * @return
     */
    public static String replaceWhiteSpace (String source) {
    	String output = "";
    	try {
	    	if ("".equals(source) || null == source ) {
	    		return "";
	    	}
	    	String[] strArr = source.trim().split(" ");
	    	
	    	for (String string : strArr) {
				if ( !"".equals(string.trim()) ) {
					output += string + " ";
				}
			}
	    	
    	}
    	catch (Exception e) {
			
		}
    	return output.trim();
    }
    /**
	 * action: tra ve gia tri tuy doi so dau vao, doi so=0 thi return false
	 * @param int
	 * @retuen boolean
	 * 
	 */
	public static boolean changeBool(int so) {
		if (0 == so) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * Method nay lay gia tri string cua mot object, neu la null thi lay gia tri
	 * mac dinh
	 * 
	 * 
	 * @param object : object
	 * @param defaultValue : gia tri mac dinh neu la null
	 * @return string
	 */
	public static String getSringEx(Object object,
									String defaultValue) {
		return String.valueOf(null != object
			? object
			: defaultValue);
	}
	
	public static String convertFromUTF8(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}
	 

	public static String convertToUTF8(String s) {
		String out = null;
		try {
			out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return out;
	}
	
	/**
     * Check String isNullOrEmpty
     * @author viethc
     * @date Aug 1, 2013
     * @param string
     * @return boolean
     */
    public static boolean isNullOrEmpty(String s) {
    	if(s == null || s.trim().equals(""))
    		return true;
    	else
    		return false;
    }
	
	/**
	 * Dinh dang kieu tien viet nam (1.000.000) * @author viethc
	 * 
	 * @param curentcy : so tien
	 * @param separator : dau phan cach phan ngan
	 * @return String
	 */
	public static String formatVND(Long curentcy,String separator) {
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		return replaceAllString(formatter.format(curentcy),",",separator);
	}
	public static String replaceAllWhiteSpace( String source){
		String output = source.replaceAll("\\s+", ""); 
		return output; 
	}
	
	/**
	 * Thuc hien trim kieu double(3.0 -> 3; 0.5 -> 0.5)
	 * @param d
	 * @return
	 * 
	 */
	public static String formatDoubleToString(double d)
	{
	    if(d == (int) d)
	        return String.format("%d",(int)d);
	    else
	        return String.format("%s",d);
	}	
	 /**
	 * @param key
	 * Get value key in properties
	 * @return
	 */
	public static String getStringResourceValue(String key)
	    {
	        Properties prop = new Properties();
	        try
	        {
	            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	            InputStream is= StringUtil.class.getClassLoader().getResourceAsStream("/qlnsnn-config.properties");
	            prop.load(is);
	            is.close();
	            return prop.getProperty(key);
	        }
	        catch(IOException e)
	        {
	            e.printStackTrace();
	        }
	        return null;
	    }
	/** convert chuoi  uppercase first word
	 * @param str
	 * @return
	 */
	public static String UpperCaseFirstWords( String str) {
   	 str = str.trim().toLowerCase();
        String data[] = str.split("\\s"); 
        String outputStr = "";
        for(int i =0;i< data.length;i++) {
            if(data[i].length()>1)
           	 outputStr = outputStr + data[i].substring(0,1).toUpperCase()+data[i].substring(1)+ " ";
            else
           	 outputStr = outputStr + data[i].toUpperCase();
        }
        return outputStr.trim();
   }
	
	
	public static double toNum(String vnNumStr) {
		double out;
		try {
			out = Double.parseDouble(
				vnNumStr.trim()
				.replace(".", "")
				.replace(",", ".")
			);
		} catch (NumberFormatException nfe) {
			out = 0D;
		}
		return out;
	}
	
	public static String toVNNum(double num) {
		String sNum = String.format("%.2f", num);
		String[] parts = sNum.split("\\.");
		
		sNum = String
			.format("%,15d", Long.parseLong(parts[0]))
			.trim()
			.replace(",", ".");
		
		return parts[1].equals("00") 
			? sNum 
			: sNum.concat(",").concat(parts[1]);
	}
    
	//Mang cac ky tu goc co dau
    private static String SOURCE_CHARACTERS = "ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵ"
    		+ "ẶặẸẹẺẻẼẽẾếỀềỂểỄễỆệỈỉỊịỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợỤụỦủỨứỪừỬửỮữỰựỶÝỲỷýỳ";
    
 // Mang cac ky tu thay the khong dau
    private static String DESTINATION_CHARACTERS = "aaaaeeeiioooouuyaaaaeeeiioooouuyaaddiiuuoouuaaaaaaaaaaaaaaaaaa"
    		+ "aaaaaaeeeeeeeeeeeeeeeeiiiioooooooooooooooooooooooouuuuuuuuuuuuuuyyyyyy";
    
    /**
     * Bo dau 1 ky tu
     * 
     * @param ch
     * @return
     */
    public static char removeAccent(char ch) {
        int index = SOURCE_CHARACTERS.indexOf(ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS.charAt(index);
        }
        
        return ch;
    }

    /**
     * Bo dau 1 chuoi
     * 
     * @param s
     * @return
     */
    public static String removeAccent(String s) {
    	s=s.trim();
        StringBuilder sb = new StringBuilder(s);
        
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }
    
    public static String createTsQuery(String s){
    	s=s.trim();
    	s = s.replace('!', ' ');
    	s = s.replace('@', ' ');
    	s = s.replace('#', ' ');
    	s = s.replace('$', ' ');
    	s = s.replace('^', ' ');
    	s = s.replaceAll("&quot;", "\"");
    	s = s.replace('&', ' ');
    	s = s.replace('*', ' ');
    	s = s.replace('-', ' ');
    	s = s.replace('|', ' ');
    	s = s.replace('\'', ' ');
    	s = s.replace('\\', ' ');
    	s = s.replace('(', ' ');
    	s = s.replace(')', ' ');
    	s = s.replaceAll("\\s+", " ");
    	s = s.replaceAll(" ", "\\|");
//    	s = s.replaceAll(" ", "\\&");
    	
    	char[] result = s.toCharArray();

    	int beginquote = -1;
    	int endquote = -1;
    	for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '"' && endquote >= beginquote) {
            	beginquote = i;
            	result[i] = '(';
            }else if (s.charAt(i) == '"' && endquote <= beginquote) {
            	endquote = i;
            	beginquote = i;
            	result[i] = ')';
            }else{
	            if(beginquote > endquote && s.charAt(i)=='|'){
	            	result[i] = '&';
	            }
            }
        }
    	s= String.valueOf(result);
    	
    	if(beginquote>endquote) s += ")";
    	s = s.trim();
    	return s;
    }
    
    public static String bbcode(String text, String Quote) {
        String html = text;

        Map<String,String> bbMap = new HashMap<String , String>();

        bbMap.put("\\[b\\]([^~]+?)\\[/b\\]", "<strong>$1</strong>");
        bbMap.put("\\[i\\]([^~]+?)\\[/i\\]", "<span style="+Quote+"font-style:italic;"+Quote+">$1</span>");
        bbMap.put("\\[u\\]([^~]+?)\\[/u\\]", "<span style="+Quote+"text-decoration:underline;"+Quote+">$1</span>");
        bbMap.put("\\[h1\\]([^~]+?)\\[/h1\\]", "<h1>$1</h1>");
        bbMap.put("\\[h2\\]([^~]+?)\\[/h2\\]", "<h2>$1</h2>");
        bbMap.put("\\[h3\\]([^~]+?)\\[/h3\\]", "<h3>$1</h3>");
        bbMap.put("\\[h4\\]([^~]+?)\\[/h4\\]", "<h4>$1</h4>");
        bbMap.put("\\[h5\\]([^~]+?)\\[/h5\\]", "<h5>$1</h5>");
        bbMap.put("\\[h6\\]([^~]+?)\\[/h6\\]", "<h6>$1</h6>");
        bbMap.put("\\[quote\\]([^~]+?)\\[/quote\\]", "<blockquote>$1</blockquote>");
        bbMap.put("\\[p\\]([^~]+?)\\[/p\\]", "<p>$1</p>");
        bbMap.put("\\[p=(.+?),(.+?)\\]([^~]+?)\\[/p\\]", "<p style="+Quote+"text-indent:$1px;line-height:$2%;"+Quote+">$3</p>");
        bbMap.put("\\[center\\]([^~]+?)\\[/center\\]", "<div align="+Quote+"center"+Quote+">$1</div>");
        bbMap.put("\\[align=(.+?)\\]([^~]+?)\\[/align\\]", "<div align="+Quote+"$1"+Quote+">$2</div>");
        bbMap.put("\\[color=(.+?)\\]([^~]+?)\\[/color\\]", "<span style="+Quote+"color:$1;"+Quote+">$2</span>");
        bbMap.put("\\[size=(.+?)\\]([^~]+?)\\[/size\\]", "<span style="+Quote+"font-size:$1 !important;"+Quote+">$2</span>");
        bbMap.put("\\[img\\](.+?)\\[/img\\]", "<img src="+Quote+"$1"+Quote+" />");
        bbMap.put("\\[img=(.+?),(.+?)\\]([^~]+?)\\[/img\\]", "<img width="+Quote+"$1"+Quote+" height="+Quote+"$2"+Quote+" src="+Quote+"$3"+Quote+" />");
        bbMap.put("\\[email\\]([^~]+?)\\[/email\\]", "<a href="+Quote+"mailto:$1"+Quote+">$1</a>");
        bbMap.put("\\[email=(.+?)\\]([^~]+?)\\[/email\\]", "<a href="+Quote+"mailto:$1"+Quote+">$2</a>");
        bbMap.put("\\[url\\](.+?)\\[/url\\]", "<a href="+Quote+"$1"+Quote+">$1</a>");
        bbMap.put("\\[url=(.+?)\\]([^~]+?)\\[/url\\]", "<a href="+Quote+"$1"+Quote+">$2</a>");
        bbMap.put("\\[youtube\\]([^~]+?)\\[/youtube\\]", "<object width="+Quote+"640"+Quote+" height="+Quote+"380"+Quote+"><param name="+Quote+"movie"+Quote+" value="+Quote+"http://www.youtube.com/v/$1"+Quote+"></param><embed src="+Quote+"http://www.youtube.com/v/$1"+Quote+" type="+Quote+"application/x-shockwave-flash"+Quote+" width="+Quote+"640"+Quote+" height="+Quote+"380"+Quote+"></embed></object>");
        bbMap.put("\\[video\\]([^~]+?)\\[/video\\]", "<video src="+Quote+"$1"+Quote+" />");
        bbMap.put("(\r\n|\r|\n|\n\r)", "<br/>");
        
        for (Map.Entry<String,String> entry: bbMap.entrySet()) {
            html = html.replaceAll(entry.getKey().toString(), entry.getValue().toString());
        }

        return html;
    }
    
    public static long[] arrayStringtoArrLong(String[] arrString){
    	long[] arrLongs = new long[arrString.length];
    	for (int i = 0; i < arrString.length; i++) {
    		arrLongs[i] = Long.valueOf(arrString[i]); 
		}
    	return arrLongs;
    }
    
    public static String[] removeNullValue(String[] firstArray){
    	List<String> list = new ArrayList<String>();
    	for(String s : firstArray) {
    		if(null != s && s.length() > 0) {
    			list.add(s);
    		}
    	}
    	firstArray = list.toArray(new String[list.size()]);
    	return firstArray;
    }
    
	public static String convertProperCase(String input) {
		StringBuffer buffy = new StringBuffer(input); // You may want to .toLowerCase() - up to you
		Matcher templateMatch = Pattern.compile("\\b[\\w]").matcher(input);
		while(templateMatch.find()) {
			buffy.replace(templateMatch.end() - 1, templateMatch.end(), templateMatch.group().toUpperCase());
		}
		
		return buffy.toString();
	}

	public static String genCodeFromVNString(String vnString) {
		String code = removeAccent(vnString);
		code = code.toLowerCase();
		return code.replaceAll("[^a-z0-9]", "");
	}
}
