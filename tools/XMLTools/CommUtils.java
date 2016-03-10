package com.api.hotel.zhuzher.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.wxap.util.MD5Util;

/**
 * 住哲通用的一些工具类
 * 
 * @author shizhiping
 * 
 */
public class CommUtils {

	/**
	 * 获取apiGoBack
	 * 
	 * @param interfaceInfo
	 * @param method
	 *            {metho, xml}
	 * @param parames
	 * @return
	 */
	public static String getApiGoBack(HashMap<String, String> interfaceInfo, String[] method, String[][] parames) {
		try {
			/*
			 * 组装请求XML
			 */
			String data = setXMLText(method[1], parames);
			/*
			 * 组装请求参数
			 */
			Long time = System.currentTimeMillis(); // 时间戳，防串改数据
			String authCode = MD5Util.MD5Encode(interfaceInfo.get("cId") + method[0] + time + interfaceInfo.get("key"), "UTF-8"); // md5加密，获取授权码
			data = DESUtils.desCrypto(data, interfaceInfo.get("dataKey"));// 加密
			data = java.net.URLEncoder.encode(data, "UTF-8");
			String reqParames = "cId=" + interfaceInfo.get("cId") + "&authCode=" + authCode + "&method=" + method[0] + "&time=" + time + "&data=" + data;// 拼好请求字符串
			/*
			 * 发送请求, 获取结果
			 */
			return HttpUtils.postRequest(interfaceInfo.get("url"), reqParames);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 得到请求参数
	 * 
	 * @param interfaceInfo
	 * @param method
	 * @param data
	 * @return
	 */
	public static String getReqParames(HashMap<String, String> interfaceInfo, String method, String data) {
		String reqParames = "";
		try {
			Long time = System.currentTimeMillis(); // 时间戳，防串改数据
			String authCode = MD5Util.MD5Encode(interfaceInfo.get("cId") + method + time + interfaceInfo.get("key"), "UTF-8"); // md5加密，获取授权码
			data = DESUtils.desCrypto(data, interfaceInfo.get("dataKey"));
			data = java.net.URLEncoder.encode(data, "UTF-8");
			reqParames = "cId=" + interfaceInfo.get("cId") + "&authCode=" + authCode + "&method=" + method + "&time=" + time + "&data=" + data;// 拼好请求字符串
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reqParames;
	}
	/**
	 * 获取XML元素列表
	 * 
	 * @param xml
	 * @param eleName
	 * @return
	 */
	public static List<String> getXMLEleList(String xml, String eleName) {
		List<String> list = new ArrayList<String>();
		int[] pos = {-1, -1};
		while (true) {
			pos = getEleTextPos(xml, eleName, pos[0], pos[1]);
			if (pos[0] < 0) {
				break;
			}
			list.add(xml.substring(pos[0], pos[1]));
		}
		return list;
	}

	/**
	 * 查找XML元素值
	 * 
	 * @return
	 */
	public static String getXMLEleText(String xml, String eleName) {
		int[] pos = getEleTextPos(xml, eleName);
		if (pos[0] != -1) {
			return xml.substring(pos[0], pos[1]);
		}
		return "";
	}

	/**
	 * 获取XML元素值位置
	 * 
	 * @param xml
	 * @param eleName
	 * @return
	 */
	private static int[] getEleTextPos(String xml, String eleName) {
		int[] pos = {-1, -1};
		eleName = "<" + eleName + ">";// 组装成为 <pageNo>
		int startindex = xml.indexOf(eleName);// 查詢<pageNo>的位置 , -1則代表沒有此字符
		if (startindex < 0) {
			return pos;
		}
		eleName = eleName.replace("<", "</");
		int endindex = xml.indexOf(eleName);
		if (endindex < 0) {
			return pos;
		}
		pos[0] = startindex + eleName.length() - 1;
		pos[1] = endindex;
		return pos;
	}

	/**
	 * 获取XML元素值位置
	 * 
	 * @param xml
	 * @param eleName
	 * @return
	 */
	private static int[] getEleTextPos(String xml, String eleName, int fromIndex1, int fromIndex2) {
		int[] pos = {-1, -1};
		eleName = "<" + eleName + ">";// 组装成为 <pageNo>
		int startindex = xml.indexOf(eleName, fromIndex1 + 1);// 查詢<pageNo>的位置 ,
		if (startindex < 0) {
			return pos;
		}
		eleName = eleName.replace("<", "</");
		int endindex = xml.indexOf(eleName, fromIndex2 + 1);
		if (endindex < 0) {
			return pos;
		}
		pos[0] = startindex + eleName.length() - 1;
		pos[1] = endindex;
		return pos;
	}

	/**
	 * 设置XML元素值
	 * 
	 * @param xml
	 *            XML文本
	 * @param elearr { {
	 *            "head", "头部" }, { "brandId", "品牌" }, { "pageNo", "100" } }
	 * @return XML文本
	 */
	public static String setXMLText(String xml, String[][] elearr) {
		if (xml == null && elearr == null) {
			return xml;
		}
		for (String[] ele : elearr) {
			// 组装成为<pageNo>10</pageNo>
			String eleNameXML = "<".concat(ele[0]).concat(">");// <pageNo>
			String eleNameXMLEnd = eleNameXML.replace("<", "</");// </pageNo>
			String target = eleNameXML.concat(getXMLEleText(xml, ele[0])).concat(eleNameXMLEnd);// <pageNo>10</pageNo>
			String replacement = eleNameXML.concat(ele[1]).concat(eleNameXMLEnd);// <pageNo>123456</pageNo>
			xml = xml.replace(target, replacement);
		}
		return xml;
	}

	/**
	 * JSON转XML
	 * 
	 * @param json
	 * @return
	 */
	public static String json2XML(String json) {
		JSONObject jobj = JSONObject.fromObject(json);
		String xml = new XMLSerializer().write(jobj);
		return xml;
	}

	/**
	 * XML转JSON
	 * 
	 * @param xml
	 * @return
	 */
	public static String xml2JSON(String xml) {
		return new XMLSerializer().read(xml).toString();
	}
}
