import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;


@SuppressWarnings("unused")
public class Api_Client {
    protected String api_url = "https://api.bithumb.com";
    protected String api_key;
    protected String api_secret;

    public Api_Client(String api_key, String api_secret) {
	this.api_key = api_key;
	this.api_secret = api_secret;
    }

    /**
     * 현재의 시간을 ns로 리턴한다.(1/1,000,000,000 초)
     * 
     * @return int
     */
    private String usecTime() {
    	/*
		long start = System.nanoTime();
		// do stuff
		long nanoseconds = System.nanoTime();
		long microseconds = TimeUnit.NANOSECONDS.toMicros(nanoseconds);
		long seconds = TimeUnit.NANOSECONDS.toSeconds(nanoseconds);
	
		int elapsedTime = (int) (microseconds + seconds);
	
		System.out.println("elapsedTime ==> " + microseconds + " : " + seconds);
		*/
    	
		return String.valueOf(System.currentTimeMillis());
    }

    private String request(String strHost, String strMemod, HashMap<String, Object> rgParams,  HashMap<String, Object> httpHeaders) {
    	String response = "";

		// SSL 여부
		if (strHost.startsWith("https://")) {
		    HttpRequest request = HttpRequest.get(strHost);
		    // Accept all certificates
		    request.trustAllCerts();
		    // Accept all hostnames
		    request.trustAllHosts();
		}
	
		if (strMemod.toUpperCase().equals("HEAD")) {
		} else {
		    HttpRequest request = null;
	
		    // POST/GET 설정
		    if (strMemod.toUpperCase().equals("POST")) {
	
			request = new HttpRequest(strHost, "POST");
			request.readTimeout(10000);
	
			System.out.println("POST ==> " + request.url());
	
			if (httpHeaders != null && !httpHeaders.isEmpty()) {
			    httpHeaders.put("api-client-type", "2");
			    request.headers(httpHeaders);
			    System.out.println(httpHeaders.toString());
			}
			if (rgParams != null && !rgParams.isEmpty()) {
			    request.form(rgParams);
			    System.out.println(rgParams.toString());
			}
		    } else {
			request = HttpRequest.get(strHost
				+ Util.mapToQueryString(rgParams));
			request.readTimeout(10000);
	
			System.out.println("Response was: " + response);
		    }
	
		    if (request.ok()) {
			response = request.body();
		    } else {
			response = "error : " + request.code() + ", message : "
				+ request.body();
		    }
		    request.disconnect();
		}
	
		return response;
    }
    
    public static String encodeURIComponent(String s)
    {
      String result = null;
   
      try
      {
        result = URLEncoder.encode(s, "UTF-8")
                           .replaceAll("\\+", "%20")
                           .replaceAll("\\%21", "!")
                           .replaceAll("\\%27", "'")
                           .replaceAll("\\%28", "(")
                           .replaceAll("\\%29", ")")
                           .replaceAll("\\%26", "&")
                           .replaceAll("\\%3D", "=")
                           .replaceAll("\\%7E", "~");
      }
   
      // This exception should never occur.
      catch (UnsupportedEncodingException e)
      {
        result = s;
      }
   
      return result;
    }

    private HashMap<String, Object> getHttpHeaders(String endpoint, HashMap<String, Object> rgData, String apiKey, String apiSecret) {
	    	
		String strData = Util.mapToQueryString(rgData).replace("?", "");
		String nNonce = usecTime();
		
		strData = strData.substring(0, strData.length()-1);
	
	
		System.out.println("1 : " + strData);
		
		strData = encodeURIComponent(strData);
		
		HashMap<String, Object> array = new HashMap<String, Object>();
	
		
		String str = endpoint + ";"	+ strData + ";" + nNonce;
		//String str = "/info/balance;order_currency=BTC&payment_currency=KRW&endpoint=%2Finfo%2Fbalance;272184496";
		
        String encoded = asHex(hmacSha512(str, apiSecret));
		
		System.out.println("strData was: " + str);
		System.out.println("apiSecret was: " + apiSecret);
		array.put("Api-Key", apiKey);
		array.put("Api-Sign", encoded);
		array.put("Api-Nonce", String.valueOf(nNonce));
	
		return array;
		
    }
    
    private static final String DEFAULT_ENCODING = "UTF-8";
	private static final String HMAC_SHA512 = "HmacSHA512";
	 
	public static byte[] hmacSha512(String value, String key){
	    try {
	        SecretKeySpec keySpec = new SecretKeySpec(
	                key.getBytes(DEFAULT_ENCODING),
	                HMAC_SHA512);
	 
	        Mac mac = Mac.getInstance(HMAC_SHA512);
	        mac.init(keySpec);
	
	        final byte[] macData = mac.doFinal( value.getBytes( ) );
	        byte[] hex = new Hex().encode( macData );
	        
	        //return mac.doFinal(value.getBytes(DEFAULT_ENCODING));
	        return hex;
	 
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException(e);
	    } catch (InvalidKeyException e) {
	        throw new RuntimeException(e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException(e);
	    }
	}
	 
	public static String asHex(byte[] bytes){
	    return new String(Base64.encodeBase64(bytes));
	}

    @SuppressWarnings("unchecked")
    public HashMap<String, Object> callApi_buy(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		//String units=params.get("units");
		System.out.println(rgParams2.get("units")+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(rgParams2.get("price")+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(rgParams2.get("type")+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
	
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
		HashMap<String, Object> result = null;
	    HashMap<String, Object> result1;
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
			System.out.println("시발시발시발시발시바리시발"+rgParams2);
			System.out.println("시발시발시발시발시바리시발"+rgParams);
			System.out.println("==== 결과 출력 ====");
			System.out.println(result.get("status"));
			//result1=(HashMap<String, Object>) result.get("data");
			//System.out.println(result1.get("balance"));
			//System.out.println();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		return result;
    }
    
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> callApi_sell(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		//String units=params.get("units");
		System.out.println(rgParams2.get("units"));
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
		System.out.println("시발시발시발시발시바리시발"+rgParams2);
		System.out.println("시발시발시발시발시바리시발"+rgParams);
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
		HashMap<String, Object> result = null;
	    HashMap<String, Object> result1;
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
	
			System.out.println("==== 결과 출력 ====");
			System.out.println(result.get("status"));
			//result1=(HashMap<String, Object>) result.get("data");
			//System.out.println(result1.get("balance"));
			//System.out.println();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		return result;
    }
    
    @SuppressWarnings("unchecked")
    public String callApi_Account(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		String balance="";
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
	
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
	
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    HashMap<String, Object> result;
		    HashMap<String, Object> result1;
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
	
			result1=(HashMap<String, Object>) result.get("data");
			balance=(String) result1.get("balance");
			//Main.result_Jtext.append(result1+"\n");
			//Main.result_Jtext.append(balance+"\n");
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		return balance;
    }
    
    @SuppressWarnings("unchecked")
    public String callApi_Balnace(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		String total_krw="";
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
	
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
	
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    HashMap<String, Object> result;
		    HashMap<String, Object> result1;
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
	
			result1=(HashMap<String, Object>) result.get("data");
			total_krw=(String) result1.get("total_krw");
			System.out.println(result1);
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		System.out.println(total_krw);
		return total_krw;
		
    }
    
    @SuppressWarnings("unchecked")
    public String callApi_Balnace_last(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		
		String xcoin_last="";
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
	
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
	
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    HashMap<String, Object> result;
		    HashMap<String, Object> result1;
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
	
			result1=(HashMap<String, Object>) result.get("data");
			System.out.println(result.get("data"));
			xcoin_last= result1.get("xcoin_last").toString();
			System.out.println(xcoin_last);
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		return xcoin_last;
    }
    
    
    
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> callApi_order_check(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		//String units=params.get("units");
		//System.out.println(rgParams2.get("units"));
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
	
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
		HashMap<String, Object> result = null;
	    HashMap<String, Object> result1;
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
	
		
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		return result;
    }
    
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> callApi_marketbuy(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		//String units=params.get("units");
		System.out.println("callapi_buy안에서 unit "+rgParams2.get("units"));
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
	
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
		HashMap<String, Object> result = null;
	    HashMap<String, Object> result1;
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
	
			System.out.println("==== 결과 출력 ====");
			System.out.println(result.get("status"));
	
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		return result;
    }
    
    @SuppressWarnings("unchecked")
    public HashMap<String, Object> callApi_marketsell(String endpoint, HashMap<String, Object> rgParams2) {
		String rgResultDecode = "";
		//String units=params.get("units");
		System.out.println(rgParams2.get("units"));
		HashMap<String, Object> rgParams = new HashMap<String, Object>();
		rgParams.put("endpoint", endpoint);
	
		if (rgParams2 != null) {
		    rgParams.putAll(rgParams2);
		}
	
		String api_host = api_url + endpoint;
		HashMap<String, Object> httpHeaders = getHttpHeaders(endpoint, rgParams, api_key, api_secret);
	
		rgResultDecode = request(api_host, "POST", rgParams, httpHeaders);
		HashMap<String, Object> result = null;
	    HashMap<String, Object> result1;
		if (!rgResultDecode.startsWith("error")) {
		    // json 파싱
		    
		    try {
			result = new ObjectMapper().readValue(rgResultDecode,
				HashMap.class);
	
			System.out.println("==== 결과 출력 ====");
			System.out.println(result.get("status"));
			//result1=(HashMap<String, Object>) result.get("data");
			//System.out.println(result1.get("balance"));
			//System.out.println();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
		}
		return result;
    }
}
