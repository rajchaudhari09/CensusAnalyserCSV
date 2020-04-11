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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {

    Map<String, IndiaCensusDAO> csvFileMap;

    public CensusAnalyser() {
        this.csvFileMap = new HashMap<String,IndiaCensusDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CesusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            IscvBuilder csvBuilder = CSVBuilderFactory.getCSVBuilder();
            Iterator<IndiaCensusCSV> csvIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            Iterable<IndiaCensusCSV> censusCSVIterable = () -> csvIterator;
            StreamSupport.stream(censusCSVIterable.spliterator(),false).forEach(censusCSV -> csvFileMap.put(censusCSV.state, new IndiaCensusDAO(censusCSV)));
            return csvFileMap.size();
        } catch (IOException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CesusAnalyserException(e.getMessage(), e.type.name());
        }  catch (NullPointerException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE);
        }
        catch (RuntimeException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER);
        }

    }

    public int loadIndiaStateCode(String indiaCensusCSVFilePath) throws CesusAnalyserException {
        int count = 0;
        try (Reader reader = Files.newBufferedReader(Paths.get(indiaCensusCSVFilePath))) {
            IscvBuilder csvBuilder = CSVBuilderFactory.getCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaStateCodeCSV.class);
            while (stateCodeCSVIterator.hasNext()){
                count++;
                IndiaStateCodeCSV indiaStateCodeCSV = stateCodeCSVIterator.next();
                IndiaCensusDAO indiaCensusDAO = csvFileMap.get(indiaStateCodeCSV.stateName);
                if(indiaCensusDAO == null) continue;
                indiaCensusDAO.stateCode = indiaStateCodeCSV.stateCode;
            }
            return count;
        } catch (IOException e) {
            throw new CesusAnalyserException(e.getMessage(),
                    CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CsvFileBuilderException e) {
            throw new CesusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> iterable = () -> iterator;
        int namOfEateries = (int) StreamSupport.stream(iterable.spliterator(), false).count();
        return namOfEateries;
    }

    public String getStateWiseSortedCensusData(String csvFilePath) throws CesusAnalyserException {
        if (csvFileMap == null || csvFileMap.size() == 0) {
            throw new CesusAnalyserException("NO_CENSUS_DATA", CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
        Comparator<IndiaCensusDAO> censusComparator = Comparator.comparing(census -> census.state);
        List<IndiaCensusDAO> censusDAOS = csvFileMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusComparator );
        String sortedStateCensusJson = new Gson().toJson(censusDAOS);
        return sortedStateCensusJson;
    }


    private void sort(List<IndiaCensusDAO> censusDAOS, Comparator<IndiaCensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size(); i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                IndiaCensusDAO census1 = censusDAOS.get(j);
                IndiaCensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }

            }

        }
    }
}