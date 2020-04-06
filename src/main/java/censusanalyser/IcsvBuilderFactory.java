package censusanalyser;

import java.util.Iterator;

public interface IcsvBuilderFactory {
    public <E> Iterator getIterator(String csvFilePath, Class csvClass)throws CensusAnalyserException;
}
