/**
 *
 * @author Daniel Gruber
 * @copyright 2014 Daniel Gruber
 * @license GNU Lesser General Public License, version 3; see "LICENSE.txt"
 */
package labyrinth.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import labyrinth.game.GameObjects.*;

/**
 *
 * @author D
 */
public class GameModel {
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
     * 
     * generates a GameModel with Properties-File.
     * @param file 
     */
    
    public GameModel(String file) throws GameFileNotFoundOrMalformedException {
        this.filename = file;   
        
        this.load();
        if(props != null) {
            this.setLevel(loadLevelChunks(props));
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
        if(props.getProperty("hasKey") == "true") {
            this.inventory.add(new Key());
        }
    }
    
    public void debug() {
        System.out.println("----");
        System.out.println("lifes: " + this.lifeManager.getLifes());
        System.out.println("Inventory has key: " + this.inventory.hasItemOfType(Key.class));
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
                matrix[k][l] = GameObject.generate_object(level[k][l]);
                
                if(matrix[k][l] instanceof DynamicGameObject) {
                    dynamicObjects.add((DynamicGameObject) matrix[k][l]);
                }
                
                if(matrix[k][l] instanceof Player) {
                    players++;
                    playerCreated = true;
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
        String[][] level = new String[size.height][size.width];
        
        Enumeration<String> names = (Enumeration<String>) props.propertyNames();
        // get height and width
        while(names.hasMoreElements()) {
            String n = names.nextElement();
            SizeInfo position = extractPositionFromString(n);
            
            try {
                // check if can be treated as integer
                Integer.parseInt(props.getProperty(n));

                level[position.height][position.width] = props.getProperty(n).trim();
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
            SizeInfo position = extractPositionFromString(n);
            
            // check if this is currently "out of bounds"
            if(position != null) {
                if(position.width + 1 > width) {
                    width = position.width + 1;
                }
                
                if(position.height + 1 > height) {
                    height = position.height + 1;
                }
            }
        }
        
        return new SizeInfo(width, height);
    }
    
    public static SizeInfo extractPositionFromString(String s) {
        if(s.indexOf(',') != -1) {
            String w = s.substring(0, s.indexOf(',') - 1);
            String h = s.substring(s.indexOf(',') + 1);
            try {
                int wi = Integer.parseInt(w);
                int hi = Integer.parseInt(h);

                return new SizeInfo(wi, hi);
            } catch(Exception e) {

            }

        }
        
        return null;
    }
}

class GameFileNotFoundOrMalformedException extends Exception {
    public GameFileNotFoundOrMalformedException(String file) {
        super("The Properties-File of the Game was not found: " + file);
    }
    
    public GameFileNotFoundOrMalformedException(String file, boolean malformed) {
        super("The Properties-File of the Game is malformed: " + file);
    }
}