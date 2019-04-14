package hello;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import hello.judge;
import hello.MyListModel;


public class frame extends JFrame { // 创建类，继承Jframe类
//	private JScrollPane scrollPane;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null; // 创建面板对象
	private JTextField new_word = null; // 创建文本域对象,用于写入新的单词
	private JPanel controlPanel = null; // 创建控制面板对象，上面是控制按钮
	private JButton confirmButton = null; // 创建按钮对象
	private JButton writeButton = null; // 创建按钮对象
	private JButton clearButton = null; // 创建按钮对象
	public String file_path;
	public String chose_word="";
	public JList<String> jl;
	

	
	
	private JTextField getJTextField() {
		if (new_word == null) {
			new_word = new JTextField(8);
			new_word.setBounds(10, 50, 200, 18);
			
		}
		return new_word;
	}//文本对象
	
	
//	private JScrollPane getJList() {
//		JList<String> jl = new JList<>(new MyListModel());
//		jl.setBorder(BorderFactory.createTitledBorder("top-5选项"));
//		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION) ;
//		//jl.addListSelectionListener();
//		System.out.println("2");
//		class SelecTry implements ListSelectionListener
//		{
//		public void valueChanged(ListSelectionEvent e){
//			int index=jl.getSelectedIndex();//得到对应的index，然后改变
//			chose_word=jl.getModel().getElementAt(index);//酱紫就可以了
//			System.out.println("chose_list:"+chose_word);
//		}
//		}
//		SelecTry selectIndex=new SelecTry();
//		jl.addListSelectionListener(selectIndex);
//		JScrollPane js = new JScrollPane(jl);
//		js.setBounds(10, 10, 100, 100);
//		return js;
//	}
	
	private JScrollPane getJList() {
		jl = new JList<>(new MyListModel());
		jl.setBorder(BorderFactory.createTitledBorder("top-8选项"));
		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION) ;
		//jl.addListSelectionListener();
		SelecTry selectIndex=new SelecTry();
		jl.addListSelectionListener(selectIndex);
		JScrollPane js = new JScrollPane(jl);
		js.setBounds(10, 10, 100, 100);
		return js;
	}
	
	
	
	
	private JLabel getJLabel(String wrong_word){
		JLabel jl=new JLabel("错误的单词是："+wrong_word);
		jl.setFont(new java.awt.Font("Dialog",1,15));   
		return jl;
	}
	
	private JPanel getControlPanel() {
		if (controlPanel == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setVgap(1);
			controlPanel = new JPanel();
			controlPanel.setLayout(flowLayout);
			controlPanel.add(getConfirmButton(), null);
			controlPanel.add(getWriteButton(), null);
			controlPanel.add(getClearButton(new_word), null);
			
		}
		return controlPanel;
	}
	
	//写入按钮
	
	private JButton getWriteButton() {
		if (writeButton == null) {
			writeButton = new JButton();
			writeButton.setText("写入词典"); // 修改按钮的提示信息
			//假如没有选择的词汇chose_word才需要进行选择
			
			writeButton.addActionListener(new java.awt.event.ActionListener() {
				// 按钮的单击事件
				public void actionPerformed(ActionEvent e) {
					
					if(chose_word!="") {//说明有对应的词汇，就将这个词汇填入词典里面
						//System.out.println("ha:"+chose_word);
						judge.dict.put(chose_word, 1);
						try {
							correctArticle(judge.article_path, chose_word);//使用选择的单词对作文进行改正
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						
					}
					else {
						String word = new_word.getText();
						judge.dict.put(word, 1);
						try {
							add_dict(judge.file_path, word);
							correctArticle(judge.article_path, word);//使用填入的单词对作文进行改正
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
					}
					
					
				}
			});//事件添加完毕，如果说不仅选择了还写入了，那就优选考虑选择的，毕竟选择的都肯定是正确的
			
			
		}
		return writeButton;
	}
	
	//重置按钮
	
	private JButton getClearButton(JTextField new_word) {
		if (clearButton == null) {
			clearButton = new JButton();
			clearButton.setText("清空"); // 修改按钮的提示信息
			clearButton.addActionListener(new java.awt.event.ActionListener() {
						// 按钮的单击事件
						public void actionPerformed(ActionEvent e) {
							new_word.setText("");
							new_word.requestFocus();
						}
					});
		}
		
		
		return clearButton;
		
	}
	//确定按钮
	private JButton getConfirmButton() {
		if (confirmButton == null) {
			confirmButton = new JButton();
			confirmButton.setText("确定"); // 修改按钮的提示信息
			confirmButton.addActionListener(new java.awt.event.ActionListener() {
				 				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//System.exit(0);这个标识当前页面的窗口全部退出
					judge.index++;
					System.out.println("index:"+judge.index);
					if (judge.index<judge.all_words_list.length)
						judge.correct(judge.dict, judge.all_words_list);
					if(judge.index>=judge.all_words_list.length)
						System.exit(0);//全部退出
					
				}
			});

		}
				
		return confirmButton;
		
	} 
	

	public frame() {//这是一个构造函数
		super();
		initialize();
	}
	
	
	private void initialize() {
		this.chose_word="";//使用空字符串进行初始化
		this.setSize(500, 300);
		//JLabel jl=new JLabel("错误的单词是：",JLabel.NORTH);
		//this.add(jl);
		this.setContentPane(getJContentPane());
		this.setTitle("纠正单词啦~~~");
	}
	
	
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			//jContentPane.setPreferredSize(new Dimension(100,50));
			jContentPane.setLayout(new BorderLayout(10,20));
			//jContentPane.add(getJTextField(),new Dimension(100,50) );
			
			
			jContentPane.add(getJLabel(judge.wrong_word),BorderLayout.NORTH);
			//此时在主函数中，wrong_word已经有结果了
			jContentPane.add(getJList(),BorderLayout.CENTER);
			jContentPane.add(getJTextField(),BorderLayout.EAST);
			jContentPane.add(getControlPanel(), BorderLayout.SOUTH);
			
		}
		return jContentPane;
	}
	
	
	public static void run() { // 主方法
		if(judge.index>=judge.all_words_list.length) {
			System.out.println("恭喜恭喜啦~~当前没有任何错误的单词，修改完毕！");
			System.exit(0);//全部退出
		}
			
		
		frame thisClass = new frame(); // 创建本类对象
		thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisClass.setVisible(true); // 设置该窗体为显示状态
		
		
		
	}
	

	

	    
    /**  
     * 追加内容到文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true  
     *   
     * @param fileName  
     * @param content  
     */   
//    public static void add_dict(String file_path, String word) {  
//        BufferedWriter out = null ;  
//        try  {  
//            out = new  BufferedWriter( new  OutputStreamWriter(  
//                    new  FileOutputStream(file_path,  true )));  
//            out.write(word+System.getProperty("line.separator")); 
//            
//        } catch  (Exception e) {  
//            e.printStackTrace();
//        } finally  {  
//            try  {  
//                out.close();  
//            } catch  (IOException e) {  
//                e.printStackTrace();  
//            }  
//        }  
//    }    
	public static void add_dict(String file_path, String word) throws IOException{  
        
      try {                                                                        
          // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件      
           FileWriter writer = new FileWriter(file_path, true);                     
           writer.write(word+System.getProperty("line.separator"));     
           writer.close();                                                         
       } catch (IOException e) {                                                   
           e.printStackTrace();                                                    
       } 

  }   
	
	public static void correctArticle(String article_path,String chose_word) throws IOException{
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
        //读出，然后进行替换，最后写入
        System.out.println(judge.wrong_word);
        System.out.println("cw:"+chose_word);
        String regex="[,.(\\s)+]"+judge.wrong_word+"[,.(\\s)+]";//表示单词的两边是空格
        if(judge.index==0)
        	regex=""+judge.wrong_word+"[,.(\\s)+]";
        String replace_chose_word=" "+chose_word+" ";//更换成两边都只有一个空格的表示
        Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(new String(filecontent, encoding));
		if(matcher.find()) {
			if(matcher.group(0).endsWith(","))
				replace_chose_word=" "+chose_word+",";//更换成两边都只有一个空格的表示;
			if(matcher.group(0).endsWith("."))
				replace_chose_word=" "+chose_word+".";//更换成两边都只有一个空格的表示;
			
		}
		else {
			System.out.println("您输入的英文文章不符合文法！请检查。");
		}
        String new_content=new String(filecontent, encoding).replaceAll(regex, replace_chose_word);//文档中的内容
        
        //开始进行写入
        FileWriter writer = new FileWriter(article_path, false); //false表示覆盖之前的，重新写入
        System.out.println(new_content.trim());
        try{           
        	writer.write(new_content.trim());//将除去第一个空格之后的字符串写入文件中
        	writer.close();  
           }catch(IOException e){
            e.printStackTrace();
           }
                
	}
//
	private class SelecTry implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e){
			int index=jl.getSelectedIndex();//得到对应的index，然后改变
			System.out.println("6");
			chose_word=jl.getModel().getElementAt(index);//酱紫就可以了
			System.out.println("chose_word:"+chose_word);
		}
	}
	//
	
	
}



