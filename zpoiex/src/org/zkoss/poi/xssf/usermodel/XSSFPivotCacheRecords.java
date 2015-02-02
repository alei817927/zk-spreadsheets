//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.zkoss.poi.xssf.usermodel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.openxmlformats.schemas.officeDocument.x2006.relationships.STRelationshipId;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotCacheRecords;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRecord;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPivotCacheRecords.Factory;
import org.zkoss.poi.POIXMLDocumentPart;
import org.zkoss.poi.POIXMLException;
import org.zkoss.poi.openxml4j.opc.PackagePart;
import org.zkoss.poi.openxml4j.opc.PackageRelationship;

public class XSSFPivotCacheRecords extends POIXMLDocumentPart {
    private CTPivotCacheRecords _pivotCacheRecords;

    XSSFPivotCacheRecords() {
        this.onDocumentCreate();
    }

    protected void onDocumentCreate() {
        this._pivotCacheRecords = Factory.newInstance();
    }

    XSSFPivotCacheRecords(PackagePart part, PackageRelationship rel) throws IOException {
        super(part, rel);
        this.onDocumentRead();
    }

    protected void onDocumentRead() throws IOException {
        try {
            this._pivotCacheRecords = org.openxmlformats.schemas.spreadsheetml.x2006.main.PivotCacheRecordsDocument.Factory.parse(this.getPackagePart().getInputStream()).getPivotCacheRecords();
        } catch (XmlException var2) {
            throw new POIXMLException(var2);
        }
    }

    protected void commit() throws IOException {
        XmlOptions xmlOptions = new XmlOptions(DEFAULT_XML_OPTIONS);
        xmlOptions.setSaveSyntheticDocumentElement(new QName(CTPivotCacheRecords.type.getName().getNamespaceURI(), "pivotCacheRecords"));
        HashMap map = new HashMap();
        map.put(STRelationshipId.type.getName().getNamespaceURI(), "r");
        xmlOptions.setSaveSuggestedPrefixes(map);
        PackagePart part = this.getPackagePart();
        this.clearMemoryPackagePart(part);
        OutputStream out = part.getOutputStream();
        this._pivotCacheRecords.save(out, xmlOptions);
        out.close();
    }

    CTRecord addNewRow() {
        return this._pivotCacheRecords.addNewR();
    }

    List<CTRecord> getRows() {
        return this._pivotCacheRecords.getRList();
    }

    public String toString() {
        return this._pivotCacheRecords.toString();
    }

    void setCount(int size) {
        this._pivotCacheRecords.setCount((long)size);
    }

    int getCount() {
        return (int)this._pivotCacheRecords.getCount();
    }
}
