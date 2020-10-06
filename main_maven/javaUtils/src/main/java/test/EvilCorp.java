package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvilCorp {

    public static void main(String[] args) {
        String str = "you are a nice person,this is a filter test! so bad for god";     //需要过滤的内容
        String tempStr = str.replace(",", " ").replace(".", " ").replace("!", " ");
        String[] arrStr = tempStr.split(" ");       //转换成数组
        Map<String, Object> replaceWordMap = new HashMap<>();       //如果出现 bad、better等就替换成后面的值
        replaceWordMap.put("bad", "ungood");
        replaceWordMap.put("better", "gooder");

        @SuppressWarnings("unchecked")
        List<String> sensitiveList = new ArrayList(){{ add("nic");add("test"); }};        //敏感词，遇到就替换成XXXX

        List<String> needReplaceWordList = new ArrayList<>();       //需要替换成指定的单词
        List<String> needFilterWordList = new ArrayList<>();        //需要过滤成XXXX的单调

        for (String strVal : arrStr) {
            if (replaceWordMap.containsKey(strVal)) {
                needReplaceWordList.add(strVal);
            }

            sensitiveList.forEach(senStr -> {
                if (strVal.startsWith(senStr) || strVal.endsWith(senStr) || strVal.equals(senStr)) {
                    needFilterWordList.add(strVal);
                }
            });
        }
        for (String needFilterStr : needFilterWordList) {
            System.out.println(needFilterStr);
            str = str.replace(needFilterStr, returnReplaceVal(needFilterStr.length()));
        }
        for (String needReplaceStr : needReplaceWordList) {
            str = str.replace(needReplaceStr, replaceWordMap.get(needReplaceStr).toString());
        }
        System.out.println(str);
    }

    private static String returnReplaceVal(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("X");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}
