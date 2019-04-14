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


public class frame extends JFrame { // �����࣬�̳�Jframe��
//	private JScrollPane scrollPane;
	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null; // ����������
	private JTextField new_word = null; // �����ı������,����д���µĵ���
	private JPanel controlPanel = null; // �������������������ǿ��ư�ť
	private JButton confirmButton = null; // ������ť����
	private JButton writeButton = null; // ������ť����
	private JButton clearButton = null; // ������ť����
	public String file_path;
	public String chose_word="";
	public JList<String> jl;
	

	
	
	private JTextField getJTextField() {
		if (new_word == null) {
			new_word = new JTextField(8);
			new_word.setBounds(10, 50, 200, 18);
			
		}
		return new_word;
	}//�ı�����
	
	
//	private JScrollPane getJList() {
//		JList<String> jl = new JList<>(new MyListModel());
//		jl.setBorder(BorderFactory.createTitledBorder("top-5ѡ��"));
//		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION) ;
//		//jl.addListSelectionListener();
//		System.out.println("2");
//		class SelecTry implements ListSelectionListener
//		{
//		public void valueChanged(ListSelectionEvent e){
//			int index=jl.getSelectedIndex();//�õ���Ӧ��index��Ȼ��ı�
//			chose_word=jl.getModel().getElementAt(index);//���ϾͿ�����
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
		jl.setBorder(BorderFactory.createTitledBorder("top-8ѡ��"));
		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION) ;
		//jl.addListSelectionListener();
		SelecTry selectIndex=new SelecTry();
		jl.addListSelectionListener(selectIndex);
		JScrollPane js = new JScrollPane(jl);
		js.setBounds(10, 10, 100, 100);
		return js;
	}
	
	
	
	
	private JLabel getJLabel(String wrong_word){
		JLabel jl=new JLabel("����ĵ����ǣ�"+wrong_word);
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
	
	//д�밴ť
	
	private JButton getWriteButton() {
		if (writeButton == null) {
			writeButton = new JButton();
			writeButton.setText("д��ʵ�"); // �޸İ�ť����ʾ��Ϣ
			//����û��ѡ��Ĵʻ�chose_word����Ҫ����ѡ��
			
			writeButton.addActionListener(new java.awt.event.ActionListener() {
				// ��ť�ĵ����¼�
				public void actionPerformed(ActionEvent e) {
					
					if(chose_word!="") {//˵���ж�Ӧ�Ĵʻ㣬�ͽ�����ʻ�����ʵ�����
						//System.out.println("ha:"+chose_word);
						judge.dict.put(chose_word, 1);
						try {
							correctArticle(judge.article_path, chose_word);//ʹ��ѡ��ĵ��ʶ����Ľ��и���
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
							correctArticle(judge.article_path, word);//ʹ������ĵ��ʶ����Ľ��и���
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
					}
					
					
				}
			});//�¼������ϣ����˵����ѡ���˻�д���ˣ��Ǿ���ѡ����ѡ��ģ��Ͼ�ѡ��Ķ��϶�����ȷ��
			
			
		}
		return writeButton;
	}
	
	//���ð�ť
	
	private JButton getClearButton(JTextField new_word) {
		if (clearButton == null) {
			clearButton = new JButton();
			clearButton.setText("���"); // �޸İ�ť����ʾ��Ϣ
			clearButton.addActionListener(new java.awt.event.ActionListener() {
						// ��ť�ĵ����¼�
						public void actionPerformed(ActionEvent e) {
							new_word.setText("");
							new_word.requestFocus();
						}
					});
		}
		
		
		return clearButton;
		
	}
	//ȷ����ť
	private JButton getConfirmButton() {
		if (confirmButton == null) {
			confirmButton = new JButton();
			confirmButton.setText("ȷ��"); // �޸İ�ť����ʾ��Ϣ
			confirmButton.addActionListener(new java.awt.event.ActionListener() {
				 				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					//System.exit(0);�����ʶ��ǰҳ��Ĵ���ȫ���˳�
					judge.index++;
					System.out.println("index:"+judge.index);
					if (judge.index<judge.all_words_list.length)
						judge.correct(judge.dict, judge.all_words_list);
					if(judge.index>=judge.all_words_list.length)
						System.exit(0);//ȫ���˳�
					
				}
			});

		}
				
		return confirmButton;
		
	} 
	

	public frame() {//����һ�����캯��
		super();
		initialize();
	}
	
	
	private void initialize() {
		this.chose_word="";//ʹ�ÿ��ַ������г�ʼ��
		this.setSize(500, 300);
		//JLabel jl=new JLabel("����ĵ����ǣ�",JLabel.NORTH);
		//this.add(jl);
		this.setContentPane(getJContentPane());
		this.setTitle("����������~~~");
	}
	
	
	
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			//jContentPane.setPreferredSize(new Dimension(100,50));
			jContentPane.setLayout(new BorderLayout(10,20));
			//jContentPane.add(getJTextField(),new Dimension(100,50) );
			
			
			jContentPane.add(getJLabel(judge.wrong_word),BorderLayout.NORTH);
			//��ʱ���������У�wrong_word�Ѿ��н����
			jContentPane.add(getJList(),BorderLayout.CENTER);
			jContentPane.add(getJTextField(),BorderLayout.EAST);
			jContentPane.add(getControlPanel(), BorderLayout.SOUTH);
			
		}
		return jContentPane;
	}
	
	
	public static void run() { // ������
		if(judge.index>=judge.all_words_list.length) {
			System.out.println("��ϲ��ϲ��~~��ǰû���κδ���ĵ��ʣ��޸���ϣ�");
			System.exit(0);//ȫ���˳�
		}
			
		
		frame thisClass = new frame(); // �����������
		thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		thisClass.setVisible(true); // ���øô���Ϊ��ʾ״̬
		
		
		
	}
	

	

	    
    /**  
     * ׷�����ݵ��ļ���ʹ��FileOutputStream���ڹ���FileOutputStreamʱ���ѵڶ���������Ϊtrue  
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
          // ��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�      
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
        //������Ȼ������滻�����д��
        System.out.println(judge.wrong_word);
        System.out.println("cw:"+chose_word);
        String regex="[,.(\\s)+]"+judge.wrong_word+"[,.(\\s)+]";//��ʾ���ʵ������ǿո�
        if(judge.index==0)
        	regex=""+judge.wrong_word+"[,.(\\s)+]";
        String replace_chose_word=" "+chose_word+" ";//���������߶�ֻ��һ���ո�ı�ʾ
        Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(new String(filecontent, encoding));
		if(matcher.find()) {
			if(matcher.group(0).endsWith(","))
				replace_chose_word=" "+chose_word+",";//���������߶�ֻ��һ���ո�ı�ʾ;
			if(matcher.group(0).endsWith("."))
				replace_chose_word=" "+chose_word+".";//���������߶�ֻ��һ���ո�ı�ʾ;
			
		}
		else {
			System.out.println("�������Ӣ�����²������ķ������顣");
		}
        String new_content=new String(filecontent, encoding).replaceAll(regex, replace_chose_word);//�ĵ��е�����
        
        //��ʼ����д��
        FileWriter writer = new FileWriter(article_path, false); //false��ʾ����֮ǰ�ģ�����д��
        System.out.println(new_content.trim());
        try{           
        	writer.write(new_content.trim());//����ȥ��һ���ո�֮����ַ���д���ļ���
        	writer.close();  
           }catch(IOException e){
            e.printStackTrace();
           }
                
	}
//
	private class SelecTry implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e){
			int index=jl.getSelectedIndex();//�õ���Ӧ��index��Ȼ��ı�
			System.out.println("6");
			chose_word=jl.getModel().getElementAt(index);//���ϾͿ�����
			System.out.println("chose_word:"+chose_word);
		}
	}
	//
	
	
}



