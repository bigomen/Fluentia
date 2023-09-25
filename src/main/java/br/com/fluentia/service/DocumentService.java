package br.com.fluentia.service;

import br.com.fluentia.model.Avaliacao;
import br.com.fluentia.repository.AlunoRepository;
import br.com.fluentia.repository.AvaliacaoRepository;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class DocumentService extends AbstractService {

	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	@Autowired
	private AlunoRepository alunoRepository;

	public void gerarPdfAvaliacaoMensal(Avaliacao avaliacao) throws DocumentException, IOException {
		var aluno = alunoRepository.getById(avaliacao.getIdAluno());
		var tipoAvaliacao = "MENSAL";
		if(avaliacao.getIdAula()!=null) {
			tipoAvaliacao = "Aula: " + avaliacao.getIdAula();
		}
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, new FileOutputStream(avaliacao.getId() + ".pdf"));
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.COURIER_BOLD);
		font.setSize(18);
		font.setColor(Color.DARK_GRAY);
		Image im = Image.getInstance("logo.png");
		im.scalePercent(5);
		im.setAlignment(Image.ALIGN_CENTER);
		document.add(im);
		Paragraph p = new Paragraph("AVALIACAO INDIVIDUAL: " + aluno.getNome(), font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		
		p = new Paragraph(tipoAvaliacao, font);
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100f);
		table.setWidths(new float[] { 3.5f, 10f });
		table.setSpacingBefore(10);

		writeTableHeader(table);
		table.addCell("GRAMMAR");
		table.addCell(avaliacao.getPointGrammar().toString());
		table.addCell("PRONUNCIATION");
		table.addCell(avaliacao.getPointPronunciation().toString());
		table.addCell("LEXICAL RESOURCE");
		table.addCell(avaliacao.getPointLexicalResource().toString());
		table.addCell("COMPREHENSION");
		table.addCell(avaliacao.getPointComprehension().toString());
		table.addCell("DISCOURSE");
		table.addCell(avaliacao.getPointDiscourse().toString());
		table.addCell("GOODS");
		table.addCell(avaliacao.getWriteGood());
		table.addCell("NEEDS");
		table.addCell(avaliacao.getNeedsImprovement());
		table.addCell("SUGESTIONS");
		table.addCell(avaliacao.getSugestion());

		document.add(table);

		document.close();

	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.DARK_GRAY);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("CRITÉRIO", font));

		table.addCell(cell);

		cell.setPhrase(new Phrase("NOTA/AVALIAÇÃO", font));
		table.addCell(cell);
	}
}
