package com.example.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFruits();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);

    }


    private void initFruits() {
        for(int i = 0; i < 2; i++) {
            Fruit blg = new Fruit(getRandomLengthName("蓝莓"), R.drawable.blg);
            fruitList.add(blg);
            Fruit liz = new Fruit(getRandomLengthName("荔枝"), R.drawable.liz);
            fruitList.add(liz);
            Fruit pineapple = new Fruit(getRandomLengthName("菠萝"), R.drawable.pineapple);
            fruitList.add(pineapple);
            Fruit strwb = new Fruit(getRandomLengthName("草莓"), R.drawable.strwb);
            fruitList.add(strwb);

            Fruit blg1 = new Fruit(getRandomLengthName("蓝莓"), R.drawable.blg);
            fruitList.add(blg1);
            Fruit liz1 = new Fruit(getRandomLengthName("荔枝"), R.drawable.liz);
            fruitList.add(liz1);
            Fruit pineapple1 = new Fruit(getRandomLengthName("菠萝"), R.drawable.pineapple);
            fruitList.add(pineapple1);
            Fruit strwb1 = new Fruit(getRandomLengthName("草莓"), R.drawable.strwb);
            fruitList.add(strwb1);

            Fruit blg2 = new Fruit(getRandomLengthName("蓝莓"), R.drawable.blg);
            fruitList.add(blg2);
            Fruit liz2 = new Fruit(getRandomLengthName("荔枝"), R.drawable.liz);
            fruitList.add(liz2);
            Fruit pineapple2 = new Fruit(getRandomLengthName("菠萝"), R.drawable.pineapple);
            fruitList.add(pineapple2);
            Fruit strwb2 = new Fruit(getRandomLengthName("草莓"), R.drawable.strwb);
            fruitList.add(strwb2);





        }
    }
    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < length; i++){
            sb.append(name);
        }
        return sb.toString();
    }

}
