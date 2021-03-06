/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import labyrinth.game.GameObjects.*;

/**
 *
 * @author D
 */
public class GameModel  {
    /**
     * all game objects.
     */
    GameObject[][] matrix;
    
    /**
     * list of dynamic game objects.
     */
    ArrayList<DynamicGameObject> dynamicObjects;
    
    /**
     * life-manager.
     */
    LifeManager lifeManager;
    
    /**
     * inventory for keys.
     */
    InventoryManager inventory;
    
    /**
     * filename.
     */
    String filename;
    
    /**
     * properties.
     * 
     * 
    */
    Properties props;
    
    /**
     * boolean if player was created.
     */
    boolean playerCreated = false;
    
    /**
     * player position.
     */
    PositionInfo playerPosition;
    
    /**
     * remove level when won.
     */
    public boolean removeWhenWon = false;
    
    /**
     * 
     * generates a GameModel with Properties-File.
     * @param file 
     */
    
    public GameModel(String file) throws Exception {
        this.filename = file;   
        
        this.load();
        if(props != null) {
            
            // get chunks of level
            String[][] levelChunks = loadLevelChunks(props);
            
            // generate Objects for level
            this.setLevel(levelChunks);

        } else {
            throw new GameFileNotFoundOrMalformedException(file);
        }
        
        this.lifeManager = new LifeManager();
        if(props.getProperty("lifes") != null) {
            try {
                int lifes = Integer.parseInt(props.getProperty("lifes"));
                if(lifes > 0) {
                    this.lifeManager.setLifes(lifes);
                }
            } catch(Exception e) {}
        }
        
        this.inventory = new InventoryManager();
        if("true".equals(props.getProperty("hasKey"))) {
            this.inventory.add(new KeyObject());
        }
        
        createPlayer();
        
        //debug();
    }
    
    public void createPlayer() throws Exception {
        if(!playerCreated) {
            playerCreated = true;
            
            PositionInfo e = getFirstEntrance();
            if(e == null) {
                 throw new PlayerNotFoundException(" No Entrance found.");
            }
            
            Player p = new Player();
            
            
            if(e.x > 1 && matrix[e.x][e.y + 1] == null) {
                e.y = e.y + 1;
                insertPlayer(e, p);
            } else if(matrix[e.x + 1][e.y] == null) {
                e.x = e.x + 1;
                insertPlayer(e, p);
            } else {
               throw new PlayerNotFoundException(" No Valid position found." + e);
            }
            
            System.out.println("Set player on Position " + e);
        }
    }
    
    public void setMovableObjectPosition(DynamicGameObject d, PositionInfo pos) {
        if(d instanceof Player) {
            this.playerPosition = pos;
        }
    }
    
    public void insertPlayer(PositionInfo pos, Player p) {
        this.dynamicObjects.add(p);
        
        matrix[pos.x][pos.y] = p;
                
        p.x = pos.x;
        p.y = pos.y;

        playerPosition = pos;
    }
    
    public PositionInfo getFirstEntrance() {
        return getEntrance(0);
    }
    
    public PositionInfo getTopLeftByPlayer(PositionInfo topLeft, SizeInfo size, int safeBorder) {
        if(!this.isPlayerWithInSafeAreaX(topLeft, size, safeBorder)) {
            topLeft.x = Math.max(0, (int) Math.round(playerPosition.x - size.width / 2));
        }
        
        if(!this.isPlayerWithInSafeAreaY(topLeft, size, safeBorder)) {
            topLeft.y = Math.max(0, (int) Math.round(playerPosition.y - size.height / 2));
        }
        
        return topLeft;
    }
    
    public boolean isPlayerWithInSafeAreaX(PositionInfo topLeft, SizeInfo size, int safeBorder) {
        if(this.width() > size.width) {

            if(topLeft.x + safeBorder > playerPosition.x) {
                return false;
            }

            if(topLeft.x + size.width - safeBorder < playerPosition.x) {
                return false;
            }
        }
        
        return true;
    }
    
     public boolean isPlayerWithInSafeAreaY(PositionInfo topLeft, SizeInfo size, int safeBorder) {
        if(this.height() > size.height) {
        
            if(topLeft.y + safeBorder > playerPosition.y) {
                return false;
            }

            if(topLeft.y + size.height - safeBorder < playerPosition.y) {
                return false;
            }
        }
        
        return true;
    }
    
    public boolean isPlayerWithinSafeArea(PositionInfo topLeft, SizeInfo size, int safeBorder) {
        return isPlayerWithInSafeAreaX(topLeft, size, safeBorder) && isPlayerWithInSafeAreaY(topLeft, size, safeBorder);
    }
    
    public PositionInfo getEntrance(int e) {
        int f = 0;
        for(int i = 0; i < matrix.length; i++) {
           for(int j = 0; j < matrix[i].length; j++) {
               if(matrix[i][j] instanceof Eingang) {
                   if(e == f) {
                       return new PositionInfo(i, j);
                   }
                   f++;
               }
           }
        }
        return null;
    }
    
    public int width() {
        return matrix.length;
    }
    
    public int height() {
        if(matrix.length > 0) {
            return matrix[0].length;
        } else {
            return 0;
        }
    }
    
    public GameObject getObjectAt(PositionInfo position) {
        if(position.x < this.width() && position.y < this.height()) {
            return matrix[position.x][position.y];
        } else {
            return null;
        }
               
        
    }
    
    public void debug() {
        System.out.println("----");
        System.out.println("lifes: " + this.lifeManager.getLifes());
        System.out.println("Inventory has key: " + this.inventory.hasItemOfType(KeyObject.class));
        System.out.println("Map:");
        
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] != null) {
                    System.out.print(matrix[i][j].getPropertyRepresentation() + "\t");
                } else {
                    System.out.print("\t");
                }
                
            }
            System.out.print("\n");
        }
        
        System.out.println("----");
    }
    
    public void load() throws GameFileNotFoundOrMalformedException {
        FileInputStream in;
        props = new Properties();
        try {
            in = new FileInputStream(this.filename);
        } catch(FileNotFoundException e) {
            throw new GameFileNotFoundOrMalformedException(this.filename);
        }
        
        try {
            props.load(in);
            in.close();
        } catch(IOException e) {
            throw new GameFileNotFoundOrMalformedException(this.filename, true);
        }
    }
    
    public void setLevel(String[][] level) throws GameFileNotFoundOrMalformedException {
        
        dynamicObjects = new ArrayList();
        
        if(level.length == 0) {
            matrix = new GameObject[0][0];
            
            return;
        }
        
        int players = 0;
        
        // interpret matrix and generate Game Objects
        matrix = new GameObject[level.length][level[0].length];
        for(int k = 0; k < level.length; k++) {
            for(int l = 0; l < level[k].length; l++) {
                matrix[k][l] = GameObject.generate_object(level[k][l], k, l);
                
                if(matrix[k][l] instanceof DynamicGameObject) {
                    dynamicObjects.add((DynamicGameObject) matrix[k][l]);
                }
                
                if(matrix[k][l] instanceof Player) {
                    players++;
                    playerCreated = true;
                    playerPosition = new PositionInfo(k, l);
                }
            }
        }
        
        // check for player validity
        if(players > 1) {
            throw new GameFileNotFoundOrMalformedException("More than one player is not allowed.");
        }
        
    }
    
    public static String[][] loadLevelChunks(Properties props) {
        SizeInfo size = getHeightAndWidth(props);
        
        System.out.println("Size of Level: " + size);
        
        return fillArrayWithValues(size, props);
    }
    
    /**
     * fills an array with given size with Properties.
     * 
     * @param size
     * @param props
     * @return 
     */
    protected static String[][] fillArrayWithValues(SizeInfo size, Properties props) {
        String[][] level = new String[size.width][size.height];
        
        Enumeration<String> names = (Enumeration<String>) props.propertyNames();
        // get height and width
        while(names.hasMoreElements()) {
            String n = names.nextElement();
            PositionInfo position = extractPositionFromString(n);
            
            try {
                // check if can be treated as integer
                Integer.parseInt(props.getProperty(n));

                level[position.x][position.y] = props.getProperty(n).trim();
            } catch(Exception e) {
                
            }
        }
        
        return level;
    }
    
    /**
     * calculates size from properties either with Attributes Width and Height
     * or with maximum of index values.
     * 
     * @param props
     * @return 
     */
    public static SizeInfo getHeightAndWidth(Properties props) {
        
        if(props.getProperty("Height") != null && null != props.getProperty("Width")) {
            try {
                
                int width = Integer.parseInt(props.getProperty("Width"));
                int height = Integer.parseInt(props.getProperty("Height"));
                
                return new SizeInfo(width, height);
            } catch(Exception e){}
        }
        
        // fall back to Enumeration-Mode
        Enumeration<String> names = (Enumeration<String>) props.propertyNames();
        int width = 0;
        int height = 0;
        // get height and width
        while(names.hasMoreElements()) {
            String n = names.nextElement();
            PositionInfo position = extractPositionFromString(n);
            
            // check if this is currently "out of bounds"
            if(position != null) {
                if(position.x + 1 > width) {
                    width = position.x + 1;
                }
                
                if(position.y + 1 > height) {
                    height = position.y + 1;
                }
            }
        }
        
        return new SizeInfo(width, height);
    }
    
    public static PositionInfo extractPositionFromString(String s) {
        String[] data = s.split(",");
        if(data.length == 2) {
            try {
                int wi = Integer.parseInt(data[0]);
                int hi = Integer.parseInt(data[1]);

                return new PositionInfo(wi, hi);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public void removeFile() {
        File f = new File(this.filename);
        f.delete();
    }
    
    public String getFilename() {
        return this.filename;
    }
    
    public boolean save(String toFile) {
        
        System.out.println("Save to " + toFile);
        System.out.println("has key: " +  this.inventory.hasItemOfType(KeyObject.class));
        
        Properties prop = new Properties();
        
        prop.setProperty("Height", "" + matrix[0].length);
        prop.setProperty("Width", "" + matrix.length);
        prop.setProperty("lifes", "" + this.lifeManager.getLifes());
        prop.setProperty("hasKey", this.inventory.hasItemOfType(KeyObject.class) ? "true" : "false");
        
        for(int x = 0; x < matrix.length; x++) {
            for(int y = 0; y < matrix[0].length; y++) {
                if(matrix[x][y] != null) {
                    prop.setProperty(x + "," + y, matrix[x][y].getPropertyRepresentation());
                }
            }
        }
        
        if(!toFile.equals(this.filename)) {
            this.removeFile();
        }
        
        try {
            FileOutputStream fileOut = new FileOutputStream(new File(toFile));
            prop.store(fileOut, "Save! :D");
            fileOut.close();
            
            this.filename = toFile;
            
            
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}