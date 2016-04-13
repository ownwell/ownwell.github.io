title: "一个很好解析和生成Json的工具"
date: 2015-05-23 01:54:55
tags: [Android]
categories:	[Android]
---

对于现在的应用开发，Server端一般都会将API以Json的形式返回给客户端，客户端所做的就是能准确的解析这些Json。还记得以前那种辛辛苦苦的一个个字段的解析么?
数据准备：

```Java

{
    "phone": [
        "12345678",
        "87654321"
    ],
    "name": "yuanzhifei89",
    "age": 100,
    "address": {
        "country": "china",
        "province": "jiangsu"
    },
    "married": false
}

```

以前的咱们怎么解析：



```Java

 public void user(String json) {

        try {
            JSONObject mObject = new JSONObject(json);

            String name = mObject.optString("name");

            int age = mObject.optInt("age");

            boolean isMarried = mObject.getBoolean("married");


            JSONObject address = mObject.optJSONObject("address");

            if (address != null) {
                String country = address.optString("country");
                String province = address.optString("province");
            }
            ……


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
```

这个是要考虑字段和Json的数据结构的，哎，这样辛辛苦苦写了一上午可能也搞不定几个接口，太累了。
还好有Gson这个Google开发的工具类，国内有好多人使用不用担心，[CSDN Gson介绍](http://blog.csdn.net/lk_blog/article/category/1172246)

下载代码瞬间就是一个Java的对应关系

```Java
 public class Person {
        private String name;
        private int age;


        private boolean isMarried;
        private ArrayList<String> phone;
        private Address address;


        public class Address {
            private String country;
            private String province;


        }


    }
```

解析这个json字符串JSON_DATA

```Java

 Gson gson = new GsonBuilder().create();
 gson.fromJson(JSON_DATA,Person.class);
```

像上面的person返回的是一个list的形式的，只需要改动解析的过程即可：
```Java
Type listType = new TypeToken<ArrayList<Person>>(){}.getType();
ArrayList<Person> mPersonList = new Gson().fromJson(JSON_DATA, listType);
```
结合[GsonFormat](http://ownwell.github.io/2015/05/15/2015-1-13-androidstudiotools/)疗效更好哦。


