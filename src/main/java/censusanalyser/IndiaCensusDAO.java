package censusanalyser;

import javax.swing.text.Utilities;

public class IndiaCensusDAO {
    public String state;
    public int densityPerSqKm;
    public int areaInSqKm;
    public int population;
    public String stateCode;

    public IndiaCensusDAO(IndiaCensusCSV indiaCensusCSV) {
        densityPerSqKm = indiaCensusCSV.densityPerSqKm;
        areaInSqKm = indiaCensusCSV.areaInSqKm;
        population = indiaCensusCSV.population;
        state = indiaCensusCSV.state;
    }
}
