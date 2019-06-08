package TSV2;

import javafx.scene.layout.Pane;

public class Tile {

    private Pane pane;
    private boolean city;
    private boolean startedCity;
    private boolean visited;
    private Point point;

    public Tile(Pane pane, boolean City, boolean isStartedCity, Point point){
        this.pane = pane;
        this.city = City;
        this.startedCity = isStartedCity;
        this.point = point;
    }

    public Tile(boolean City, boolean isStartedCity, Point point) {
        this.city = City;
        this.startedCity = isStartedCity;
        this.point = point;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public boolean isCity() {
        return city;
    }

    public void setCity(boolean city) {
        this.city = city;

        if(city){
            pane.setStyle("-fx-background-color: blue;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
        }else{
            pane.setStyle("-fx-background-color: lightgrey;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
        }
    }

    public void setWithoutPaneCity(boolean city) {
        this.city = city;
    }

    public boolean isStartedCity() {
        return startedCity;
    }

    public void setStartedCity(boolean startedCity) {
        this.startedCity = startedCity;

        if(startedCity){
            pane.setStyle("-fx-background-color: red;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
        }else if(city){
            pane.setStyle("-fx-background-color: blue;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
        }else {
            pane.setStyle("-fx-background-color: lightgrey;" +
                    "-fx-border-color: black;" +
                    "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
        }
    }

    public void setWithoutPaneStartedCity(boolean startedCity) {
        this.startedCity = startedCity;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String toString(){
        /*
        if(startedCity){
            return "s";
        }else if(city){
            return "c";
        }else if(!startedCity && !city){
            return "n";
        }
        return "ERROR";
        */

        return point.toString();
    }
}