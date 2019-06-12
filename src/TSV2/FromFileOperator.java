package TSV2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FromFileOperator {

    int max=0;
    private List<Tile> cityList = new ArrayList<Tile>();
    private Tile[][] tileArray;
    private Controller controller;

    public FromFileOperator(Controller controller) {
        this.controller = controller;
    }

    public void mainG(){
        readFromFile();
        setTileArray();

        GreedyAlg ga = new GreedyAlg(tileArray,controller,true, this);

        ga.main();
    }

    public void mainSA(){
        readFromFile();
        setTileArray();

        SimulatedAnnealing sa = new SimulatedAnnealing(tileArray,controller,true, this);

        sa.main();
    }

    public void setTileArray(){

        tileArray = new Tile[max+1][max+1];

        for(int i = 0; i< max+1; i++){
            for(int j = 0; j< max+1; j++){
                Tile tile = new Tile(false,false, new Point(j,i));
                tileArray[i][j] = tile;
            }
        }

        for(int i=0 ; i<cityList.size() ; i++){
            if(i==0){
                tileArray[cityList.get(i).getPoint().getY()][cityList.get(i).getPoint().getX()].setWithoutPaneCity(true);
                tileArray[cityList.get(i).getPoint().getY()][cityList.get(i).getPoint().getX()].setWithoutPaneStartedCity(true);
            }else{
                tileArray[cityList.get(i).getPoint().getY()][cityList.get(i).getPoint().getX()].setWithoutPaneCity(true);
            }
        }
    }

    public void readFromFile(){
        try(BufferedReader br = new BufferedReader(new FileReader("city.txt"))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            List<Tile> tmpCityList = new ArrayList<Tile>();

            while(line != null){
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                if(!(line==null)){
                    String[] point = line.split("\\s");
                    int x = Integer.parseInt(point[0]);
                    int y = Integer.parseInt(point[1]);
                    Tile tile;
                    if(max==0){
                        tile = new Tile(true,true,new Point(x,y));
                    }else {
                        tile = new Tile(true,false,new Point(x,y));
                    }

                    if(x>max){
                        max = x;
                    }
                    if(y>max){
                        max = y;
                    }
                    cityList.add(tile);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Tile> getCityList() {
        return cityList;
    }

    public int getMax() {
        return max;
    }
}
