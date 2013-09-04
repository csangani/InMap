/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iitk.cimam.xml;

import com.iitk.cimam.xml.exception.BadRequestException;
import com.iitk.cimam.xml.exception.XMLParserException;
import com.iitk.cimam.exception.InternalErrorException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Chirag Sangani
 */
public class RequestParser {

    String request;
    Document doc;
    Node requestNode;
    Node formatNode;
    Node queryNode;
    Node interfaceNode;

    public RequestParser(String request) throws BadRequestException, XMLParserException {
	try {
	    this.request = request;
	    doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(request)));
	    doc.normalizeDocument();
	} catch (SAXException ex) {
	    throw new BadRequestException(ex.getMessage());
	} catch (IOException ex) {
	    throw new XMLParserException(ex.getMessage());
	} catch (ParserConfigurationException ex) {
	    throw new XMLParserException(ex.getMessage());
	}
    }

    public void validate() throws BadRequestException {
	if (doc.getElementsByTagName("request") == null) {
	    throw new BadRequestException("request field not found");
	}
	if (doc.getElementsByTagName("request").getLength() > 1) {
	    throw new BadRequestException("too many request fields");
	}

	requestNode = doc.getElementsByTagName("request").item(0);
	boolean formatFlag = false, queryFlag = false, interfaceFlag = false;
	for (int i = 0; i < requestNode.getChildNodes().getLength(); i++) {
	    Node childNode = requestNode.getChildNodes().item(i);
	    if (childNode.getNodeName().equals("format")) {
		if (formatFlag == true) {
		    throw new BadRequestException("multiple format fields");
		}
		formatFlag = true;
		formatNode = childNode;
	    }
	    if (childNode.getNodeName().equals("query")) {
		if (queryFlag == true) {
		    throw new BadRequestException("multiple query fields");
		}
		queryFlag = true;
		queryNode = childNode;
	    }
	    if (childNode.getNodeName().equals("interface")) {
		if (interfaceFlag == true) {
		    throw new BadRequestException("multiple interface fields");
		}
		interfaceFlag = true;
		interfaceNode = childNode;
	    }
	}
	if (!formatFlag || !queryFlag || !interfaceFlag) {
	    if (!formatFlag) {
		throw new BadRequestException("missing format field");
	    }
	    if (!queryFlag) {
		throw new BadRequestException("missing query field");
	    }
	    throw new BadRequestException("missing interface field");
	}
	if (getRequestType() == 1) {
	    if (queryNode.getChildNodes().getLength() != 1) {
		throw new BadRequestException("query has too many or no elements");
	    }
	    if (!queryNode.getFirstChild().getNodeName().equals("tagList")) {
		throw new BadRequestException("first element of query must be tagList");
	    }
	    NodeList XMLTagList = queryNode.getFirstChild().getChildNodes();
	    for (int i = 0; i < XMLTagList.getLength(); i++) {
		Node tag = XMLTagList.item(i);
		if (!tag.getNodeName().equals("tag")) {
		    throw new BadRequestException("tagList has element other than tag");
		}
		if (tag.getChildNodes().getLength() == 0) {
		    throw new BadRequestException("empty tag");
		}
		boolean nameFlag = false;
		for (int j = 0; j < tag.getChildNodes().getLength(); j++) {
		    Node attrNode = tag.getChildNodes().item(j);
		    if (!attrNode.getNodeName().equals("highlight")
			    && !attrNode.getNodeName().equals("name")
			    && !attrNode.getNodeName().equals("description")) {
			throw new BadRequestException("unrecognized tag attribute");
		    }
		    if (attrNode.getNodeName().equals("name")) {
			if (nameFlag) {
			    throw new BadRequestException("multiple tag names");
			}
			nameFlag = true;
		    }
		    if (attrNode.getFirstChild() == null) {
			throw new BadRequestException("empty tag attribute");
		    }
		    if (attrNode.getNodeName().equals("name")) {
			if (!attrNode.getFirstChild().getNodeValue().matches("[a-zA-Z0-9]*")) {
			    throw new BadRequestException("tag name must be alphanumeric");
			}
		    }
		    if (attrNode.getNodeName().equals("highlight")) {
			try {
			    if (attrNode.getFirstChild().getNodeValue().length() != 6) {
				throw new BadRequestException("highlight tag must be a 6-digit hexadecimal number");
			    }
			    Integer.parseInt(attrNode.getFirstChild().getNodeValue(), 16);
			} catch (NumberFormatException ex) {
			    throw new BadRequestException("highlight tag must be a 6-digit hexadecimal number");
			}
		    }
		}
		if (!nameFlag) {
		    throw new BadRequestException("missing tag name");
		}
	    }
	}
	boolean interfaceSizeFlag = false;
	for (int i = 0; i < interfaceNode.getChildNodes().getLength(); i++) {
	    Node childNode = interfaceNode.getChildNodes().item(i);
	    if (childNode.getNodeName().equals("size")) {
		if (interfaceSizeFlag) {
		    throw new BadRequestException("multiple interface size tags");
		}
		interfaceSizeFlag = true;
	    }
	}
	if (!interfaceSizeFlag) {
	    throw new BadRequestException("missing interface size tag");
	}
	boolean interfaceEditFlag = false;
	for (int i = 0; i < interfaceNode.getChildNodes().getLength(); i++) {
	    Node childNode = interfaceNode.getChildNodes().item(i);
	    if (childNode.getNodeName().equals("edit")) {
		if (interfaceEditFlag) {
		    throw new BadRequestException("multiple interface edit tags");
		}
		interfaceEditFlag = true;
	    }
	}
	for (int i = 0; i < interfaceNode.getChildNodes().getLength(); i++) {
	    Node childNode = interfaceNode.getChildNodes().item(i);
	    if (childNode.getNodeName().equals("size")) {
		boolean interfaceSizeWidthFlag = false, interfaceSizeHeightFlag = false;
		for (int j = 0; j < childNode.getChildNodes().getLength(); j++) {
		    Node grandchildNode = childNode.getChildNodes().item(j);
		    if (grandchildNode.getNodeName().equals("width")) {
			if (interfaceSizeWidthFlag) {
			    throw new BadRequestException("multiple interface size width tags");
			}
			interfaceSizeWidthFlag = true;
			try {
			    Integer.parseInt(grandchildNode.getFirstChild().getNodeValue());
			} catch (NumberFormatException ex) {
			    throw new BadRequestException("interface size width field must be an integer");
			}
		    }
		    if (grandchildNode.getNodeName().equals("height")) {
			if (interfaceSizeHeightFlag) {
			    throw new BadRequestException("multiple interface size height tags");
			}
			interfaceSizeHeightFlag = true;
			try {
			    Integer.parseInt(grandchildNode.getFirstChild().getNodeValue());
			} catch (NumberFormatException ex) {
			    throw new BadRequestException("interface size height field must be an integer");
			}
		    }
		}
		if (!interfaceSizeWidthFlag) {
		    throw new BadRequestException("missing interface size width tag");
		}
		if (!interfaceSizeHeightFlag) {
		    throw new BadRequestException("missing interface size height tag");
		}
	    } else if (childNode.getNodeName().equals("edit")) {
		if (!childNode.getFirstChild().getNodeValue().equals("true") && !childNode.getFirstChild().getNodeValue().equals("false")) {
		    throw new BadRequestException("invalid edit mode switch");
		}
	    } else {
		throw new BadRequestException("unknown interface parameter");
	    }
	}
    }

    public int getRequestType() throws BadRequestException {
	String format = formatNode.getChildNodes().item(0).getNodeValue();
	if (format.equals("TagsAndHighlighting")) {
	    return 1;
	} else {
	    throw new BadRequestException("unable to recognize format");
	}
    }

    public Map<String, Map<String, String>> TAHGetTagList() throws BadRequestException, InternalErrorException {
	if (getRequestType() != 1) {
	    throw new InternalErrorException("EJB.XML.0001");
	}
	Map<String, Map<String, String>> TagList = new HashMap<String, Map<String, String>>();
	NodeList XMLTagList = queryNode.getFirstChild().getChildNodes();
	for (int i = 0; i < XMLTagList.getLength(); i++) {
	    Node tag = XMLTagList.item(i);
	    Map<String, String> AttrMap = new HashMap<String, String>();
	    String name = "";
	    for (int j = 0; j < tag.getChildNodes().getLength(); j++) {
		Node attrNode = tag.getChildNodes().item(j);
		if (attrNode.getNodeName().equals("name")) {
		    name = attrNode.getFirstChild().getNodeValue();
		} else {
		    AttrMap.put(attrNode.getNodeName(), attrNode.getFirstChild().getNodeValue());
		}
	    }
	    TagList.put(name, AttrMap);
	}
	return TagList;
    }

    public int getInterfaceSizeWidth() throws InternalErrorException {
	try {
	    Node sizeNode = null;
	    for (int i = 0; i < interfaceNode.getChildNodes().getLength(); i++) {
		if (interfaceNode.getChildNodes().item(i).getNodeName().equals("size")) {
		    sizeNode = interfaceNode.getChildNodes().item(i);
		    break;
		}
	    }
	    for (int i = 0; i < sizeNode.getChildNodes().getLength(); i++) {
		if (sizeNode.getChildNodes().item(i).getNodeName().equals("width")) {
		    return Integer.parseInt(sizeNode.getChildNodes().item(i).getFirstChild().getNodeValue());
		}
	    }
	    throw new InternalErrorException("EJB.XML.0003");
	} catch (NullPointerException ex) {
	    throw new InternalErrorException("EJB.XML.0002");
	}
    }

    public int getInterfaceSizeHeight() throws InternalErrorException {
	try {
	    Node sizeNode = null;
	    for (int i = 0; i < interfaceNode.getChildNodes().getLength(); i++) {
		if (interfaceNode.getChildNodes().item(i).getNodeName().equals("size")) {
		    sizeNode = interfaceNode.getChildNodes().item(i);
		    break;
		}
	    }
	    for (int i = 0; i < sizeNode.getChildNodes().getLength(); i++) {
		if (sizeNode.getChildNodes().item(i).getNodeName().equals("height")) {
		    return Integer.parseInt(sizeNode.getChildNodes().item(i).getFirstChild().getNodeValue());
		}
	    }
	    throw new InternalErrorException("EJB.XML.0005");
	} catch (NullPointerException ex) {
	    throw new InternalErrorException("EJB.XML.0004");
	}
    }

    public void sanitize() {
	//  Sanitize description fields
	NodeList descriptions = doc.getElementsByTagName("description");
	for (int i = 0; i < descriptions.getLength(); i++) {
	    Node desc = descriptions.item(i);
	    desc.getFirstChild().setNodeValue(desc.getFirstChild().getNodeValue().replace("</script>", "\\074/script\\076"));
	    desc.getFirstChild().setNodeValue(desc.getFirstChild().getNodeValue().replace("\"", "\\\""));
	}
    }

    public boolean getEditMode() {
	NodeList descriptors = interfaceNode.getChildNodes();
	for (int i = 0; i < descriptors.getLength(); i++) {
	    Node descriptor = descriptors.item(i);
	    if (descriptor.getNodeName().equals("edit") && descriptor.getFirstChild().getNodeValue().equals("true")) {
		return true;
	    }
	}
	return false;
    }
}
