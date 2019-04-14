package hello;
import java.io.*;
//import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.HashMap;
//import hello.frame;
import hello.distance;


public class judge {
	public static String file_path="src\\hello\\englishDictionary.txt";
	public static String article_path="src\\hello\\article.txt";
	public static HashMap<String,Integer> dict;
	public static String[] all_words_list;
	public static String wrong_word;//表示此时被检查的错误单词
	public static String[] origin_words_list;
	public static HashMap<String,Integer> count_dict;//错误单词和词典里面所有的单词对比之后形成的距离词典
	public static String[] top8Word;
	public static int index=0;//表示目前是第几个错误单词
	
	///主函数在此
	public static void main(String []args) {
		System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径 
		//输出当前路径之后发现当前的路径是hello这个包的路径
		dict=readFile(file_path);
		//System.out.println(dict.size());//46372
		//将原字典中的单词构成map，用来检查单词是不是正确，不存在就是不正确
		origin_words_list=process(pre_process(file_path));
		//System.out.println("origin_words_list.size():"+origin_words_list.length);//46372
		//原字典所有单词形成的字符串列表
		all_words_list=process(pre_process(article_path));
		
		//待检查作文中所有单词形成的字符串列表
		correct(dict,all_words_list);
	
	}
	//
	/**
	 * 读入文件
	 * 创建字典
	 * */
	//
	public static HashMap<String,Integer> readFile (String file_path) {
		
		File file=new File(file_path);
		BufferedReader reader = null; 
		HashMap<String,Integer> dict=new HashMap<>();
		/**建立一个字典类型，如果包含对应的单词value=1，否则value=0*/
	       try {  

//	           System.out.println("以行为单位读取文件内容，一次读一整行：");  
	           reader = new BufferedReader(new FileReader(file));  
	           String tempString = null;  

	         // 一次读入一行，直到读入null为文件结束  

	           while ((tempString = reader.readLine()) != null) {  
	                     
	               dict.put(tempString,1);
	               
	               /**加入词典*/
	           }  

	          reader.close();  

	        } catch (IOException e) {  

	            e.printStackTrace();  

	        } finally {  
	            if (reader != null) {  

	               try {  

	                  reader.close();  

	               } catch (IOException e1) {  

	               }  

	         }  

	      } 
	       return dict;
	}
	//
	public static String[] pre_process(String article_path){
        String encoding = "UTF-8";  
        File file = new File(article_path);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()]; 
        
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
        	String regex="(\\s)+";
            return new String(filecontent, encoding).split(regex);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
	   
	}
	
	public static String[] process(String[] pre_all_words_list) {
		int len=pre_all_words_list.length;
		String[] all_words_list=new String[len];
		for (int i=0;i<len;i++) {
			all_words_list[i]=pre_all_words_list[i].replace(".","").replace("?", "").replace(",", "");
		}
		return all_words_list;
		
	}
	
	public static void correct(HashMap<String,Integer> dict,String[] all_words_list) {
		int len=all_words_list.length;
//		System.out.println("文章中所有单词的长度："+len);
		//逐个单词进行判断
		
		//长度是len，不能使用循环艾玛，一次全部出来了，此时index=0
		while(dict.get(all_words_list[index])!=null) {
			index++;
//			System.out.println("index:"+index);
			if(index<len);
			else {
				frame.run();//跳出窗口
				return;//直接返回
			}
//			System.out.println("目前的index:"+dict.get(all_words_list[index]));
		}
			
		System.out.println("前index:"+index);
		if(dict.get(all_words_list[index])==null)	
		{//弹出窗口，进行处理
			if(index!=len) {
				wrong_word=all_words_list[index];
				count_dict=distance.twoDistance(wrong_word,origin_words_list);
				//System.out.println("count_dict:"+count_dict);
				top8Word=distance.sortByValue(count_dict);
			}
			//System.out.println("1");
			frame.run();
			//break;
		}
		//
	
	}
	
	

}
		


  



