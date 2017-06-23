package com.ccd.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringEscapeUtils;

public class XmlUtil {

    /**
     * return result of transform method
     * 
     * @param xmlFile
     * @param xslFile
     * @param isXmlString
     * @param isXslString
     * @return
     * @throws TransformerConfigurationException
     */
    public String transformXmlXslFiles(String xmlFile, String xslFile, boolean isXmlString, boolean isXslString) {
	try {
	    if (xmlFile == null || xmlFile == "null") {
		return null;
	    }
	    if (xslFile == null || xslFile == "null") {
		return null;
	    }
	    InputStream xmlFileInputStream = null;
	    InputStream xsltFileInputStream = null;
	    if (isXmlString) {
		xmlFileInputStream = new ByteArrayInputStream(xmlFile.getBytes());
	    } else {
		xmlFileInputStream = new FileInputStream(xmlFile);
	    }
	    if (isXslString) {
		xsltFileInputStream = new ByteArrayInputStream(xslFile.getBytes());
	    } else {
		xsltFileInputStream = new FileInputStream(xslFile);
	    }

	    return transform(xmlFileInputStream, xsltFileInputStream);
	} catch (Exception exception) {
	    // System.out.println(exception.getMessage());
	}
	return null;

    }

    /**
     * Transform the XML and XSLT
     * 
     * @param xmlFile
     * @param xsltFile
     * @return
     * @throws IOException
     */
    public String transform(InputStream xmlFile, InputStream xsltFile) throws IOException {
	try {
	    Source xmlSource = new StreamSource(xmlFile);
	    Source xsltSource = new StreamSource(xsltFile);
	    TransformerFactory transFact = TransformerFactory.newInstance();
	    Transformer trans = transFact.newTransformer(xsltSource);
	    StreamResult streamResult = new StreamResult(new ByteArrayOutputStream());
	    try {
		trans.transform(xmlSource, streamResult);
	    } catch (TransformerException e) {
		e.printStackTrace();
	    }
	    if (streamResult != null && streamResult.getOutputStream() != null) {
		return streamResult.getOutputStream().toString();
	    }
	} catch (Exception exception) {
	    return null;
	} finally {
	    xmlFile.close();
	    xsltFile.close();
	}
	return null;
    }

    public String getFileNameByMrn(String dir, String mrn) {
	try {
	    if (mrn == null || mrn == "null" || mrn == "undefined") {
		return null;
	    }
	    String tempName = null;
	    File d = new File(dir);
	    if (d.isDirectory()) {
		String[] files = d.list();
		for (String s : files) {
		    tempName = s.substring(0, s.lastIndexOf('.'));// remove
								  // extension
		    if (Arrays.asList(tempName.split("_")).contains(mrn)) {
			return dir + File.separator + s;
		    }
		}
	    }
	} catch (Exception exception) {
	    System.out.println(exception.getMessage());
	}
	return null;
    }

    public String getFileContentByMrn(String dir, String mrn) {
	try {
	    if (mrn == null || mrn == "null" || mrn == "undefined") {
		return null;
	    }
	    String tempName = null;
	    File d = new File(dir);
	    if (d.isDirectory()) {
		String[] files = d.list();
		for (String s : files) {
		    tempName = s.substring(0, s.lastIndexOf('.'));// remove
								  // extension
		    if (Arrays.asList(tempName.split("_")).contains(mrn)) {
			File file = new File(dir + File.separator + s);
			String content = "";
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while ((line = reader.readLine()) != null) {
			    content = content + line;
			}
			reader.close();
			return content;
		    }
		}
	    }
	} catch (Exception exception) {
	    System.out.println(exception.getMessage());
	}
	return null;
    }

    public static void main(String[] a) {
	try {
	    XmlUtil ic = new XmlUtil();
	    System.out.println(ic.getFileNameByMrn("D://XML Documents/MHG/archiveCCDs/", "37955"));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public String escapeStringTag(String string) {
	String body = StringEscapeUtils.unescapeXml(string);
	return body;
    }

    public String unescapeXml(String string) {
	String body = StringEscapeUtils.unescapeXml(string);
	return body;
    }

    public String escapeXml(String string) {
	String body = StringEscapeUtils.escapeXml(string);
	return body;
    }

    public String unescapeHtml4(String string) {
	String body = StringEscapeUtils.unescapeHtml4(string);
	return body;
    }

    public String escapeHtml4(String string) {
	String body = StringEscapeUtils.escapeHtml4(string);
	return body;
    }

    public static Class<StringEscapeUtils> getStringEscapeUtils() {
	return StringEscapeUtils.class;
    }
}
