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
	public static String wrong_word;//��ʾ��ʱ�����Ĵ��󵥴�
	public static String[] origin_words_list;
	public static HashMap<String,Integer> count_dict;//���󵥴ʺʹʵ��������еĵ��ʶԱ�֮���γɵľ���ʵ�
	public static String[] top8Word;
	public static int index=0;//��ʾĿǰ�ǵڼ������󵥴�
	
	///�������ڴ�
	public static void main(String []args) {
		System.out.println(System.getProperty("user.dir"));//user.dirָ���˵�ǰ��·�� 
		//�����ǰ·��֮���ֵ�ǰ��·����hello�������·��
		dict=readFile(file_path);
		//System.out.println(dict.size());//46372
		//��ԭ�ֵ��еĵ��ʹ���map��������鵥���ǲ�����ȷ�������ھ��ǲ���ȷ
		origin_words_list=process(pre_process(file_path));
		//System.out.println("origin_words_list.size():"+origin_words_list.length);//46372
		//ԭ�ֵ����е����γɵ��ַ����б�
		all_words_list=process(pre_process(article_path));
		
		//��������������е����γɵ��ַ����б�
		correct(dict,all_words_list);
	
	}
	//
	/**
	 * �����ļ�
	 * �����ֵ�
	 * */
	//
	public static HashMap<String,Integer> readFile (String file_path) {
		
		File file=new File(file_path);
		BufferedReader reader = null; 
		HashMap<String,Integer> dict=new HashMap<>();
		/**����һ���ֵ����ͣ����������Ӧ�ĵ���value=1������value=0*/
	       try {  

//	           System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");  
	           reader = new BufferedReader(new FileReader(file));  
	           String tempString = null;  

	         // һ�ζ���һ�У�ֱ������nullΪ�ļ�����  

	           while ((tempString = reader.readLine()) != null) {  
	                     
	               dict.put(tempString,1);
	               
	               /**����ʵ�*/
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
//		System.out.println("���������е��ʵĳ��ȣ�"+len);
		//������ʽ����ж�
		
		//������len������ʹ��ѭ�����꣬һ��ȫ�������ˣ���ʱindex=0
		while(dict.get(all_words_list[index])!=null) {
			index++;
//			System.out.println("index:"+index);
			if(index<len);
			else {
				frame.run();//��������
				return;//ֱ�ӷ���
			}
//			System.out.println("Ŀǰ��index:"+dict.get(all_words_list[index]));
		}
			
		System.out.println("ǰindex:"+index);
		if(dict.get(all_words_list[index])==null)	
		{//�������ڣ����д���
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
		


  



