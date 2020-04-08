package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.IscvBuilder;

import java.util.Iterator;

public class CSVBuilderFactory {

    public static CsvBuilder getCSVBuilder() {
        return new CsvBuilder();
    }
}
