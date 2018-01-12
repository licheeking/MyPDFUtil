package com.intergraph;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;

public class Main {

    public static void main(String[] args) {
        String path = "D:\\12.1 OFFSHORE\\M0011\\M0011-MRY-12M010-VDB-02_R1(comment)\\M0011-MRY-12M010-VDB-02_R1(comment).pdf";
        String newName = "4.pdf";
        int from = 177;
        int end = 180;

        for (int i = 1; i < 6; i++) {
            partitionPdfFile(path, newName, from, end);
        }

    }

    /**
     * 截取pdfFile的第from页至第end页，组成一个新的文件名
     * @param pdfFile
     * @param subfileName
     * @param from
     * @param end
     */
    public static void partitionPdfFile(String pdfFile,
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
}
