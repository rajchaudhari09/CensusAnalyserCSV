package censusanalyser;

import com.opencsv.bean.CsvBindByName;

public class USCensusCSV {

    @CsvBindByName(column = "State Id" , required = true)
    public String stateId;

    @CsvBindByName(column = "State" , required = true)
    public String stateName;

    @CsvBindByName(column = "Population" , required = true)
    public double population;

    @CsvBindByName(column = "Housing units" , required = true)
    public double housingUnits;

    @CsvBindByName(column = "Total area" , required = true)
    public double totalArea;

    @CsvBindByName(column = "Water area" , required = true)
    public double waterArea;

    @CsvBindByName(column = "Land area" , required = true)
    public double landArea;

    @CsvBindByName(column = "Population Density" , required = true)
    public double populationDensity;

    @CsvBindByName(column = "Housing Density" , required = true)
    public double housingdensity;
}
