package com.intergraph;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFUI {
    private JTextField textField1;
    private JTextField textField3;
    private JTextField textField2;
    private JTextField textField5;
    private JTextField textField4;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JButton a1Button;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a4Button;
    private JButton a5Button;
    private JPanel JPanel1;
    private JTextField textPath;
    private JTextField textName;
    private JButton mergeButton;
    private JTextField textMerge;

    private String path;
    private String name;
    private String[] files = new String[6];

    public PDFUI() {
        a1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                path = textPath.getText();
                name = textName.getText();
                partitionPdfFile(path, name+"-MD1.pdf", Integer.parseInt(textField1.getText()), Integer.parseInt(textField2.getText()));
            }
        });
        a2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                path = textPath.getText();
                name = textName.getText();
                partitionPdfFile(path, name+"-VD.pdf", Integer.parseInt(textField3.getText()), Integer.parseInt(textField4.getText()));
            }
        });
        a3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                path = textPath.getText();
                name = textName.getText();
                partitionPdfFile(path, name+"-CS.pdf", Integer.parseInt(textField5.getText()), Integer.parseInt(textField6.getText()));
            }
        });
        a4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                path = textPath.getText();
                name = textName.getText();
                partitionPdfFile(path, name+"-DS.pdf", Integer.parseInt(textField7.getText()), Integer.parseInt(textField8.getText()));
            }
        });
        a5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                path = textPath.getText();
                name = textName.getText();
                partitionPdfFile(path, name+"-MD2.pdf", Integer.parseInt(textField9.getText()), Integer.parseInt(textField10.getText()));
            }
        });
        mergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                files = ;
//                newPath
//
//                mergePdfFiles(files, newPath);
//                for (int i = 0; i < files.length; i++) {
//                    File file = new File(files[i]);
//                    if (file.exists()) {
//                        file.delete();
//                    }
//                }

                File dir = new File(textMerge.getText());
                String[] old = new PDFUI().ListFileFileter(dir);
                String[] real = {old[2], old[3]};
                String str1 = old[2].substring(0, old[2].length()-5);
                String str2 = old[2].substring(old[2].length()-4);
//                System.out.println(str1);
//                System.out.println(str2);
                for (int i = 0; i < old.length; i++) {
                    System.out.println(old[i]);
                }
                mergePdfFiles(real,str1+str2);
                for (int i = 0; i < real.length; i++) {
                    File file = new File(real[i]);
                    if (file.exists()) {
                        file.delete();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("PDFUI");
        frame.setContentPane(new PDFUI().JPanel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocation(600, 200);
        frame.setVisible(true);
    }

    public void partitionPdfFile(String pdfFile,
                                        String newFile, int from, int end) {
        Document document = null;
        PdfCopy copy = null;
        try {
            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            if(end==0){
                end = n;
            }
            ArrayList<String> savepaths = new ArrayList<String>();
            String staticpath = pdfFile.substring(0, pdfFile.lastIndexOf("\\")+1);
            String savepath = staticpath+ newFile;
            savepaths.add(savepath);
            document = new Document(reader.getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(savepaths.get(0)));
            document.open();
            for(int j=from; j<=end; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch(DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void mergePdfFiles(String[] files, String savepath)
    {
        try
        {
            Document document = new Document(new PdfReader(files[0]).getPageSize(1));

            PdfCopy copy = new PdfCopy(document, new FileOutputStream(savepath));

            document.open();

            for(int i=0; i<files.length; i++)
            {
                PdfReader reader = new PdfReader(files[i]);

                int n = reader.getNumberOfPages();

                for(int j=1; j<=n; j++)
                {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }

            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch(DocumentException e) {
            e.printStackTrace();
        }
    }

    public String[] ListFileFileter(File dir){
        if(dir.exists()){
            //匿名内部类，把FileFilter接口对象作为参数
            File[] file = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if(pathname.isDirectory()){
                        return true;
                    }
                    String name = pathname.getName();//获取文件的名称E:\复件 demodir\Learn\sgim_piccell.v1.bin.bak
//                    System.out.println("****************"+pathname);
                    return name.endsWith(".pdf")|| name.endsWith(".PDF");//过滤文件类型为.bak或者.BAK文件，而不包含.BAK或者.bak的文件
                }
            });
            //深度遍历文件，递归
            for(int i=0;i<file.length;i++){
                if(file[i].isFile()){//如果遍历到的是文件，直接删除
//                    files[i].delete();
                    files[i] = file[i].getPath();
//                    System.out.println(files[i].getPath());
                }else{//还是目录，继续遍历，直到是文件，再删除
                    ListFileFileter(file[i]);
                }
            }
            return files;
        }else{
            throw new RuntimeException("操作的文件或者目录不存在！");
        }
    }
}
