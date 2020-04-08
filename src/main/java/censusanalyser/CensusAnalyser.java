package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IscvBuilder;
import com.opencsv.ICSVParser;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CesusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            IscvBuilder CSVBuilderFactory = censusanalyser.CSVBuilderFactory.getCSVBuilder();
            Iterator<IndiaCensusCSV> censusCSVIterator =  CSVBuilderFactory.getIterator (reader,IndiaCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e){
            if (e.getMessage().contains("header!"))
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        } catch (CsvFileBuilderException e) {
            throw new CesusAnalyserException(e.getMessage(),e.type);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CesusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            IscvBuilder CSVBuilderFactory = censusanalyser.CSVBuilderFactory.getCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCsvIterator = CSVBuilderFactory.getIterator(reader,IndiaStateCodeCSV.class);
            return this.getCount(stateCsvIterator);
        } catch (IOException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        } catch (CsvFileBuilderException e) {
            throw new CesusAnalyserException(e.getMessage(),e.type);
        }
    }

    private <E> int getCount(Iterator<E> csvIterator){
        Iterable<E> csvIterable = () -> csvIterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(),false).count();
        return numOfEnteries;
    }
}