package javatools.jsonListMapUtil;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public class JsonListUtil {

    public static void main(String[] args) {
        String jsonStr = "[																" +
                "    {                                                                  " +
                "        'id': -3,                                                      " +
                "        'name': '校对-1',                                              " +
                "        'node': 'check',                                               " +
                "        'personnel': [                                                 " +
                "            {                                                          " +
                "                'id': 91,                                              " +
                "                'code': 'A127',                                        " +
                "                'name': '包晓晓'                                       " +
                "            }                                                          " +
                "        ]                                                              " +
                "    },                                                                 " +
                "    {                                                                  " +
                "        'id': -4,                                                      " +
                "        'name': '审核-1',                                              " +
                "        'node': 'examine',                                             " +
                "        'personnel': [                                                 " +
                "            {                                                          " +
                "                'id': 88,                                              " +
                "                'code': 'A125',                                        " +
                "                'name': '陈曦'                                         " +
                "            }                                                          " +
                "        ]                                                              " +
                "    }                                                                  " +
                "]                                                                      ";
        List<Map> list = JSONObject.parseArray(jsonStr, Map.class);
        for (Map m : list) {
            System.out.println(m.get("id"));
            List<Map<String, Object>> pList = (List<Map<String, Object>>) m.get("personnel");
            for (Map<String, Object> p : pList) {
                System.out.println(p.get("name"));
            }
        }
    }

}
