package TSV2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {

    private List<Point> pathList = new ArrayList<Point>();
    private List<Point> previousPathList = new ArrayList<Point>();
    private double distance = 0.0;

    public Path(Path path){
        this.pathList = path.pathList;
    }

    public Path(){
        pathList = new ArrayList<>();
    }

    public void addToPath(Point point){
        pathList.add(point);
    }

    public List<Point> getPathList() {
        return pathList;
    }

    public void setPathList(List<Point> pathList) {
        this.pathList = pathList;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void calcDistance(){
        distance = 0.0;
        for(int i=0 ; i<pathList.size()-1 ; i++){
            distance = distance + calcOneDistance(pathList.get(i),pathList.get(i+1));
        }
    }

    private double calcOneDistance(Point x1, Point x2){
        double xDis = x1.getX() - x2.getX();
        xDis = Math.pow(xDis,2);
        double yDis = x1.getY() - x2.getY();
        yDis = Math.pow(yDis,2);

        distance = Math.sqrt(xDis + yDis);

        return distance;
    }

    public void swapPoints(){

        int index1 = (int)(Math.random() * pathList.size());
        int index2 = (int)(Math.random() * pathList.size());
        while(index1 == index2 || index1 == 0 || index2 == 0 || index1 == pathList.size()-1 || index2 == pathList.size()-1){
            index1 = (int)(Math.random() * pathList.size());
            index2 = (int)(Math.random() * pathList.size());
        }

        previousPathList = new ArrayList<Point>();
        for(int i=0;i<pathList.size();i++){
            previousPathList.add(pathList.get(i));
        }


        Point x = pathList.get(index1);
        Point y = pathList.get(index2);
        pathList.set(index1, y);
        pathList.set(index2, x);
    }

    public void revertSwap(){
        pathList = previousPathList;
    }
}
