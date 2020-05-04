package ass01;


import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestFastjson {
    public static void main(String[] args) {
        Bean bean = new Bean();
        bean.setAge(11);
        bean.setName("jack");
        List<String> hobies = new ArrayList<String>();
        hobies.add("play game");
        hobies.add("reading book");
        hobies.add("watching films");


        bean.setHobies(hobies);
        System.out.println(JSONObject.toJSONString(bean));
//        Bean bean2 = JSONObject.parseObject(JSONObject.toJSONString(bean), Bean.class);

    }
}
