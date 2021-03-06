package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE = "./src/test/resources/CensusData.txt";
    private static final String WRONG_CSV_FILE_DILEMETER = "./src/test/resources/CesusinvalidDelimeter.csv";
    private static final String WRONG_CSV_FILE_HEADER = "./src/test/resources/CesusinvalidHeader.csv";
    private static final String INDIA_STATE_CODE_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String US_CENSUS_CSV_FILE_PATH = "./src/test/resources/USCensusData.csv";

   @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(30, numOfRecords);
        } catch (CesusAnalyserException e) {
            e.printStackTrace();
        }catch (CsvFileBuilderException e){
            e.printStackTrace();
        }
   }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_TYPE);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongFileDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_DILEMETER);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_HEADER);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStateCodes = censusAnalyser.loadIndiaStateCode(INDIA_STATE_CODE_FILE_PATH);
            Assert.assertEquals(37, numOfStateCodes);
        } catch (CesusAnalyserException e) {
            e.printStackTrace();
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {

        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Type indianCsvType = new TypeToken<LinkedHashMap<String, IndiaCensusCSV>>() {
            }.getType();
            LinkedHashMap<String, IndiaCensusCSV> sortedCsvData = new Gson().fromJson(sortedCensusData, indianCsvType);
            String firstState = (String) sortedCsvData.keySet().toArray()[11];
            Assert.assertEquals("Andhra Pradesh", firstState);

        } catch (Exception e) {
           e.printStackTrace();
        }
    }
    @Test
    public void givenIndianCensusData_WhenSortedOnStateWrong_ShouldReturnWrongSortedResult() {
        String sortedCensusData = null;
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            sortedCensusData = censusAnalyser.getStateWiseSortedCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Type indianCsvType = new TypeToken<LinkedHashMap<String, IndiaCensusCSV>>() {
            }.getType();
            LinkedHashMap<String, IndiaCensusCSV> sortedCsvData = new Gson().fromJson(sortedCensusData, indianCsvType);
            String firstState = (String) sortedCsvData.keySet().toArray()[0];
            Assert.assertEquals("Andhra Pradesh", firstState);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.INVALID_FILE_DATA_TYPE,e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void givenIndiaStateCode_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_CSV_FILE_PATH);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_CSV_FILE_TYPE);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_WithWrongFileDelimeter_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_CSV_FILE_DILEMETER);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndianStateCode_WithWrongFileHeader_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CesusAnalyserException.class);
            censusAnalyser.loadIndiaStateCode(WRONG_CSV_FILE_HEADER);
        } catch (CesusAnalyserException e) {
            Assert.assertEquals(CesusAnalyserException.ExceptionType.INVALID_FILE_HEADER, e.type);
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenUSCSVFileReturnsCorrectRecords() throws CesusAnalyserException {
        CensusAnalyser censusAnalyser = null;
        try {
            censusAnalyser = new CensusAnalyser();
        } catch (CesusAnalyserException e) {
            e.printStackTrace();
        } catch (CsvFileBuilderException e) {
            e.printStackTrace();
        }
        int numOfRecords = censusAnalyser.loadUSCensusData(US_CENSUS_CSV_FILE_PATH);
        Assert.assertEquals(51, numOfRecords);
    }
}