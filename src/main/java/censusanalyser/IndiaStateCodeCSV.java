package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class IndiaStateCodeCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public String srno;

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public String tin;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndiaStateCodeCSV{" +
                "SrNo ='" + srno + '\'' +
                ", StateName ='" + stateName + '\'' +
                ", Tin ='" + tin + '\'' +
                ", StateCode ='" + stateCode + '\'' +
                '}';
    }
}
