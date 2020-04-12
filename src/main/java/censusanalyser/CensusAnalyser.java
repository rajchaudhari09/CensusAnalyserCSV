package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;
import com.bl.csvbuilder.IscvBuilder;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String,CensusCSVDao> censusCSVMap=null;
    public int loasUSCensusData;

    public CensusAnalyser() {
        this.censusCSVMap = new HashMap<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CesusAnalyserException {
        if (!csvFilePath.contains(".csv"))
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_TYPE);
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            IscvBuilder CsvBuilder = CSVBuilderFactory.getCSVBuilder();
        } catch (IOException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("header!"))
                throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
            throw new CesusAnalyserException("Invalid file type", CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        }
        return 0;
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
            throw new CesusAnalyserException(e.getMessage(), e.type.name());
        }
    }


    private <E> int getCount(Iterator<E> csvIterator) {
        Iterable<E> csvIterable = () -> csvIterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return numOfEnteries;
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CesusAnalyserException {
        loadIndiaCensusData(csvFilePath);
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CesusAnalyserException("Invalid file data type", CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        }

        Comparator<CensusCSVDao> censusComparator = Comparator.comparing(census -> census.state);
        List<USCensusCSV> censusDAOS = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS,censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }

    private void sort(List<USCensusCSV> censusDAOS,Comparator<CensusCSVDao> censusComparator){
        for (int i = 0; i < censusCSVMap.size(); i++) {
            for (int j = 0; j < censusCSVMap.size() - i - 1; j++) {
                CensusCSVDao census1 = censusCSVMap.get(j);
                CensusCSVDao census2 = censusCSVMap.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }
    public <CsvFileBuilderExceptione extends Throwable> int loadUSCensusData(String usCensusCsvFilePath) throws CsvFileBuilderExceptione {
        try (Reader reader = Files.newBufferedReader(Paths.get(usCensusCsvFilePath))) {
            IscvBuilder csvBuilder = CSVBuilderFactory.getCSVBuilder();
            Iterator<USCensusCSV> iterator = csvBuilder.getCSVFileIterator(reader, USCensusCSV.class);
            Iterable<USCensusCSV> iterable = () -> iterator;
            StreamSupport.stream(iterable.spliterator(),false)
                    .forEach(UsCensus -> censusCSVMap.put(UsCensus.stateName,new CensusCSVDao(UsCensus)));
            return censusCSVMap.size();

        } catch (IOException e) {

            try {
                throw new CesusAnalyserException(e.getMessage(),
                        CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
            } catch (CesusAnalyserException ex) {
                ex.printStackTrace();
            }

        } catch (RuntimeException e) {


        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }


        return 0;
    }



}