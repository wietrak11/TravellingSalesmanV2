package TSV2;

import javafx.fxml.FXML;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    //GUI properties
    private Stage stage;
    private int BOARD_TILE_WIDTH = 20;
    private int BOARD_TILE_HEIGHT = 20;
    private double TILE_WIDTH = 500/BOARD_TILE_WIDTH;
    private double TILE_HEIGHT = 500/BOARD_TILE_HEIGHT;
    private Tile[][] tileArray = new Tile[BOARD_TILE_HEIGHT][BOARD_TILE_WIDTH];

    //FXML properties
    @FXML
    private GridPane board;

    //Algorithms properties
    private List<Tile> cityList = new ArrayList<Tile>();

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setGrid(){
        for(int i = 0; i< BOARD_TILE_HEIGHT; i++){
            for(int j = 0; j< BOARD_TILE_WIDTH; j++){
                Tile tile = new Tile(new Pane(), false,false, new Point(j,i));
                tileArray[i][j] = tile;
                tileArray[i][j].getPane().setStyle("-fx-background-color: lightgrey;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 0.3px 0.3px 0.3px 0.3px;");
                tileArray[i][j].getPane().setPrefWidth(TILE_WIDTH);
                tileArray[i][j].getPane().setPrefHeight(TILE_HEIGHT);
                GridPane.setConstraints(tileArray[i][j].getPane(),j,i);
                board.getChildren().add(tileArray[i][j].getPane());
            }
        }
    }

    public void setMouseClickedListener(){
        board.addEventHandler(MouseEvent.MOUSE_CLICKED,
                me -> {
                    if(me.getX()>=0 && me.getX()<500 && me.getY()>=0 && me.getY()<500) {
                        if (me.getButton().equals(MouseButton.PRIMARY)) {
                            if(tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].isCity()==true){
                                if(tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].isStartedCity()==true){
                                    tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setStartedCity(false);
                                }else{
                                    tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setStartedCity(true);
                                }
                            }
                            else{
                                tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setCity(true);
                            }
                        }
                        if (me.getButton().equals(MouseButton.SECONDARY)) {
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setCity(false);
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setStartedCity(false);
                        }
                    }
                });
    }

    public void setMouseDraggedListener(){
        board.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                me -> {
                    if(me.getX()>=0 && me.getX()<500 && me.getY()>=0 && me.getY()<500){
                        if(me.isPrimaryButtonDown()) {
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setCity(true);
                        }
                        if(me.isSecondaryButtonDown()) {
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setCity(false);
                            tileArray[(int) (me.getY() / TILE_WIDTH)][(int) (me.getX() / TILE_HEIGHT)].setStartedCity(false);
                        }
                    }
                });
    }

    public void greedyClick(MouseEvent mouseEvent){

        GreedyAlg ga = new GreedyAlg(tileArray,this);
        ga.main();

    }

    public void saClick(MouseEvent mouseEvent){
        SimulatedAnnealing hca = new SimulatedAnnealing(tileArray, this);
        hca.main();
    }

    public void loadGClick(MouseEvent mouseEvent) {
        FromFileOperator ffo = new FromFileOperator(this);
        ffo.mainG();
    }

    public void loadSAClick(MouseEvent mouseEvent) {
        FromFileOperator ffo = new FromFileOperator(this);
        ffo.mainSA();
    }

    public void showVisualization(Path path){
        normalizeBoard(path.getPathList().get(0));
        generateCityList();
        double multiplier = (600/BOARD_TILE_WIDTH)-1;
        double radius = (1.0/(double)BOARD_TILE_WIDTH) * 200.0;
        Visualization vis = new Visualization(cityList,path.getPathList(),multiplier,radius);
        vis.start(stage);
    }

    public void showVisualizationFromFile(Path path, List<Tile> cityList, int max){
        double multiplier = 0.53;
        double radius = (1.0/(double)max) * 200.0;

        Visualization vis = new Visualization(cityList,path.getPathList(),multiplier,radius);
        vis.start(stage);
    }

    private void normalizeBoard(Point point){
        for(int i = 0; i< tileArray.length; i++) {
            for (int j = 0; j < tileArray[0].length; j++) {
                if(tileArray[i][j].isCity()) {
                    tileArray[i][j].setVisited(false);
                    tileArray[i][j].setStartedCity(false);
                }
            }
        }
        tileArray[point.getY()][point.getX()].setStartedCity(true);
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

    private <T> void showArray(T[][] array){
        System.out.print("{");
        for(int i=0 ; i<array.length ; i++){
            System.out.print("{");
            for(int j=0 ; j<array[0].length ; j++){
                System.out.print(array[i][j] + " ");
            }
            System.out.print("},");
            System.out.println();
        }
        System.out.print("};");
        System.out.println();
        System.out.println();
    }
}
