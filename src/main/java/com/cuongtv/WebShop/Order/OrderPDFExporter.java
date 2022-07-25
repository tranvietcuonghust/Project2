package com.cuongtv.WebShop.Order;
import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


public class OrderPDFExporter {
    private List<OrderedItem> orderedItemList;
    private Order order;
    private Double Total;

    public OrderPDFExporter(List<OrderedItem> orderedItemList, Order order) {
        this.orderedItemList = orderedItemList;
        this.order = order;
        this.Total =0.0;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Product ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("P.Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Price", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Payment Amount", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {

        for (OrderedItem orderedItem : orderedItemList) {
            table.addCell("     "+String.valueOf(orderedItem.getOrderedItemID()));
            //table.addCell(String.valueOf(orderedItem.getOrder().getId()));
            table.addCell(orderedItem.getProduct().getName());
            table.addCell("      "+String.valueOf(orderedItem.getQuantity()));
            table.addCell("    "+String.valueOf(orderedItem.getProduct().getPrice()) +"$");
            table.addCell("    "+String.valueOf(orderedItem.getPrice())+"$");
            Total+=orderedItem.getPrice();
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A5);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("BILL", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        Font font1 = FontFactory.getFont(FontFactory.COURIER);
        font1.setColor(Color.BLACK);
        Paragraph p1 = new Paragraph("Order # " + String.valueOf(order.getId()), font1);
        p1.add("\nCustomer Name: "+ String.valueOf(order.getName()));
        p1.add("\nAddress: "+ order.getAddress());
        p1.add("\nPhone: "+ order.getPhone());
        p1.add("\nCreate Date: "+ String.valueOf(order.getCreateDate()));


        document.add(p);

        document.add(p1);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {0.9f, 1.5f, 1.0f, 1.0f, 1.0f});
        table.setSpacingBefore(20);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        Font font2 = FontFactory.getFont(FontFactory.TIMES_ITALIC);
        font2.setSize(16);
        Paragraph p2 = new Paragraph("Total Amount: " + String.valueOf(Total) +"$  ", font2);
        p2.setAlignment(Paragraph.ALIGN_RIGHT);
        document.add(p2);

        document.close();

    }
}
