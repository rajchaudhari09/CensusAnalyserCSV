package censusanalyser;

public class CensusCSVDao {

        public String state;
        public double totalArea;
        public double populationDensity;
        public double population;

        public CensusCSVDao(IndiaCensusCSV censusCSV) {

            state = censusCSV.state;
            totalArea = censusCSV.areaInSqKm;
            populationDensity = censusCSV.densityPerSqKm;
            population = censusCSV.population;

        }

        public CensusCSVDao(USCensusCSV usCensusCSV) {

            state = usCensusCSV.stateName;
            totalArea = usCensusCSV.totalArea;
            populationDensity =usCensusCSV.populationDensity;
            population = usCensusCSV.population;
        }

        @Override
        public String toString() {
            return "IndiaCensusCSVDao{" +
                    "state='" + state + '\'' +
                    ", areaInSqKm=" + totalArea +
                    ", densityPerSqKm=" + populationDensity +
                    ", population=" + population +
                    '}';
        }

    }
