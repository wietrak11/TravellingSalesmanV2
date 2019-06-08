package TSV2;

import java.util.ArrayList;
import java.util.List;

public class GreedyAlg {

    private List<Tile> cityList = new ArrayList<Tile>();
    private Tile[][] tileArray;
    private Controller controller;
    private boolean fromFile;
    private FromFileOperator ffo;

    public GreedyAlg(Tile[][] tileArray, Controller controller) {
        this.tileArray = tileArray;
        this.controller = controller;
    }

    public GreedyAlg(Tile[][] tileArray, Controller controller, boolean fromFile, FromFileOperator ffo) {
        this.tileArray = tileArray;
        this.controller = controller;
        this.fromFile = fromFile;
        this.ffo = ffo;
    }

    public void main(){
        final long startTime = System.currentTimeMillis();
        Path path = new Path();
        greedyAlg(path);
        path.calcDistance();
        final long endTime = System.currentTimeMillis();

        if(fromFile){
            controller.showVisualizationFromFile(path, ffo.getCityList(), ffo.getMax());
        }else {
            controller.showVisualization(path);
        }


        System.out.println("GREEDY ALGORITHM");
        System.out.println("Path:");
        System.out.println("    " + path.getPathList());
        System.out.println("Distance: ");
        System.out.println("    " + path.getDistance());
        System.out.println("Time of greedy algorithm: " + (endTime-startTime) + " milliseconds");
        System.out.println();

        cityList.clear();
    }

    private void greedyAlg(Path path){
        int numberOfCities = countCities();

        for(int i=0 ; i<numberOfCities ; i++){
            generateCityList();
            findNearest(path);
        }
    }

    private void findNearest(Path path){

        List<Double> distanceList = new ArrayList<Double>();
        double nearest = 0.0;

        if(cityList.size()>1){
            nearest = calcDistance(cityList.get(0),cityList.get(1));
        }

        int index = 0;

        for(int i=1 ; i<cityList.size() ; i++){
            distanceList.add(calcDistance(cityList.get(0),cityList.get(i)));
        }

        for(int i=0 ; i<distanceList.size() ; i++){
            if(distanceList.get(i)<nearest){
                nearest = distanceList.get(i);
                index = i;
            }
        }
        if(cityList.size()==1){
            path.addToPath(cityList.get(0).getPoint());
            cityList.get(0).setVisited(true);
            path.addToPath(path.getPathList().get(0));
        }
        else {
            path.addToPath(cityList.get(0).getPoint());
            if(fromFile){
                cityList.get(index+1).setWithoutPaneStartedCity(true);
            }else{
                cityList.get(index+1).setStartedCity(true);
            }
            cityList.get(0).setVisited(true);
        }
    }

    private int countCities(){
        int counter = 0;

        for(int i = 0; i< tileArray.length; i++) {
            for (int j = 0; j < tileArray[0].length; j++) {
                if (tileArray[i][j].isCity()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private double calcDistance(Tile x1, Tile x2){

        double distance = 0.0;

        double xDis = x1.getPoint().getX() - x2.getPoint().getX();
        xDis = Math.pow(xDis,2);
        double yDis = x1.getPoint().getY() - x2.getPoint().getY();
        yDis = Math.pow(yDis,2);

        distance = Math.sqrt(xDis + yDis);

        return distance;
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
