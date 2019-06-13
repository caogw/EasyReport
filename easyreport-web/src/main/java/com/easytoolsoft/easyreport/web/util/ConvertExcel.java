package com.easytoolsoft.easyreport.web.util;

import me.chyxion.xls.TableToXls;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class ConvertExcel {
    /**
     * html 转换为EXCEL
     * @param tableHtml
     * @param out
     */
    public static void toCvt(String tableHtml, OutputStream out){
        StringBuilder html = new StringBuilder();
        Scanner s = new Scanner(tableHtml);
        while (s.hasNext()) {
            html.append(s.nextLine());
        }
        s.close();
        TableToXls.process(html, out);
    }
}
