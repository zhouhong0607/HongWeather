package com.example.administrator.kossweather.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.kossweather.R;
import com.example.administrator.kossweather.adapter.Item;
import com.example.administrator.kossweather.adapter.QuickAdapter;
import com.example.administrator.kossweather.database.DatabaseOperator;
import com.example.administrator.kossweather.datamodel.City;
import com.example.administrator.kossweather.datamodel.County;
import com.example.administrator.kossweather.datamodel.Province;
import com.example.administrator.kossweather.util.HttpCallbackListener;
import com.example.administrator.kossweather.util.HttpUtil;
import com.example.administrator.kossweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private int currentLevel;
    private boolean isFromShowActivity;

    BaseQuickAdapter<Item> quickAdapter;
    private List<Item> datalist = new ArrayList<>();
    private RecyclerView mRecyclerView;

    private TextView titleText;
    private ProgressDialog progressDialog;
    private DatabaseOperator databaseOperator;


    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;

    private Province selectedProvince;
    private City selectedCity;
    private County selectedCounty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFromShowActivity = getIntent().getBooleanExtra("from_showactivity", false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (preferences.getBoolean("city_selected", false) && (!isFromShowActivity)) {
            Intent intent = new Intent(this, ShowActivity.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_select);

        titleText = (TextView) findViewById(R.id.title);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

         quickAdapter = new QuickAdapter(this, R.layout.item_view, datalist);
        quickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        quickAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                @Override public void onItemClick(View view, int position) {
                    if (currentLevel == LEVEL_PROVINCE) {
                        selectedProvince = provinceList.get(position);
                        queryCities();
                    } else if (currentLevel == LEVEL_CITY) {
                        selectedCity = cityList.get(position);
                        queryCounties();
                    }else if(currentLevel==LEVEL_COUNTY)
                    {
                        String countyCode=countyList.get(position).getCountyCode();
                        Intent intent=new Intent(SelectActivity.this,ShowActivity.class);
                        intent.putExtra("county_code",countyCode);
                        startActivity(intent);
                        finish();
                    }
                }
             });
        mRecyclerView.setAdapter(quickAdapter);
        databaseOperator = DatabaseOperator.getInstance(this);
        queryProvinces();
    }


    private void queryProvinces() {
        provinceList = databaseOperator.queryFromProvince();
        if (provinceList.size() > 0) {
            datalist.clear();
            for (Province province : provinceList) {
                Item item=new Item();
                item.setTxt(province.getProvinceName());
                datalist.add(item);
            }
            quickAdapter.notifyDataSetChanged();//更新listview
            mRecyclerView.setSelected(false);
            titleText.setText("省份");
            currentLevel = LEVEL_PROVINCE;

        } else//服务器上查询
        {
            queryFromServer(null, "province");
        }
    }

    private void queryCities() {
        cityList = databaseOperator.queryFromCity(selectedProvince.getProvinceCode());
        if (cityList.size() > 0) {
            datalist.clear();
            for (City city : cityList) {
                Item item=new Item();
                item.setTxt(city.getCityName());
                datalist.add(item);

            }
            quickAdapter.notifyDataSetChanged();//更新listview
            mRecyclerView.setSelected(false);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;

        } else//服务器上查询
        {
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    private void queryCounties() {
        countyList = databaseOperator.queryFromCounty(selectedCity.getCityCode());
        if (countyList.size() > 0) {
            datalist.clear();
            for (County county : countyList) {
                Item item=new Item();
                item.setTxt(county.getCountyName());
                datalist.add(item);

            }
            quickAdapter.notifyDataSetChanged();//更新listview
            mRecyclerView.setSelected(false);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;

        } else//服务器上查询
        {
            queryFromServer(selectedCity.getCityCode(), "county");
        }
    }


    private void queryFromServer(final String code, final String type) {
        String address;
        if (!TextUtils.isEmpty(code)) {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        } else {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }

        showProgressDialog();

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvincesResponse(databaseOperator, response);
                } else if ("city".equals(type)) {
                    result = Utility.handleCitiesResponse(databaseOperator, response, selectedProvince.getProvinceCode());
                } else if ("county".equals(type)) {
                    result = Utility.handleCountiesResponse(databaseOperator, response, selectedCity.getCityCode());
                }
                if (result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }

            }

            @Override
            public void onError(Exception e) {

                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        closeProgressDialog();
                        Toast.makeText(SelectActivity.this, "加载失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }


    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载....");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            queryCities();
        } else if (currentLevel == LEVEL_CITY) {
            queryProvinces();
        } else {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
