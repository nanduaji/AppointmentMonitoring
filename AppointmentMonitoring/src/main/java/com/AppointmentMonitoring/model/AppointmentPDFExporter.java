package com.AppointmentMonitoring.model;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class AppointmentPDFExporter {
	private List<Appointments> listAppointments;

	public AppointmentPDFExporter(List<Appointments> listAppointments) {
		super();
		this.listAppointments = listAppointments;
	}
	
	private void writeTableHeader(PdfPTable table) {
		
		PdfPCell cell= new PdfPCell();
		cell.setBackgroundColor(Color.gray);
		cell.setPadding(5);
		
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setSize(13);
		font.setColor(Color.WHITE);
		
		
		cell.setPhrase(new Phrase("App.Num.", font ));
		table.addCell(cell);
		cell.setPhrase(new Phrase("From Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("From Time", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("To Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("To Time", font));
		table.addCell(cell);
		
//		cell.setPhrase(new Phrase("Meeting Completed", font));
//		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Meeting Description", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Agenda", font));
		table.addCell(cell);


	}
	private void writeTableData(PdfPTable table ) {
		DateFormat dateformatter= new SimpleDateFormat("DD:MM:YYYY");
		for(Appointments appointment : listAppointments) {
			
			table.addCell(String.valueOf(appointment.getAppointmentNumber()));
			table.addCell(String.valueOf(dateformatter.format(appointment.getFromDate())));
			table.addCell(String.valueOf(appointment.getFromTime()));
			table.addCell(String.valueOf(dateformatter.format(appointment.getToDate())));
			table.addCell(String.valueOf(appointment.getToTime()));
//			table.addCell(appointment.getMeetingCompleted());
			table.addCell(appointment.getMeetingDescription());
			table.addCell(appointment.getAgenda());
			
			
		}
		
	}
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
		font.setColor(Color.darkGray);
		font.setSize(18);
		
		Paragraph title = new Paragraph("List of all Appointments", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);
		
		PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100);
		table.setSpacingBefore(15);
		table.setWidths(new float[] {1.5f,2.5f,2.5f,2.5f,2.5f,3.5f,3.0f});
		
		
		writeTableHeader(table);
		writeTableData(table);
		
		document.add(table);
		document.close();
	}

	}

