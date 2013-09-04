/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.svg;

import com.iitk.cimam.data.Vectors;
import com.iitk.cimam.data.VectorsFacade;
import com.iitk.cimam.svg.exception.InvalidSVGFileException;
import com.iitk.cimam.svg.exception.SVGParserException;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Chirag Sangani
 */
public class SVGParser {

    String SVG;
    Document doc;
    Node ParentNode;
    List<Node> Objects;

    public SVGParser(String SVG) throws InvalidSVGFileException, SVGParserException {
	try {
	    this.SVG = SVG;
	    doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(SVG)));
	    doc.normalizeDocument();
	} catch (SAXException ex) {
	    throw new InvalidSVGFileException(ex.getMessage());
	} catch (IOException ex) {
	    throw new SVGParserException(ex.getMessage());
	} catch (ParserConfigurationException ex) {
	    throw new SVGParserException(ex.getMessage());
	}
    }

    public void validate() throws InvalidSVGFileException {
	NodeList Nodes = doc.getElementsByTagName("svg");
	if (Nodes.getLength() != 1) {
	    throw new InvalidSVGFileException("there should be exactly one SVG object");
	}
	ParentNode = Nodes.item(0);
	Objects = new LinkedList<Node>();
	NodeList polygons = doc.getElementsByTagName("polygon");
	for (int i = 0; i < polygons.getLength(); i++) {
	    Objects.add(polygons.item(i));
	}
	NodeList polylines = doc.getElementsByTagName("polyline");
	for (int i = 0; i < polylines.getLength(); i++) {
	    Objects.add(polylines.item(i));
	}
	NodeList rects = doc.getElementsByTagName("rect");
	for (int i = 0; i < rects.getLength(); i++) {
	    Objects.add(rects.item(i));
	}
	NodeList paths = doc.getElementsByTagName("path");
	for (int i = 0; i < paths.getLength(); i++) {
	    Objects.add(paths.item(i));
	}

	for (int i = 0; i < Objects.size(); i++) {
	    //	Check if all objects are supported
	    Node object = Objects.get(i);
	    if (!object.getNodeName().equals("polygon") & !object.getNodeName().equals("rect") && !object.getNodeName().equals("path") && !object.getNodeName().equals("polyline")) {
		throw new InvalidSVGFileException("only polygon, rect and path are supported");
	    }
	    if (object.getNodeName().equals("polygon") || object.getNodeName().equals("polyline")) {
		if (!object.hasAttributes()) {
		    throw new InvalidSVGFileException("polygon object has no attributes");
		}

		NamedNodeMap attributes = object.getAttributes();

		//  Polygon must have a points attribute
		Node points = attributes.getNamedItem("points");
		if (points == null) {
		    throw new InvalidSVGFileException("missing polygon points attribute");
		}

		//  Points must be a space-separated list of coordinates
		String[] uncleanCoordinates = points.getNodeValue().trim().replaceAll("[\\r\\n]*", "").replaceAll("[\\t]*", "").split(" ");
		List<String> coordinates = new LinkedList<String>();
		for (int j = 0; j < uncleanCoordinates.length; j++) {
		    if (!uncleanCoordinates[j].isEmpty()) {
			coordinates.add(uncleanCoordinates[j]);
		    }
		}
		for (String coordinate : coordinates) {
		    String x = "", y = "";
		    try {
			x = coordinate.split(",")[0];
			y = coordinate.split(",")[1];
		    } catch (ArrayIndexOutOfBoundsException ex) {
			throw new InvalidSVGFileException("individual points in a polygon must be a comma-separated pair of values");
		    }
		    try {
			Double.parseDouble(x);
			Double.parseDouble(y);
		    } catch (NumberFormatException ex) {
			throw new InvalidSVGFileException("polygon point coordinates must be a valid number");
		    }
		}

		//  Does the polygon have a highlight attribute?
		Node highlight = attributes.getNamedItem("fill");
		if (highlight != null) {
		    if (highlight.getNodeValue().length() != 7) {
			throw new InvalidSVGFileException("fill attribute value must be a 6 digit hexadecimal number prefixed with #");
		    }
		    try {
			Integer.parseInt(highlight.getNodeValue().substring(1), 16);
		    } catch (NumberFormatException ex) {
			throw new InvalidSVGFileException("fill attribute value must be a 6 digit hexadecimal number prefixed with #");
		    }
		}
	    }

	    if (object.getNodeName().equals("rect")) {
		if (!object.hasAttributes()) {
		    throw new InvalidSVGFileException("rect object has no attributes");
		}

		NamedNodeMap attributes = object.getAttributes();

		//  Rect must have x, y, width and height attribute
		Node x = attributes.getNamedItem("x");
		if (x == null) {
		    throw new InvalidSVGFileException("missing rect x attribute");
		}
		Node y = attributes.getNamedItem("y");
		if (y == null) {
		    throw new InvalidSVGFileException("missing rect y attribute");
		}
		Node width = attributes.getNamedItem("width");
		if (width == null) {
		    throw new InvalidSVGFileException("missing rect width attribute");
		}
		Node height = attributes.getNamedItem("height");
		if (height == null) {
		    throw new InvalidSVGFileException("missing rect height attribute");
		}

		//  x, y, width and height must be numbers
		try {
		    Double.parseDouble(x.getNodeValue());
		} catch (NumberFormatException ex) {
		    throw new InvalidSVGFileException("rect x attribute must be a number");
		}
		try {
		    Double.parseDouble(y.getNodeValue());
		} catch (NumberFormatException ex) {
		    throw new InvalidSVGFileException("rect y attribute must be a number");
		}
		try {
		    Double.parseDouble(width.getNodeValue());
		} catch (NumberFormatException ex) {
		    throw new InvalidSVGFileException("rect width attribute must be a number");
		}
		try {
		    Double.parseDouble(height.getNodeValue());
		} catch (NumberFormatException ex) {
		    throw new InvalidSVGFileException("rect height attribute must be a number");
		}

		//  Does the rect have a highlight attribute?
		Node highlight = attributes.getNamedItem("fill");
		if (highlight != null) {
		    if (highlight.getNodeValue().length() != 7) {
			throw new InvalidSVGFileException("fill attribute value must be a 6 digit hexadecimal number prefixed with #");
		    }
		    try {
			Integer.parseInt(highlight.getNodeValue().substring(1), 16);
		    } catch (NumberFormatException ex) {
			throw new InvalidSVGFileException("fill attribute value must be a 6 digit hexadecimal number prefixed with #");
		    }
		}
	    }

	    if (object.getNodeName().equals("path")) {
		if (!object.hasAttributes()) {
		    throw new InvalidSVGFileException("path object has no attributes");
		}

		NamedNodeMap attributes = object.getAttributes();

		//  path must have d attribute
		Node d = attributes.getNamedItem("d");
		if (d == null) {
		    throw new InvalidSVGFileException("missing path d attribute");
		}

		//  Does the path have a highlight attribute?
		Node highlight = attributes.getNamedItem("fill");
		if (highlight != null) {
		    if (highlight.getNodeValue().length() != 7) {
			throw new InvalidSVGFileException("fill attribute value must be a 6 digit hexadecimal number prefixed with #");
		    }
		    try {
			Integer.parseInt(highlight.getNodeValue().substring(1), 16);
		    } catch (NumberFormatException ex) {
			throw new InvalidSVGFileException("fill attribute value must be a 6 digit hexadecimal number prefixed with #");
		    }
		}
	    }
	}
    }

    public Vectors[] getObjects(double X, double Y, VectorsFacade VF) {
	List<Vectors> vectors = new LinkedList();
	for (int i = 0; i < Objects.size(); i++) {
	    Node object = Objects.get(i);
	    NamedNodeMap attributes = object.getAttributes();
	    Vectors vector = new Vectors();
	    if (object.getNodeName().equals("path")) {
		vector.setSvg(attributes.getNamedItem("d").getNodeValue());
	    }
	    if (object.getNodeName().equals("polygon") || object.getNodeName().equals("polyline")) {
		String[] uncleanCoordinates = attributes.getNamedItem("points").getNodeValue().trim().replaceAll("[\\r\\n]*", "").replaceAll("[\\t]*", "").split(" ");
		List<String> points = new LinkedList<String>();
		for (int j = 0; j < uncleanCoordinates.length; j++) {
		    if (!uncleanCoordinates[j].isEmpty()) {
			points.add(uncleanCoordinates[j]);
		    }
		}
		String[][] coordinates = new String[points.size()][2];
		for (int j = 0; j < points.size(); j++) {
		    coordinates[j] = points.get(j).split(",");
		}
		if (points.size() > 0) {
		    StringBuilder pathData = new StringBuilder("M ").append(coordinates[0][0]).append(" ").append(coordinates[0][1]);
		    for (int j = 1; j < points.size(); j++) {
			pathData.append(" L ").append(coordinates[j][0]).append(" ").append(coordinates[j][1]);
		    }
		    pathData.append("z");
		    vector.setSvg(pathData.toString());
		} else {
		    vector.setSvg("");
		}
	    }
	    if (object.getNodeName().equals("rect")) {
		double x = Double.parseDouble(attributes.getNamedItem("x").getNodeValue());
		double y = Double.parseDouble(attributes.getNamedItem("y").getNodeValue());
		double width = Double.parseDouble(attributes.getNamedItem("width").getNodeValue());
		double height = Double.parseDouble(attributes.getNamedItem("height").getNodeValue());
		StringBuilder pathData = new StringBuilder("M ").append(x).append(" ").append(y);
		pathData.append(" L ").append(x + width).append(" ").append(y).
			append(" L ").append(x + width).append(" ").append(y + height).
			append(" L ").append(x).append(" ").append(y + height).
			append("z");
		vector.setSvg(pathData.toString());
	    }

	    if (attributes.getNamedItem("fill") != null) {
		vector.setHighlight(attributes.getNamedItem("fill").getNodeValue().substring(1));
	    } else {
		vector.setHighlight("666666");
	    }
	    vector.setDescription("New Object");
	    vector.setHeight(0.0);
	    vector.setName("Unnamed");
	    vector.setTagList("untagged");
	    Random rand = new Random();
	    int UID = Math.abs(rand.nextInt());
	    while(VF.find(UID) != null) {
		UID = Math.abs(rand.nextInt());
	    }
	    vector.setUid(UID);
	    vector.setWidth(0.0);
	    vector.setX(X);
	    vector.setY(Y);
	    vector.setZ(0);
	    vectors.add(vector);
	}
	return vectors.toArray(new Vectors[0]);
    }
    
    List<List<String>> pathParser(String path) {
	List<List<String>> pathSegments= new LinkedList<List<String>>();
	String cleanPath = path.replaceAll("[\\\r\\\n]*", " ").replaceAll("[ ]+", " ");
	return pathSegments;
    }
}