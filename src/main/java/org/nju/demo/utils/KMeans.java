package org.nju.demo.utils;

import org.nju.demo.config.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class KMeans {
    //记录迭代的次数
    static int count = 1;
    //向量维度
    static int dimension = 104;
    //文件的绝对路径
    public static String filePath = "/Users/sunchen/PycharmProjects/alarm_classification/data/vector.txt";
    //储存从文件中读取的数据
    static ArrayList<ArrayList<Float>> table = new ArrayList<ArrayList<Float>>();
    //储存分类一的结果
    public static ArrayList<ArrayList<Float>> alist = new ArrayList<ArrayList<Float>>();
    //储存分类二的结果
    public static ArrayList<ArrayList<Float>> blist = new ArrayList<ArrayList<Float>>();
    //记录初始随机产生的2个聚类中心
    public static ArrayList<ArrayList<Float>> randomList = new ArrayList<ArrayList<Float>>();

    //读取文件中的数据，储存到集合中
    public static ArrayList<ArrayList<Float>> readTable(String filePath){
        table.clear();
        ArrayList<Float> d = null;
        File file = new File(filePath);
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            BufferedReader bf = new BufferedReader(isr);
            String str = null;
            while((str = bf.readLine()) != null) {
                d = new ArrayList<Float>();
                String[] str1 = str.split(",");
                for(int i = 0; i < str1.length ; i++) {
                    d.add(Float.parseFloat(str1[i]));
                }
                table.add(d);
            }
//			System.out.println(table);
            bf.close();
            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("文件不存在！");
        }
        return table;
    }

    //随机产生2个初始聚类中心
    public static ArrayList<ArrayList<Float>> randomList() {
        int[] list = new int[2];
        //产生2个互不相同的随机数
        do {
            list[0] = (int)(Math.random()*30);
            list[1] = (int)(Math.random()*30);
        }while(list[0] == list[1]);
        //为了测试方便，我这里去数据集中前2个作为初始聚类中心
        randomList.clear();
        for(int i = 0; i < 2 ; i++) {
            //randomList.add(list[i]);
            randomList.add(table.get(i));
        }
        return randomList;
    }

    //比较两个数的大小，并返回其中较小的数
    public static double minNumber(double x, double y) {
        if(x < y) {
            return x;
        }
        return y;
    }

    //计算各个数据到两个中心点的距离，然后分成两类
    public static void eudistance(ArrayList<ArrayList<Float>> list){
        alist.clear();
        blist.clear();
        double minNumber;
        double distancea,distanceb;
//		System.out.println("randomList:"+randomList);
        for(int i = 0; i < table.size() ; i++) {
            distancea = 0;
            distanceb = 0;
            for(int j = 1;j <= dimension;++j){
                distancea += Math.pow(table.get(i).get(j)-list.get(0).get(j), 2);
            }
            for(int j = 1;j <= dimension;++j){
                distanceb += Math.pow(table.get(i).get(j)-list.get(1).get(j), 2);
            }
            minNumber = minNumber(distancea,distanceb);
            if(minNumber == distancea) {
                alist.add(table.get(i));
            }else{
                blist.add(table.get(i));
            }
        }
        System.out.println("第"+count+"次迭代:");
//        System.out.println(alist);
//        System.out.println(blist);
        for (int i=0;i<alist.size();++i){
            System.out.print(alist.get(i).get(0)+",");
        }
        System.out.println();
        for (int i=0;i<blist.size();++i){
            System.out.print(blist.get(i).get(0)+",");
        }
        System.out.println("\n");
        count++;
    }

    //计算每个类中各维度的平均值，重新生成聚类中心
    public static ArrayList<Float> newList(ArrayList<ArrayList<Float>> list){
        float c=0f;
        float[] avnums = new float[dimension];
        float[] sums = new float[dimension];
        ArrayList<Float> k = new ArrayList<Float>();
        for(int i = 0; i < list.size(); i++) {
            for (int j = 1;j <= dimension;++j){
                sums[j-1] += list.get(i).get(j);
            }
        }
        k.add(c);
        for (int i = 0;i < avnums.length;++i){
            avnums[i] = (float) (sums[i]*1.0/list.size());
            k.add(avnums[i]);
        }
        return k;
    }

    //判断两个集合的元素是否完全相同，若相同，则返回1；否则，返回0
    public static int same(ArrayList<ArrayList<Float>> list1, ArrayList<ArrayList<Float>> list2) {
        int countn = 0;
        if(list1.size()==list2.size()) {
            for(int i = 0; i < list1.size() ; i++) {
                for(int j = 0; j < list2.size() ; j++) {
                    if(list1.get(i).containsAll(list2.get(j)) && list2.get(j).containsAll(list1.get(i))) {
                        countn++;
                        break;
                    }
                }
            }
        }
        if(countn == list1.size()) {
            return 1;
        }else {
            return 0;
        }
    }

    //迭代求出最后的分类结果
    public static void kmeans() {
        int a,b,k=0;
        ArrayList<ArrayList<Float>> klist = null;
        ArrayList<ArrayList<Float>> arlist = null;
        ArrayList<ArrayList<Float>> brlist = null;
        do {
            klist = new ArrayList<ArrayList<Float>>();
            arlist = new ArrayList<ArrayList<Float>>();
            brlist = new ArrayList<ArrayList<Float>>();
            arlist.addAll(alist);
            brlist.addAll(blist);
            klist.clear();
            klist.add(newList(alist));
            klist.add(newList(blist));
            eudistance(klist);
            a = same(alist,arlist);
            b = same(blist,brlist);
            if(a == 1 && b == 1) {
                KMeans.count = 1;
                break;
            }
        }while(true);
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<Float>> rlist = new ArrayList<ArrayList<Float>>();
        readTable(filePath);
        rlist = randomList();
        eudistance(rlist);
        kmeans();
    }
}
