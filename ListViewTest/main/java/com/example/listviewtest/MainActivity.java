package com.example.listviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Transliterator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  String[] data = {"香蕉","苹果","枇杷","草莓","柠檬","桃子","榴莲","梨","红枣","石榴" +
            "橘子","芒果","西瓜","柿子","山竹","百香果","杏子","火龙果、桂圆、荸荠、柚子、桑葚、李子、" +
            "菠萝、菠萝蜜、槟榔、橙子、杨梅、银杏、无花果、乌梅、甘蔗、哈密瓜、人参果、樱桃、" +
            "圣女果、黄桃、雪莲果、蛇皮果、莲雾、红毛丹、鳄梨、海棠果、释迦果、蛋黄果、香瓜、" +
            "青梅 、山楂 、水蜜桃 、荔枝、金桔 、柑桔 、椰子、杨桃、木瓜、莲雾","葡萄"};

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();   //初始化水果数据
        FruitAdapter adapter = new FruitAdapter(MainActivity.this, R.layout.fruit_item, fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFruits(){
        for(int i = 0; i < 4; i++){
            Fruit blg = new Fruit("蓝莓", R.drawable.blg);
            fruitList.add(blg);
            Fruit piapp = new Fruit("菠萝",R.drawable.pineapple);
            fruitList.add(piapp);
            Fruit strwb = new Fruit("草莓",R.drawable.strwb);
            fruitList.add(strwb);
        }
    }


}
