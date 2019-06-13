package com.easytoolsoft.easyreport.web.util;

import com.google.common.collect.Lists;
import me.chyxion.xls.TableToXls;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;

public class ParseHtml {
    /**
     * html 转换为EXCEL
     * @param tableHtml
     * @param out
     */
    public static void toCvt(String tableHtml){

        List<String> heads = Lists.newArrayList();
        List<List<String>> allVals = Lists.newArrayList();
        Document doc = Jsoup.parse(tableHtml);
        List<Element> tables = doc.getElementsByTag("table");

        for (Element element : tables) {
            List<Element> theads = element.getElementsByTag("thead");
            for (Element thead : theads) {
                List<Element> trs = thead.getElementsByTag("tr");
                for (Element tr : trs) {
                    List<Element> ths = tr.getElementsByTag("th");
                    for (Element th : ths) {
//                        System.out.println(td.text());
                        heads.add(th.text());
                    }

                }
            }

            List<Element> tbodys = element.getElementsByTag("tbody");
            for (Element  tbody: tbodys) {
                List<Element> trs = tbody.getElementsByTag("tr");

                for (Element tr : trs) {
                    List<String> vals = Lists.newArrayList();
                    List<Element> tds = tr.getElementsByTag("td");
                    for (Element td : tds) {
                        vals.add(td.text());
                    }
                    allVals.add(vals);
                }
            }
            System.out.print(heads);
            System.out.print(allVals);
        }
    }

    public static void toImg(String tableHtml,HttpServletResponse response){

        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        // 指定生成的响应图片,一定不能缺少这句话,否则错误.
        response.setContentType("image/png");

        String style = "<style type=\"text/css\">\t.easyreport {\twidth: 100%;}.easyreport td:hover {\toverflow: visible;\twhite-space: normal;\tword-wrap: break-word;}.easyreport .easyreport-header td,.easyreport .easyreport-header td {\theight: 28px;\tbackground: #308ca5;\tborder-right: 1px solid #ccc;\tborder-top: none;\tcolor: #fff;\ttext-align: center;}.easyreport .easyreport-header th {\tbackground: #53b125;}.easyreport tr.easyreport-row {\tbackground: #e6f1f5}.easyreport th,.easyreport td {\tpadding: 8px 10px;\tborder-right: 1px solid #ccc;\tcolor: #36534f;}.easyreport .easyreport-fixed-column {\tbackground: #ecfbd2;\tfont-weight: bold;\tfont-size: 11px;\ttext-align: left;}.easyreport .selected {\tbackground: #fddc30;}.easyreport th {\tbackground: #f7fbfc;\tborder: 1px solid #ccc;\tfont-size: 11px;\tcolor: #333;\ttext-align: center;\tpadding:8px 2px 8px 2px;}.easyreport td {\tborder: 1px solid #ccc;\ttext-align: center;\tfont-size: 11px;\theight: 18px;\ttext-align: center;}</style>";

        JEditorPane ed = new JEditorPane("text/html",style+tableHtml);
        ed.setSize(800,500);

        //create a new image
        BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        //paint the editor onto the image
        SwingUtilities.paintComponent(image.createGraphics(),
                ed,
                new JPanel(),
                0, 0, image.getWidth(), image.getHeight());
        //save the image to file
        try {
            ImageIO.write((RenderedImage)image, "png", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {

    }
}
