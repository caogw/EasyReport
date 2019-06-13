package com.easytoolsoft.easyreport.engine;

import com.easytoolsoft.easyreport.engine.data.*;
import com.easytoolsoft.easyreport.engine.query.Queryer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表产生器类
 *
 * @author tomdeng
 */
public class ReportGenerator {

    /**
     * @param ds
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(final ReportDataSource ds, final ReportParameter parameter) {
        return generate(getDataSet(ds, parameter), parameter);
    }

    /**
     * @param queryer
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(final Queryer queryer, final ReportParameter parameter) {
        return generate(getDataSet(queryer, parameter), parameter);
    }

    /**
     * @param metaDataSet
     * @param parameter
     * @return
     */
    public static ReportTable generate(final ReportMetaDataSet metaDataSet, final ReportParameter parameter) {
        return generate(getDataSet(metaDataSet, parameter), parameter);
    }

    /**
     * @param ds
     * @param parameter
     * @return
     */
    public static AbstractReportDataSet getDataSet(final ReportDataSource ds, final ReportParameter parameter) {
        return new DataExecutor(ds, parameter).execute();
    }

    /**
     * @param queryer
     * @param parameter
     * @return
     */
    public static AbstractReportDataSet getDataSet(final Queryer queryer, final ReportParameter parameter) {
        return new DataExecutor(queryer, parameter).execute();
    }

    /**
     * @param metaDataSet
     * @param parameter
     * @return
     */
    public static AbstractReportDataSet getDataSet(final ReportMetaDataSet metaDataSet,
                                                   final ReportParameter parameter) {
        return new DataExecutor(parameter).execute(metaDataSet);
    }

    /**
     * @param dataSet
     * @param parameter
     * @return ReportTable
     */
    public static ReportTable generate(final AbstractReportDataSet dataSet, final ReportParameter parameter) {
        final ReportBuilder builder = createBuilder(dataSet, parameter);
        final ReportDirector director = new ReportDirector(builder);
        director.build();
        ReportTable reportTable = builder.getTable();
        List<Map<String,Object>> rows = new ArrayList<>();
        for (ReportMetaDataRow reportMetaDataRow : dataSet.getMetaData().getRows()) {
            Map<String,Object> rowMap = new HashMap<>();
            for(Map.Entry<String, ReportMetaDataCell> entry:reportMetaDataRow.getCells().entrySet()){
//                System.out.println(entry.getValue().getName()+" "+entry.getValue().getValue());
                rowMap.put(entry.getValue().getName(),entry.getValue().getValue());
            }
            rows.add(rowMap);
        }
        reportTable.setRows(rows);
        return reportTable;
    }

    private static ReportBuilder createBuilder(final AbstractReportDataSet reportDataSet,
                                               final ReportParameter parameter) {
        if (parameter.getStatColumnLayout() == LayoutType.HORIZONTAL) {
            return new HorizontalStatColumnReportBuilder(reportDataSet, parameter);
        }
        return new VerticalStatColumnReportBuilder(reportDataSet, parameter);
    }
}
