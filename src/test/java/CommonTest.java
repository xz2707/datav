import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.datav.common.util.HttpUtil;
import com.datav.common.util.JhttpUtil;
import com.datav.common.util.Symbol;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiaozhi on 2017/6/27.
 */
public class CommonTest {
    public static void main(String[] args) throws ParseException, IOException {
        test1();
    }
    private static void test1() throws ParseException, IOException {

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("130@1498813444");
        jsonArray.add("106@1498812676");
        jsonArray.add("baidu@1498815631");
        System.out.println(jsonArray.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",jsonArray);
//        String result = HttpUtil.urlGet("http://192.168.3.194:9591/data/DataSelectOrderInfo"+params);
//        String result = JhttpUtil.instance.sendHttpGetRequest("http://192.168.3.22:5000/data/DataSelectOrderInfo"+params);
//        JSONObject json = JhttpUtil.instance.sendHttpPostRequestByJson("http://192.168.3.22:5000/data/DataSelectOrderInfo",jsonObject.toString(),JSONObject.class,JSONObject.class);
//        JSONObject json = JhttpUtil.instance.sendHttpPostRequestByJson("http://192.168.3.22:5000/data/DataSelectOrderPoint",jsonObject.toString(),JSONObject.class,JSONObject.class);
//        JSONObject json = JhttpUtil.instance.sendHttpPostRequestByJson("http://192.168.3.194:9591/data/DataSelectOrderPoint",jsonObject.toString(),JSONObject.class,JSONObject.class);
        JSONObject json = JhttpUtil.instance.sendHttpPostRequestByJson("http://192.168.3.194:9591/data/DataSelectOrderInfo",jsonObject.toString(),JSONObject.class,JSONObject.class);
        System.out.println(json.toString());

//        double total_money = 100;
//        JSONArray cityArray = (JSONArray) json.get("data");
//        //数组里包含多个{"adcode":"610400","amount":5219,"shopnum":"130@1498615673"}
//        Map<String,Integer> adcodeMap = new HashMap<>();
//        int total_amount = 0;
//        for (Object o : cityArray) {
//            JSONObject cityJson = (JSONObject) o;
//            String adcode = String.valueOf(cityJson.get("adcode"));
//            int amount = Integer.parseInt(String.valueOf(cityJson.get("amount")));
//            String shopnum = String.valueOf(cityJson.get("shopnum"));
//            total_amount += amount;//购物车总金额
//            if(adcodeMap.containsKey(adcode)){
//                int t_amount = adcodeMap.get(adcode)+amount;
//                adcodeMap.put(adcode,t_amount);
//            }else{
//                adcodeMap.put(adcode,amount);
//            }
//        }
//        Map<String,Double> adcode_float_map = new HashMap<>();
//        for (Map.Entry<String, Integer> param : adcodeMap.entrySet()) {
//            adcode_float_map.put(param.getKey(), (double)param.getValue() / total_amount);
//        }
//        JSONObject cityJson = new JSONObject();
//        for (Map.Entry<String, Double> entry : adcode_float_map.entrySet()) {
//            cityJson.put(entry.getKey(), entry.getValue() * total_money);
//        }
//
//        System.out.println(cityJson);

    }
}
