package com.weiun.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description 正则解析工具
 * 
 * @author Frank
 * @version 1.0
 * @create_time 2010-2-5
 */
public class PatternUtils {

	/**
	 * 捕获正则表达式中的指定字符串
	 * 
	 * @param sourceStr
	 * @param regex
	 * @return
	 */
	public static List<String> parseStrs(String sourceStr, String regex) {
		List<String> ret = new ArrayList<String>();
		StringBuilder input = new StringBuilder(sourceStr);
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			String match = input.substring(start, end).trim();
			ret.add(match);
		}

		return ret;
	}

	/**
	 * 捕获正则表达式中的指定字符串
	 * 
	 * @param sourceStr
	 * @param regex
	 * @param group
	 * @return
	 */
	public static List<String> parseStrs(String sourceStr, String regex,
			int group) {
		List<String> ret = new ArrayList<String>();
		StringBuilder input = new StringBuilder(sourceStr);
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		while (matcher.find() && group <= matcher.groupCount()) {
			ret.add(matcher.group(group).trim());
		}

		return ret;
	}

	/**
	 * 捕获正则表达式中的指定字符串
	 * 
	 * @param sourceStr
	 * @param regex
	 * @return
	 */
	public static String parseStr(String sourceStr, String regex) {
		String ret = "";
		StringBuilder input = new StringBuilder(sourceStr);
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {

			int start = matcher.start();
			int end = matcher.end();
			ret = input.substring(start, end).trim();
		}

		return ret;
	}

	/**
	 * 捕获正则表达式中的指定字符串
	 * 
	 * @param sourceStr
	 * @param regex
	 * @param group
	 * @return
	 */
	public static String parseStr(String sourceStr, String regex, int group) {
		String ret = "";
		StringBuilder input = new StringBuilder(sourceStr);
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find() && group <= matcher.groupCount()) {

			ret = matcher.group(group).trim();
		}

		return ret;
	}

	/**
	 * 正则验证
	 * 
	 * @param regex
	 * @param src
	 * @param match
	 *            true为全匹配， false为包含
	 * @return
	 */
	public static boolean regex(String regex, String src, boolean match) {

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(src);

		if (match) {
            return matcher.matches();
        }

		return matcher.find();
	}

	/**
	 * 将输入字符串中的所有捕获组进行替换
	 * 
	 * @param regex
	 * @param rpStr
	 * @param content
	 * @return
	 */
	public static String replace(String regex, String rpStr, String content) {

		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(content);
		content = m.replaceAll(rpStr);
		return content;

	}
	
	public static void main(String[] args){
		String content = "2013-01-13 23:59:55  [ http--8080-25$88356931:364431255 ] - [ INFO ]  [SQT] 0 [IP]171.88.21.213 [U]http://171.88.21.213:8080/sqt/industry/loadNewsContent.do [C]andriod [S]sqt ";
		//List<String> list = PatternUtils.parseStrs(new String(content), "\\s+\\[C\\][a-zA-Z0-9/]+\\s+");
		//List<String> list = PatternUtils.parseStrs(new String(content), "\\s+\\[U\\]http://sqt\\.9tong\\.com/[a-zA-Z0-9/]+\\.do\\s+");
		List<String> list = PatternUtils.parseStrs(new String(content), "\\s+\\[U\\]http://[a-zA-Z0-9:/\\.]+\\.do\\s+");

		System.out.println("---------------------------");
		System.out.println(list);

	}
}
