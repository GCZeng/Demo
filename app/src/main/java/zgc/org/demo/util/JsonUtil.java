package zgc.org.demo.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Author:ZGC
 * Time2019/6/18 15:02
 * Description
 */
public class JsonUtil {
    private static Gson mGson = null;

    static {
        mGson = new Gson();
    }

    public static Gson getmGson() {
        return mGson;
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    public static String toJson(Object object) {
        return mGson.toJson(object);
    }

    public static String toJson(Object object, Type typeOfSrc) {
        return mGson.toJson(object, typeOfSrc);
    }

    public static String string2Json(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '/':
                    sb.append("\\/");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
}
