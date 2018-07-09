package zgc.org.demo.activity;

import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import zgc.org.demo.R;
import zgc.org.demo.activity.base.BaseActivity;
import zgc.org.demo.util.LogUtil;

/**
 * Author:Nick
 * Time2018/7/9 15:35
 * Description
 */
public class GsonTransformActivity extends BaseActivity {
    @BindView(R.id.tv_source)
    TextView tvSource;
    @BindView(R.id.tv_default)
    TextView tvDefault;
    @BindView(R.id.tv_custom)
    TextView tvCustom;

    private final String json = "{\"num1\":1,\"num2\":1.0}";

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_gson_transform;
    }

    @Override
    protected void initView() {
        setTitle(getIntent().getStringExtra("title"));
        tvSource.setText(String.format(getString(R.string.gson_map_source), json));
        //默认转换
        Gson gson = new Gson();
        TreeMap<String, Object> map = gson.fromJson(json, new TypeToken<TreeMap<String, Object>>() {
        }.getType());
        LogUtil.d(map.get("num1") + "\n" + map.get("num2"));
        tvDefault.setText(String.format(getString(R.string.gson_map_transform_default), gson.toJson(map, new TypeToken<HashMap<String, Object>>() {
        }.getType())));

        Gson gson2 = new GsonBuilder()
                .registerTypeAdapter(
                        new TypeToken<TreeMap<String, Object>>() {
                        }.getType(),
                        new JsonDeserializer<TreeMap<String, Object>>() {
                            @Override
                            public TreeMap<String, Object> deserialize(
                                    JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {

                                TreeMap<String, Object> hashMap = new TreeMap<>();
                                JsonObject jsonObject = json.getAsJsonObject();
                                Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                                for (Map.Entry<String, JsonElement> entry : entrySet) {
                                    hashMap.put(entry.getKey(), entry.getValue());
                                }
                                return hashMap;
                            }
                        }).create();
        TreeMap<String, Object> map2 = gson2.fromJson(json, new TypeToken<TreeMap<String, Object>>() {
        }.getType());
        LogUtil.d(map2.get("num1") + "\n" + map2.get("num2"));
        tvCustom.setText(String.format(getString(R.string.gson_map_transform_custom), gson2.toJson(map2, new TypeToken<HashMap<String, Object>>() {
        }.getType())));
    }

    @Override
    public void initData() {

    }

}
