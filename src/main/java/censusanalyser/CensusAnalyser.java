package censusanalyser;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        Iterator<IndiaCensusCSV> censusCSVIterator = CsvFileBuilder.getIterator(csvFilePath,IndiaCensusCSV.class);
        int numOfEntries = 0;
        Iterable<IndiaCensusCSV> indiaCensusCSVIterable = ()-> censusCSVIterator;
        numOfEntries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
        return numOfEntries;
    }

    public int loadIndiaStateCode(String csvFilePath) throws CensusAnalyserException {
        this.checkValidCsvFile(csvFilePath);
        Iterator<IndiaStateCodeCSV> stateCSVIterator = CsvFileBuilder.getIterator(csvFilePath,IndiaStateCodeCSV.class);
        int numOfEntries = 0;
        Iterable<IndiaStateCodeCSV> indiaCensusCSVIterable = () -> stateCSVIterator;
        numOfEntries = (int) StreamSupport.stream(indiaCensusCSVIterable.spliterator(),false).count();
        return numOfEntries;
    }

    public void checkValidCsvFile(String csvFilePath) throws CensusAnalyserException{
        if (!csvFilePath.contains(".csv")) {
            throw new CensusAnalyserException("Invalid file type", CensusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        }
    }
}