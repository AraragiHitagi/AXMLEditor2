package cn.wjdiankong.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	
	private final static String CMD_TXT = "[usage java -jar AXMLEditor2.jar [-tag|-attr] [-i|-r|-m] [��ǩ��|��ǩΨһID|������|����ֵ] [�����ļ�|����ļ�]" +
											"\n����˵����" +
											"\n  1>�������ԣ�application�ı�ǩ�в���android:debuggable=\"true\"���ԣ��ó����ڿɵ�ʽ״̬" +
											"\n    java -jar AXMLEditor2.jar -attr -i application package debuggable true AndroidManifest.xml AndroidManifest_out.xml" +
											"\n  2>ɾ�����ԣ�application��ǩ��ɾ��allowBackup���ԣ�������app�Ϳ��Խ���ɳ�����ݱ���" +
											"\n    java -jar AXMLEditor2.jar -attr -r application allowBackup AndroidManifest.xml AndroidManifest_out.xml" +
											"\n  3>�������ԣ�application�ı�ǩ���޸�android:debuggable=\"true\"���ԣ��ó����ڿɵ�ʽ״̬" +
											"\n    java -jar AXMLEditor2.jar -attr -m application package debuggable true AndroidManifest.xml AndroidManifest_out.xml" +
											"\n  4>�����ǩ����Ϊ�����ǩʱһ����ǩ���ݱȽ϶࣬�������ʽ�����㣬��������һ����Ҫ�����ǩ���ݵ�xml�ļ����ɡ�" +
											"\n    java -jar AXMLEditor2.jar -tag -i [insert.xml] AndroidManifest.xml AndroidManifest_out.xml" +
											"\n  5>ɾ����ǩ��ɾ��android:name=\"cn.wjdiankong.demo.MainActivity\"�ı�ǩ����" +
											"\n    java -jar AXMLEditor2.jar -tag -r activity cn.wjdiankong.demo.MainActivity AndroidManifest.xml AndroidManifest_out.xml";

	public static void main(String[] args){

		/**
		 * �����ʽ��
		 * -i ��Ӷ���
		 * -r ɾ������
		 * -m ���¶���
		 * -attr ����
		 * -tag ��ǩ
		 * ���Բ���ֱ������������ɣ���ǩ������Ҫ������Ϣ
		 */
		
		if(args.length < 3){
			System.out.println("��������...");
			System.out.println(CMD_TXT);
			return;
		}
		
		String inputfile = args[args.length-2];
		String outputfile = args[args.length-1];
		File inputFile = new File(inputfile);
		File outputFile = new File(outputfile);
		if(!inputFile.exists()){
			System.out.println("�����ļ�������...");
			return;
		}
		
		//���ļ�
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try{
			fis = new FileInputStream(inputFile);
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=fis.read(buffer)) != -1){
				bos.write(buffer, 0, len);
			}
			ParserChunkUtils.xmlStruct.byteSrc = bos.toByteArray();
		}catch(Exception e){
			System.out.println("parse xml error:"+e.toString());
		}finally{
			try{
				fis.close();
				bos.close();
			}catch(Exception e){
			}
		}
		
		doCommand(args);
		
		//д�ļ�
		if(!outputFile.exists()){
			outputFile.delete();
		}
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(outputFile);
			fos.write(ParserChunkUtils.xmlStruct.byteSrc);
			fos.close();
		}catch(Exception e){
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void testDemo(){
		//ɾ��һ��tag��ɾ��tagʱ����ָ��tag���ƺ�nameֵ����������Ψһȷ��һ��tag��Ϣ
		//XmlEditor.removeTag("uses-permission", "android.permission.INTERNET");
		//XmlEditor.removeTag("activity", ".MainActivity");

		//ɾ�����ԣ�����Ҫָ�����Զ�Ӧ��tag���ƺ�nameֵ��Ȼ�������������
		//XmlEditor.removeAttr("activity", ".MainActivity", "name");
		//XmlEditor.removeAttr("uses-permission", "android.permission.INTERNET", "name");

		//��ӱ�ǩ��ֱ����xml�����ü��ɣ���Ҫע�����������Ϣ��manifest����ı�ǩ������application��ǩ�ĺ���
		//XmlEditor.addTag();

		//������ԣ�����ָ����ǩ����
		//XmlEditor.addAttr("activity", ".MainActivity", "jiangwei", "fourbrother");

		//�������ԣ�����ֱ�Ӳ�����ɾ��������Ӳ������
		//XmlEditor.modifyAttr("application", "package", "debuggable", "true");
	}

	public static void doCommand(String[] args){
		if("-tag".equals(args[0])){
			if(args.length < 2){
				System.out.println("ȱ�ٲ���...");
				System.out.println(CMD_TXT);
				return;
			}
			//��ǩ
			if("-i".equals(args[1])){
				if(args.length < 3){
					System.out.println("ȱ�ٲ���...");
					System.out.println(CMD_TXT);
					return;
				}
				//�������
				String insertXml = args[2];
				File file = new File(insertXml);
				if(!file.exists()){
					System.out.println("�����ǩxml�ļ�������...");
					return;
				}
				XmlEditor.addTag(insertXml);
				System.out.println("�����ǩ���...");
				return;
			}else if("-r".equals(args[1])){
				if(args.length < 4){
					System.out.println("ȱ�ٲ���...");
					System.out.println(CMD_TXT);
					return;
				}
				//ɾ������
				String tag = args[2];
				String tagName = args[3];
				XmlEditor.removeTag(tag, tagName);
				System.out.println("ɾ����ǩ���...");
				return;
			}else{
				System.out.println("������ǩ��������...");
				System.out.println(CMD_TXT);
				return;
			}
		}else if("-attr".equals(args[0])){
			if(args.length < 2){
				System.out.println("ȱ�ٲ���...");
				System.out.println(CMD_TXT);
				return;
			}
			//����
			if("-i".equals(args[1])){
				if(args.length < 6){
					System.out.println("ȱ�ٲ���...");
					System.out.println(CMD_TXT);
					return;
				}
				//��������
				String tag = args[2];
				String tagName = args[3];
				String attr = args[4];
				String value = args[5];
				XmlEditor.addAttr(tag, tagName, attr, value);
				System.out.println("�����������...");
				return;
			}else if("-r".equals(args[1])){
				if(args.length < 5){
					System.out.println("ȱ�ٲ���...");
					System.out.println(CMD_TXT);
					return;
				}
				//ɾ������
				String tag = args[2];
				String tagName = args[3];
				String attr = args[4];
				XmlEditor.removeAttr(tag, tagName, attr);
				System.out.println("ɾ���������...");
				return;
			}else if("-m".equals(args[1])){
				if(args.length < 6){
					System.out.println("ȱ�ٲ���...");
					System.out.println(CMD_TXT);
					return;
				}
				//�޸�����
				String tag = args[2];
				String tagName = args[3];
				String attr = args[4];
				String value = args[5];
				XmlEditor.modifyAttr(tag, tagName, attr, value);
				System.out.println("�޸��������...");
			}else{
				System.out.println("�������Բ�������...");
				System.out.println(CMD_TXT);
				return;
			}
		}
	}
	
}
