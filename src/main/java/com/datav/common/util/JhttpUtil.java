package com.datav.common.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;

@Component("jhttpUtil")
public class JhttpUtil {
	private static final Log log = LogFactory.getLog(JhttpUtil.class);


	public static JhttpUtil instance=new JhttpUtil();

	public  String convertStreamToString(InputStream is,String charset) throws UnsupportedEncodingException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,charset));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	private String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	public  String sendHttpGetRequest(String url) throws IOException {
		// 创建Get方法实例
		HttpGet httpgets = new HttpGet(url);
		HttpResponse response;
		String str = "";
		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient httpClient = new DefaultHttpClient();
		try {
			response = httpClient.execute(httpgets);
		} catch (ClientProtocolException e) {
			httpgets.abort();
			throw e;
		} catch (IOException e) {
			httpgets.abort();
			throw e;
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instreams = null;
			try {
				instreams = entity.getContent();
				str = convertStreamToString(instreams,"utf-8");
				httpgets.abort();
			} catch (Exception e) {
				httpgets.abort();
				log.error("读取http请求失败", e);
			} finally {
				if (null != instreams) {
					instreams.close();
				}
			}
		}
		if (str.contains("!DOCTYPE html")) {
			return null;
		}
		return str;
	}
	public <T, I> T sendHttpPostRequestByJson(String url, String params,
											  Class<T> classType, Class<I> listType) throws IOException {

		HttpPost post = new HttpPost(url);
		// 接收参数json列表

		StringEntity entity = new StringEntity(params,"utf-8");//解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		post.setEntity(entity);
		HttpResponse response;
		@SuppressWarnings({ "deprecation", "resource" })
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			log.error("ClientProtocolException"+e);
			throw e;
		} catch (IOException e) {
			log.error("IOException"+e);
			throw e;
		}
		// 若状态码为200 ok
		if (response.getStatusLine().getStatusCode() == 200) {
			// 取出回应字串
			try {
				HttpEntity result = response.getEntity();
				String str = "";
				if (result != null) {

					InputStream instreams = null;
					try {

						instreams = result.getContent();
						str = convertStreamToString(instreams);
					} catch (Exception e) {
						log.error("读取校园网http请求失败", e);
					} finally {
						if (null != instreams) {
							instreams.close();
						}
						post.releaseConnection();
						httpClient.close();
					}
				}

				if (classType.equals(ArrayList.class)) {
					return (T) JSON.parseArray(str, listType);
				} else {
					return JSON.parseObject(str, classType);
				}
			} catch (ParseException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}
		}else{
			log.error("发送POST请求,错误状态码!!"+response.getStatusLine().getStatusCode());
		}

		return null;
	}

	public <T, I> T sendHttpPutRequestByJson(String url, String params,
											  Class<T> classType, Class<I> listType) throws IOException {

		HttpPut put = new HttpPut(url);
		// 接收参数json列表
		StringEntity entity = new StringEntity(params,"utf-8");//解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");

		put.setEntity(entity);
		HttpResponse response;
		@SuppressWarnings({ "deprecation", "resource" })
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			response = httpClient.execute(put);
		} catch (ClientProtocolException e) {
			log.error("ClientProtocolExceptions"+e);
			throw e;
		} catch (IOException e) {
			log.error("IOExceptions"+e);
			throw e;
		}
		// 若状态码为200 ok
		if (response.getStatusLine().getStatusCode() == 200) {
			// 取出回应字串
			try {
				HttpEntity result = response.getEntity();
				String str = "";
				if (result != null) {

					InputStream instreams = null;
					try {

						instreams = result.getContent();
						str = convertStreamToString(instreams);
					} catch (Exception e) {
						log.error("读取http请求失败", e);
					} finally {
						if (null != instreams) {
							instreams.close();
						}
						put.releaseConnection();
						httpClient.close();
					}
				}

				if (classType.equals(ArrayList.class)) {
					return (T) JSON.parseArray(str, listType);
				} else {
					return JSON.parseObject(str, classType);
				}
			} catch (ParseException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}
		}else{
			log.error("发送put请求,错误状态码!!"+response.getStatusLine().getStatusCode());
		}

		return null;
	}
	public <T, I> T sendHttpPostRequestByJsonTest(String url, String params,
											  Class<T> classType, Class<I> listType) throws IOException {

		HttpPost post = new HttpPost(url);
		// 接收参数json列表

		StringEntity entity = new StringEntity(params,"utf-8");//解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		post.setEntity(entity);


		HttpResponse response;
		@SuppressWarnings({ "deprecation", "resource" })
		DefaultHttpClient httpClient = new DefaultHttpClient();

		try {
			response = httpClient.execute(post);
		} catch (ClientProtocolException e) {
			log.error("ClientProtocolException"+e);
			throw e;
		} catch (IOException e) {
			log.error("IOException"+e);
			throw e;
		}
		// 若状态码为200 ok
		if (response.getStatusLine().getStatusCode() == 200) {
			// 取出回应字串
			try {
				HttpEntity result = response.getEntity();
				String str = "";
				if (result != null) {

					InputStream instreams = null;
					try {

						instreams = result.getContent();
						str = convertStreamToString(instreams);

					} catch (Exception e) {
						log.error("读取校园网http请求失败", e);
					} finally {
						if (null != instreams) {
							instreams.close();
						}
						post.releaseConnection();
						httpClient.close();
					}
				}

				if (classType.equals(ArrayList.class)) {
					return (T) JSON.parseArray(str, listType);
				} else {
					return JSON.parseObject(str, classType);
				}
			} catch (ParseException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}
		}else{
			log.error("发送POST请求,错误状态码!!"+response.getStatusLine().getStatusCode());
		}

		return null;
	}
	/**
	 *
	 * @param <I>
	 * @param url
	 * @param classType
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public <T, I> T sendHttpGetRequest(String url, Class<T> classType,
									   Class<I> listType) throws IOException {

		// 创建HttpClient实例
		@SuppressWarnings({ "deprecation", "resource" })
		HttpClient httpClient = new DefaultHttpClient();

		// 创建Get方法实例
		HttpGet httpgets = new HttpGet(url);
		HttpResponse response;
		try {
			response = httpClient.execute(httpgets);
			if (response.getStatusLine().getStatusCode() != 200) {
				httpgets.abort();
				return null;
			}
		} catch (ClientProtocolException e) {
			httpgets.abort();
			throw e;
		} catch (IOException e) {
			httpgets.abort();
			throw e;
		}
		HttpEntity entity = response.getEntity();
		String str = "";
		if (entity != null) {
			InputStream instreams = null;
			try {

				instreams = entity.getContent();
				str = convertStreamToString(instreams);
				// Do not need the rest
				httpgets.abort();

			} catch (Exception e) {
				httpgets.abort();
				log.error("读取校园网http请求失败", e);
			} finally {
				if (null != instreams) {
					instreams.close();
				}
			}
		}

		if (str.contains("!DOCTYPE html")) {
			return null;
		}

		if (classType.equals(ArrayList.class)) {
			return (T) JSON.parseArray(str, listType);
		} else {

//			return JSON.parseObject(str, classType);
			return  new Gson().fromJson(str, classType);
		}

	}
}