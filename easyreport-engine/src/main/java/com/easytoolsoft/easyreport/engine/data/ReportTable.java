package com.easytoolsoft.easyreport.engine.data;

import java.util.List;
import java.util.Map;

/**
 * 报表类
 *
 * @author tomdeng
 */
public class ReportTable {
    private final String htmlText;
    private final String sqlText;
    private final int metaDataRowCount;
    private final int metaDataColumnCount;
    private List<Map<String,Object>> rows;

    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }

    public ReportTable(final String htmlText, final String sqlText, final int metaDataRowCount,
                       final int metaDataColumnCount) {
        this.htmlText = htmlText;
        this.sqlText = sqlText;
        this.metaDataRowCount = metaDataRowCount;
        this.metaDataColumnCount = metaDataColumnCount;
    }

    public String getHtmlText() {
        return this.htmlText;
    }

    public String getSqlText() {
        return this.sqlText;
    }

    public long getMetaDataRowCount() {
        return this.metaDataRowCount;
    }

    public int getMetaDataColumnCount() {
        return this.metaDataColumnCount;
    }
}
