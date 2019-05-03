package blindnessmod.util;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLUtil {
	public static Document getXML(List<ItemInfo> list) throws ParserConfigurationException {
			Document document = createXMLDocument("RegItemList");
			Element root = document.getDocumentElement();

			for(ItemInfo i : list) {
				System.out.println(i.ID);
				// DOM要素の構築
				Element Item = document.createElement("Item");

				Element ID = document.createElement("ID");
				ID.appendChild(document.createTextNode(String.valueOf(i.ID)));
				Item.appendChild(ID);

				Element META = document.createElement("META");
				META.appendChild(document.createTextNode(String.valueOf(i.META)));
				Item.appendChild(META);

				Element COUNT = document.createElement("COUNT");
				COUNT.appendChild(document.createTextNode(String.valueOf(i.Count)));
				Item.appendChild(COUNT);

				Element DisCOUNT = document.createElement("DisCOUNT");
				DisCOUNT.appendChild(document.createTextNode(String.valueOf(i.DisCount)));
				Item.appendChild(DisCOUNT);

				Element HASTAG = document.createElement("HASTAG");
				HASTAG.appendChild(document.createTextNode(String.valueOf(i.HasNBT)));
				Item.appendChild(HASTAG);

				if(i.HasNBT) {
					Element NBT = document.createElement("NBT");
					NBT.appendChild(document.createTextNode(i.NBT.toString()));
					Item.appendChild(NBT);
				}

				root.appendChild(Item);
			}
		return document;
	}

	private static Document createXMLDocument(String root) throws ParserConfigurationException {
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();

	    DOMImplementation dom = builder.getDOMImplementation();
	    return dom.createDocument("", root, null);
	}

    public static boolean CreateXML(File file,List<ItemInfo> list) throws ParserConfigurationException {
    	Document document = getXML(list);
        // Transformerインスタンスの生成
        Transformer transformer = null;
        try {
             TransformerFactory transformerFactory = TransformerFactory
                       .newInstance();
             transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
             e.printStackTrace();
             return false;
        }

        // Transformerの設定
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","4");

        // XMLファイルの作成
        try {
             transformer.transform(new DOMSource(document), new StreamResult(
                       file));
        } catch (TransformerException e) {
             e.printStackTrace();
             return false;
        }

        return true;
   }


}
