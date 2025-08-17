package com.cafe;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator {
    // Store PDFs in the project root folder
    private static final String BILLS_DIR = "./bills/";
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

    public static String generateBill(int transactionId, String billText, List<TransactionItem> items) {
        try {
            // Create bills directory if it doesn't exist
            File billsDir = new File(BILLS_DIR);
            if (!billsDir.exists()) {
                if (!billsDir.mkdirs()) {
                    throw new Exception("Failed to create bills directory");
                }
            }

            // Create filename with timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = String.format("bill_%d_%s.pdf", transactionId, timestamp);
            String filepath = BILLS_DIR + filename;

            // Create document
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filepath));
            document.open();

            // Add title
            Paragraph title = new Paragraph("Café Billing System", TITLE_FONT);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Add transaction details
            Paragraph details = new Paragraph();
            details.setFont(NORMAL_FONT);
            details.add("Transaction ID: " + transactionId + "\n");
            details.add("Date: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n\n");
            document.add(details);

            // Create table for items
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 1, 1, 1});

            // Add table headers
            String[] headers = {"Item", "Quantity", "Price", "Total"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, HEADER_FONT));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(5);
                table.addCell(cell);
            }

            // Add items
            double total = 0;
            for (TransactionItem item : items) {
                table.addCell(new Phrase(item.getName(), NORMAL_FONT));
                table.addCell(new Phrase(String.valueOf(item.getQuantity()), NORMAL_FONT));
                table.addCell(new Phrase(String.format("%.2f", item.getUnitPrice()), NORMAL_FONT));
                table.addCell(new Phrase(String.format("%.2f", item.getTotal()), NORMAL_FONT));
                total += item.getTotal();
            }

            // Add total row
            PdfPCell totalCell = new PdfPCell(new Phrase("Total", HEADER_FONT));
            totalCell.setColspan(3);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(totalCell);
            table.addCell(new Phrase(String.format("%.2f", total), HEADER_FONT));

            document.add(table);

            // Add footer
            Paragraph footer = new Paragraph("\nThank you for your business!", NORMAL_FONT);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return filepath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
} 