package app.common.bridge;


import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexableField;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class DateTimeBridge implements TwoWayFieldBridge {

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneoptions) {


        DateTime datetime = (DateTime) value;
        int year = datetime.getYear();
        int month = datetime.getMonthOfYear();
        int day = datetime.getDayOfMonth();


        // set year
        luceneoptions.addFieldToDocument(name + ".year", String.valueOf(year), document);

        // set month
        luceneoptions.addFieldToDocument(name + ".month", String.format("%02d", month), document);


        // set day
        luceneoptions.addFieldToDocument(name + ".day", String.format("%02d", day), document);

    }


    @Override
    public Object get(String name, Document document) {

        IndexableField fieldyear = document.getField(name + ".year");
        IndexableField fieldmonth = document.getField(name + ".month");
        IndexableField fieldday = document.getField(name + ".day");
        String strdate = fieldday.stringValue() + "/" + fieldmonth.stringValue() + "/" + fieldyear.stringValue();
        DateTime value = DateTime.parse(strdate, DateTimeFormat.forPattern("dd/MM/yyyy"));
        return String.valueOf(value);
    }


    @Override
    public String objectToString(Object date) {

        DateTime datetime = (DateTime) date;
        int year = datetime.getYear();
        int month = datetime.getMonthOfYear();
        int day = datetime.getDayOfMonth();
        String value = String.format("%02d", day) + "/" + String.format("%02d", month) + "/" + String.valueOf(year);

        return String.valueOf(value);


    }

}