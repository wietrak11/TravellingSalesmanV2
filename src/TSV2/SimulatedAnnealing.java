package TSV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulatedAnnealing {

    // PARAMETRY
    double startingTemperature = 10;
    int numberOfIterations = 100000;
    double coolingRate = 0.9999;


    Path path = new Path();
    private List<Tile> cityList = new ArrayList<Tile>();
    private Tile[][] tileArray;
    private Controller controller;
    private boolean fromFile;
    private FromFileOperator ffo;

    public SimulatedAnnealing(Tile[][] tileArray, Controller controller) {
        this.tileArray = tileArray;
        this.controller = controller;
    }

    public SimulatedAnnealing(Tile[][] tileArray, Controller controller, boolean fromFile, FromFileOperator ffo) {
        this.tileArray = tileArray;
        this.controller = controller;
        this.fromFile = fromFile;
        this.ffo = ffo;
    }

    public void main(){
        final long startTime = System.currentTimeMillis();
        generateCityList();
        simulateAnnealing();
        final long endTime = System.currentTimeMillis();

        if(fromFile){
            controller.showVisualizationFromFile(path, ffo.getCityList(), ffo.getMax());
        }else {
            controller.showVisualization(path);
        }

        path.calcDistance();

        System.out.println("SIMULATED ANNEALING ALGORITHM");
        System.out.println("Path:");
        System.out.println("    " + path.getPathList());
        System.out.println("Distance: ");
        System.out.println("    " + path.getDistance());
        System.out.println("Time of simulated annealing algorithm: " + (endTime-startTime) + " milliseconds");
        System.out.println();

        cityList.clear();
    }

    public void simulateAnnealing(){
        double bestDistance;
        double t = startingTemperature;

        generateRandomPath();

        Path temp = new Path();

        for(int i=0;i<path.getPathList().size();i++){
            temp.addToPath(path.getPathList().get(i));
        }

        path.calcDistance();
        bestDistance = path.getDistance();

        for(int i=0;i<numberOfIterations;i++){
            if(t > 0.1){
                temp.swapPoints();
                temp.calcDistance();

                double currentDistance = temp.getDistance();

                if(currentDistance < bestDistance){
                    bestDistance = temp.getDistance();
                }
                else if (Math.exp((bestDistance - currentDistance) / t) < Math.random()){
                    temp.revertSwap();
                }

                t *= coolingRate;
            } else {
                continue;
            }

        }
        path = temp;
    }

    private void generateRandomPath(){

        for(int i=1;i<cityList.size();i++){
            path.addToPath(cityList.get(i).getPoint());
        }


        Collections.shuffle(path.getPathList());
        path.addToPath(cityList.get(0).getPoint());
        path.getPathList().add(0, cityList.get(0).getPoint());
    }


    private void generateCityList(){
        cityList.clear();

        for(int i = 0; i< tileArray.length; i++) {
            for (int j = 0; j < tileArray[0].length; j++) {
                if(tileArray[i][j].isStartedCity() && !tileArray[i][j].isVisited()) {
                    cityList.add(tileArray[i][j]);
                }
            }
        }

        for(int i = 0; i< tileArray.length; i++) {
            for (int j = 0; j < tileArray[0].length; j++) {
                if(tileArray[i][j].isCity() && !tileArray[i][j].isStartedCity() && !tileArray[i][j].isVisited()) {
                    cityList.add(tileArray[i][j]);
                }
            }
        }
    }



}
