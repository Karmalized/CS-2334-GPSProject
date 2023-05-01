import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.ToolTipManager;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

public class Button extends JButton implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String buttonLabel;
	BufferedImage img;
	MapPolygonImpl poly;
	public static ArrayList<Coordinate> thisTrip;
	public static Timer t;
	IconMarker raccoon;
	
	public Button(String string) {
		super(string);
		buttonLabel = string;
		
		try {
			img = ImageIO.read(new File("raccoon.png"));
		} catch (IOException m) {
			System.out.println("no raccoon :(");
		}
	}
	
	public void addActionListener() {
		super.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		int i = 0;
//			thisTrip = new ArrayList<>();
//			Driver.mapViewer.removeAllMapPolygons();
//			if(CheckBox.getIsChecked() == true) {
//				for(TripPoint trip : TripPoint.getTrip()) {
//					thisTrip.add(new Coordinate(trip.getLat(),trip.getLon()));
//				}
//			} else {
//				for(TripPoint trip : TripPoint.getMovingTrip()) {
//					thisTrip.add(new Coordinate(trip.getLat(),trip.getLon()));
//				}
//			}
//				raccoon = new IconMarker(thisTrip.get(0),img);
//				raccoon.setVisible(true);
//				poly = new MapPolygonImpl(thisTrip);
//				poly.setColor(Color.CYAN);
//				System.out.println(thisTrip.size());
//				int duration = (ComboBox.getDuration()*1000)/thisTrip.size();
//				System.out.println(duration);
//				t = new Timer((ComboBox.getDuration()*1000*2)/thisTrip.size(),timeListener);
//				System.out.println(t);
//				t.setRepeats(true);
//				t.start();
		
		if(e.getActionCommand().equals(buttonLabel))
			i++;
		System.out.println(i);	
				
	}
	
	ActionListener timeListener = new ActionListener() {
		int i = 1;
		boolean runOnce = false;
		@Override
		public void actionPerformed(ActionEvent e) {
			if(i < thisTrip.size() && runOnce == false) {
			System.out.println("mapping");
//			Driver.mapViewer.addMapMarker(raccoon);
			ArrayList<Coordinate> route = new ArrayList<Coordinate>(Arrays.asList(thisTrip.get(i-1),thisTrip.get(i),thisTrip.get(i)));
			Driver.mapViewer.addMapPolygon(new MapPolygonImpl(route));
			Driver.mapViewer.removeAllMapMarkers();
			Driver.mapViewer.addMapMarker(new IconMarker(route.get(route.size()-1),img));
			i++;
			} else {
			System.out.println("Journey Complete");
			((Timer)e.getSource()).stop();
			i = 1;
			runOnce = true;
			}
		}
	};

}
