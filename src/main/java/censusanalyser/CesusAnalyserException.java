package censusanalyser;

import com.bl.csvbuilder.CsvFileBuilderException;

public class CesusAnalyserException extends Exception {
    public CesusAnalyserException(ExceptionType censusFileProblem) {

    }

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,
        INVALID_FILE_TYPE,
        INVALID_FILE_DATA_TYPE,
        INVALID_FILE_HEADER;
    }

    ExceptionType type;

    public CesusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

    public CesusAnalyserException(String message, ExceptionType type, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public CesusAnalyserException(String message, CsvFileBuilderException.ExceptionType type) {
        super(message);
        this.type = ExceptionType.valueOf(type.name());
    }

    public CesusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
}