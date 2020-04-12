package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State Id" , required = true)
    String stateId;

    @CsvBindByName(column = "State" , required = true)
    String stateName;

    @CsvBindByName(column = "Population" , required = true)
    double population;

    @CsvBindByName(column = "Housing units" , required = true)
    double housingUnits;

    @CsvBindByName(column = "Total area" , required = true)
    double totalArea;

    @CsvBindByName(column = "Water area" , required = true)
    double waterArea;

    @CsvBindByName(column = "Land area" , required = true)
    double landArea;

    @CsvBindByName(column = "Population Density" , required = true)
    double populationDensity;

    @CsvBindByName(column = "Housing Density" , required = true)
    double housingdensity;
}
