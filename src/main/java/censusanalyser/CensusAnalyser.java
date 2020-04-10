package censusanalyser;

import com.bl.csvbuilder.CsvBuilder;
import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IscvBuilder;
import com.google.gson.Gson;
import com.opencsv.ICSVParser;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    List<IndiaCensusDAO> csvFileList;

    public CensusAnalyser() {
        this.csvFileList = new ArrayList<IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CesusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            IscvBuilder CsvBuilder = CSVBuilderFactory.getCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = CsvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvIterator.hasNext()) {
                this.csvFileList.add(new IndiaCensusDAO(csvIterator.next()));
            }
            return csvFileList.size();
        } catch (IOException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        } catch (CsvFileBuilderException e) {
            throw new CesusAnalyserException(e.getMessage(), e.type);
        }
    }

    public int loadIndiaStateCode(String csvFilePath) throws CesusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            IscvBuilder CsvBuilder = CSVBuilderFactory.getCSVBuilder();
            List<IndiaStateCodeCSV> csvFileList = CsvBuilder.getCSVFileList(reader, IndiaStateCodeCSV.class);
            return csvFileList.size();
        } catch (IOException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        } catch (CsvFileBuilderException e) {
            throw new CesusAnalyserException(e.getMessage(), e.type);
        }
    }

    private <E> int getCount(Iterator<E> csvIterator) {
        Iterable<E> csvIterable = () -> csvIterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CesusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (csvFileList == null || csvFileList.size() == 0) {
            throw new CesusAnalyserException("Invalid file data type", CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        }

        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(csvFileList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < csvFileList.size(); i++) {
            for (int j = 0; j < csvFileList.size() - i - 1; j++) {
                IndiaCensusDAO census1 = csvFileList.get(j);
                IndiaCensusDAO census2 = csvFileList.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    csvFileList.set(j, census2);
                    csvFileList.set(j + 1, census1);
                }
            }
        }
    }
}