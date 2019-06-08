package TSV2;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private List<Point> pathList = new ArrayList<Point>();
    private double distance = 0.0;

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
        for(int i=0 ; i<pathList.size()-1 ; i++){
            distance = distance + calcOneDistance(pathList.get(i),pathList.get(i+1));
        }
    }

    private double calcOneDistance(Point x1, Point x2){

        double distance = 0.0;

        double xDis = x1.getX() - x2.getX();
        xDis = Math.pow(xDis,2);
        double yDis = x1.getY() - x2.getY();
        yDis = Math.pow(yDis,2);

        distance = Math.sqrt(xDis + yDis);

        return distance;
    }
}
