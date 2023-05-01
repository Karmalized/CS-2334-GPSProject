import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import org.openstreetmap.gui.jmapviewer.tilesources.OsmTileSource;



public class Driver {
	
	// Declare class data
	public static ArrayList<Coordinate> thisTrip;
	private static IconMarker raccoon;
	private static JCheckBox checkbox;
	static JButton playButton;
	static JMapViewer mapViewer;
	private static BufferedImage img;
	private static Color setColor;

    public static void main(String[] args) throws FileNotFoundException, IOException {

    	// Read file and call stop detection
    	TripPoint.readFile("triplog.csv");
    	TripPoint.h1StopDetection();
    	TripPoint.h2StopDetection();
    	
    	
    	// Set up frame, include your name in the title
    	JFrame frame = new JFrame("Matthew Tran");
    	frame.setSize(800,800);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setVisible(true);
        
        // Set up Panel for input selections
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setSize(JMapViewer.WIDTH, JMapViewer.HEIGHT);
    	
        // Play Button
        playButton = new Button("Play");
        playButton.setVisible(true);
        try {
			img = ImageIO.read(new File("raccoon.png"));
		} catch (IOException m) {
			System.out.println("no raccoon :(");
		}

    	
        // CheckBox to enable/disable stops
        CheckBox checkbox = new CheckBox("Include Stops",false);
        checkbox.setVisible(true);
//        checkbox.setAlignmentX(FlowLayout.CENTER);
    	
        // ComboBox to pick animation time
        ComboBox combobox = new ComboBox();
        combobox.setVisible(true);
//        combobox.setAlignmentX(FlowLayout.LEFT);
        
        String[] colors = {"Pick A Color", "BLACK", "BLUE", "CYAN", "DARK_GRAY", "GRAY", "GREEN", "LIGHT_GRAY", "MAGENTA", "ORANGE", "PINK", "RED", "WHITE", "YELLOW"};
        JComboBox colorbox = new JComboBox(colors);
        colorbox.setVisible(true);
        
    	
        // Add all to top panel
        panel.add(combobox);
        panel.add(checkbox);
        panel.add(playButton);
        panel.add(colorbox);
        
        
        // Set up mapViewer
        mapViewer = new JMapViewer();
    	mapViewer.setTileSource(new OsmTileSource.TransportMap());
    	mapViewer.setOpaque(true);
    	mapViewer.setVisible(true);
    	frame.getContentPane().add(mapViewer, BorderLayout.CENTER);
    	frame.getContentPane().add(panel, BorderLayout.NORTH);
    	panel.setVisible(true);
        
        
        // Add listeners for GUI components
        checkbox.addItemListener();
        combobox.addActionListener();
        playButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        			thisTrip = new ArrayList<>();
        			Driver.mapViewer.removeAllMapPolygons();
        			if(CheckBox.getIsChecked() == true) {
        				for(TripPoint trip : TripPoint.getTrip()) {
        					thisTrip.add(new Coordinate(trip.getLat(),trip.getLon()));
        				}
        			} else {
        				for(TripPoint trip : TripPoint.getMovingTrip()) {
        					thisTrip.add(new Coordinate(trip.getLat(),trip.getLon()));
        				}
        			}
        				raccoon = new IconMarker(thisTrip.get(0),img);
        				raccoon.setVisible(true);
        				Timer t = new Timer((ComboBox.getDuration()*1000)/thisTrip.size(),timeListener);
        				t.setRepeats(true);
        				t.start();
        		
        				
        	}
        });
        
        colorbox.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		JComboBox list = (JComboBox)e.getSource();
        		String color = (String)list.getSelectedItem();
        		System.out.println(color);
        		if(color == "BLACK")
        			setColor = Color.BLACK;
        		if(color == "BLUE")
        			System.out.println(color);
        			setColor = Color.BLUE;
        		if(color == "CYAN")
        			setColor = Color.CYAN;
        		if(color == "DARK_GRAY")
        			setColor = Color.DARK_GRAY;
        		if(color == "GRAY")
        			setColor = Color.GRAY;
        		if(color == "GREEN")
        			setColor = Color.GREEN;
        		if(color == "LIGHT_GRAY")
        			setColor = Color.LIGHT_GRAY;
        		if(color == "MAGENTA")
        			setColor = Color.MAGENTA;
        		if(color == "ORANGE")
        			setColor = Color.ORANGE;
        		if(color == "PINK")
        			setColor = Color.PINK;
        		if(color == "RED")
        			setColor = Color.RED;
        		if(color == "WHITE")
        			setColor = Color.WHITE;
        		if(color == "YELLOW")
        			setColor = Color.YELLOW;
        		
        	}
        });

        // Set the map center and zoom level
        TripPoint center = TripPoint.getMovingTrip().get(TripPoint.getMovingTrip().size()/4);
        Coordinate middlePoint = new Coordinate(center.getLat(),center.getLon());
        mapViewer.setDisplayPosition(middlePoint, 5);
        panel.revalidate();
        
        
    }
    
    static ActionListener timeListener = new ActionListener() {
		int i = 1;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(i < thisTrip.size()) {
			System.out.println("mapping");
//			Driver.mapViewer.addMapMarker(raccoon);
			ArrayList<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(thisTrip.get(i-1),thisTrip.get(i),thisTrip.get(i)));
			MapPolygonImpl poly = new MapPolygonImpl(route);
			poly.setColor(setColor);
			Driver.mapViewer.addMapPolygon(poly);
			Driver.mapViewer.removeAllMapMarkers();
			Driver.mapViewer.addMapMarker(new IconMarker(route.get(route.size()-1),img));
			i++;
			} else {
			System.out.println("Journey Complete");
			((Timer)e.getSource()).stop();
			i = 1;
			}
		}
	};
    
    // Animate the trip based on selections from the GUI components
    
    
}