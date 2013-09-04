/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.gen;

import com.iitk.cimam.CIMAM;
import com.iitk.cimam.data.Vectors;
import com.iitk.cimam.exception.InternalErrorException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chirag Sangani
 */
public class Generator {

    private int startX = 50;
    private int startY = 60;
    private Map<String, Map<String, String>> TAHTagList;
    private List<Vectors> VectorData;
    private boolean editMode = false;

    public Generator(Map<String, Map<String, String>> TAHTagList, List<Vectors> VectorData, boolean editMode) {
	this.TAHTagList = TAHTagList;
	this.VectorData = VectorData;
	this.editMode = editMode;
    }

    public String getOutput(int Width, int Height) throws InternalErrorException {
	return getBase(getContainer(getHTML(editMode), getJS(Width, Height), Width, Height));
    }

    private String getContainer(String HTML, String JS, int Width, int Height) throws InternalErrorException {
	try {
	    BufferedReader BR = new BufferedReader(new InputStreamReader(Generator.class.getResource("ContainerHead.html").openStream()));
	    StringBuilder head = new StringBuilder("");
	    String line = BR.readLine();
	    while (line != null) {
		head.append(line);
		line = BR.readLine();
	    }
	    BR = new BufferedReader(new InputStreamReader(Generator.class.getResource("ContainerTail.html").openStream()));
	    StringBuilder tail = new StringBuilder("");
	    line = BR.readLine();
	    while (line != null) {
		tail.append(line);
		line = BR.readLine();
	    }
	    return head.toString().replace("#CANVASWIDTH#", new Integer(Width).toString()).replace("#CANVASHEIGHT#", new Integer(Height).toString()) + HTML + JS + tail.toString();
	} catch (Exception ex) {
	    throw new InternalErrorException("EJB.GEN.0001");
	}
    }

    private String getHTML(boolean editMode) throws InternalErrorException {
	try {
	    BufferedReader BR = new BufferedReader(new InputStreamReader(Generator.class.getResource("HTML.html").openStream()));
	    StringBuilder HTML = new StringBuilder("");
	    String line = BR.readLine();
	    while (line != null) {
		HTML.append(line);
		line = BR.readLine();
	    }
	    String rawHTML = HTML.toString();
	    if (editMode) {
		return rawHTML.replace("#EDITDISPLAY#", "");
	    } else {
		return rawHTML.replace("#EDITDISPLAY#", "display: none;");
	    }
	} catch (Exception ex) {
	    throw new InternalErrorException("EJB.GEN.0002");
	}
    }

    private String getJS(int Width, int Height) throws InternalErrorException {
	try {
	    BufferedReader BR = new BufferedReader(new InputStreamReader(Generator.class.getResource("JS.html").openStream()));
	    StringBuilder JS = new StringBuilder("");
	    String line = BR.readLine();
	    while (line != null) {
		JS.append(line);
		line = BR.readLine();
	    }
	    String output = JS.toString();

	    //	Inject canvas dimensions

	    output = output.replace("#CANVASWIDTH#", new Integer(Width).toString());
	    output = output.replace("#CANVASHEIGHT#", new Integer(Height).toString());

	    //	Inject starting point

	    output = output.replace("#STARTX#", new Integer(startX).toString());
	    output = output.replace("#STARTY#", new Integer(startY).toString());

	    // Initialize floor arrays

	    int maxFloor = 0;
	    for (Vectors vector : VectorData) {
		if (vector.getZ() > maxFloor) {
		    maxFloor = vector.getZ();
		}
	    }
	    StringBuilder ArrayInit = new StringBuilder("");
	    for (int i = 0; i < maxFloor + 1; i++) {
		ArrayInit.append("CIMAMMapVectors[").append(i).append("] = new Array();").append("CIMAMMapNames[").append(i).append("] = new Array();").append("CIMAMMapDescriptions[").append(i).append("] = new Array();").append("CIMAMMapUIDs[").append(i).append("] = new Array();");
	    }
	    output = output.replace("#ARRAYINIT#", ArrayInit);

	    //	Inject Vectors

	    StringBuilder VectorInit = new StringBuilder("");

	    int[] floorCount = new int[maxFloor + 1];
	    for (Vectors vector : VectorData) {
		VectorInit.append("CIMAMMapVectors[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("] = CIMAMMapCanvas.path(\"").append(vector.getSvg()).append("\").attr(CIMAMMapVectorAttributes)");
		String highlightColour = vector.getHighlight();
		String description = vector.getDescription();
		int UID = vector.getUid();
		String[] vectorTags = vector.getTagList().split(",");
		for (int i = vectorTags.length - 1; i > -1; i--) {
		    if (TAHTagList.get(vectorTags[i]) != null) {
			if (TAHTagList.get(vectorTags[i]).get("highlight") != null) {
			    highlightColour = TAHTagList.get(vectorTags[i]).get("highlight");
			}
			if (TAHTagList.get(vectorTags[i]).get("description") != null) {
			    description = TAHTagList.get(vectorTags[i]).get("description");
			}
		    }
		}
		VectorInit.append(".attr({fill:\"#").append(highlightColour).append("\"});");

		VectorInit.append("CIMAMMapVectors[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("].translate(").append(vector.getX()).append(",").append(vector.getY()).append(");");
		VectorInit.append("CIMAMMapNames[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("] = CIMAMMapCanvas.text(CIMAMMapVectors[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("].getBBox().width/2 + CIMAMMapVectors[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("].getBBox().x,CIMAMMapVectors[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("].getBBox().height/2 + CIMAMMapVectors[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("].getBBox().y,\"").append(vector.getName()).append("\").attr(CIMAMMapTextAttributes);");
		VectorInit.append("CIMAMMapNames[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("];");
		VectorInit.append("CIMAMMapDescriptions[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("]=\"").append(description).append("\";");
		VectorInit.append("CIMAMMapUIDs[").append(vector.getZ()).append("][").append(floorCount[vector.getZ()]).append("]=").append(UID).append(";");
		floorCount[vector.getZ()]++;
	    }
	    output = output.replace("#VECTORINIT#", VectorInit);

	    return output;
	} catch (Exception ex) {
	    throw new InternalErrorException("EJB.GEN.0003");
	}
    }

    private String getBase(String input) {
	return input.replace("#BASE#", CIMAM.getBase());
    }
}
