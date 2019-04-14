package hello;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//查看两个单词之间的最短距离，使用动态规划计算距离，感觉这个距离计算公式比较靠谱


public class distance {
	public static void main(String[] args) {
		String a="a",b="starf";
		System.out.println(distance(b,a));
	}
	
	//计算所有的距离词典，一对多
	public static HashMap<String,Integer> twoDistance(String wrong_word,String[] origin_words_list) {
		System.out.println("wrong_word:"+wrong_word);
		HashMap<String,Integer> count_dict=new HashMap<String,Integer>();
		int len=origin_words_list.length;
		//System.out.println("len:"+len);
		for(int i=0;i<len;i++) {
			count_dict.put(origin_words_list[i],distance(wrong_word,origin_words_list[i]));
		}//这里出现了一个问题，当两个字母的顺序发生改变的时候，distance计算出来的结果是不一样的。神奇。
		return count_dict;
	}
	
	//计算两个单词之间的距离，一对一
	public static int distance(String word1,String word2) {
		int row=word1.length();
		int col=word2.length();
        int[][] dis = new int[row+1][col+1];
        //创建二维数组
        for(int j=0;j<=col;j++) {
        	dis[0][j]=j;
        }//初始化行
        for(int i=0;i<=row;i++) {
        	dis[i][0]=i;
        }//初始化列
        for(int i=1;i<=row;i++) {
        	for(int j=1;j<=col;j++) {
        		dis[i][j]=Math.min(Math.min(dis[i-1][j]+1,dis[i][j-1]+1),dis[i-1][j-1]+
        				(word1.charAt(i-1)==word2.charAt(j-1)?0:1));
        	}
        }   
        return dis[row][col];
	}
	
	//找到topK的单词
	public static <K, V extends Comparable<? super V>> String[] sortByValue(Map<K, V> map)
    {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return compare;
            }
        });
        //System.out.println(list);//输出没有排序的完整词典
        String[] result=new String[8];
        int index=0;
        for (Map.Entry<K, V> entry : list) {
            result[index++]=(String) entry.getKey();
            if(index==8)
            	break;
        }
        return result;
    }
	
	
}
