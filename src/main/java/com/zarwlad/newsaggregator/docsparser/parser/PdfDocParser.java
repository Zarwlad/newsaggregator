package com.zarwlad.newsaggregator.docsparser.parser;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.fit.pdfdom.PDFDomTree;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Pattern;

@Component
@Slf4j
public class PdfDocParser {
    @SneakyThrows
    public void generateTextFromPdf(MultipartFile file){
        byte[] bytes = readBytesFromFile(file);
        PDDocument pdDocument = PDDocument.load(bytes);
        Writer output = new PrintWriter("D:\\Download\\newPdf.html", StandardCharsets.UTF_8);
        new PDFDomTree().writeText(pdDocument, output);

        output.close();
    }

    public String parseChangeLog(MultipartFile file, String stopPhrase){
        PdfReader pdfReader = getPdfReaderInstance(readBytesFromFile(file));

        StringBuilder str = new StringBuilder();

        boolean changeLogStart = false;
        try {
            for (int i = 1; i < pdfReader.getNumberOfPages(); i++) {
                String pageData = getTextFromPage(pdfReader, i);
                log.debug("Successfully read text from file {}, page {}. Content: {}", file.getName(), i, pageData);

                String purifiedContent = pageData.toLowerCase().strip();
                if (!changeLogStart
                        && purifiedContent.contains("история изменений")
                        && !purifiedContent.contains("содержание")){
                    changeLogStart = true;
                }

                if (purifiedContent.contains(stopPhrase.toLowerCase().strip())
                && !purifiedContent.contains("содержание"))
                    break;

                if (changeLogStart){
                    str.append(pageData);
                }
            }
        } finally {
            pdfReader.close();
        }
        return str.toString();
    }

    private String getTextFromPage(PdfReader pdfReader, int page){
        try {
            return PdfTextExtractor.getTextFromPage(pdfReader, page, new SimpleTextExtractionStrategy());
        } catch (IOException e) {
            log.error("Попытка чтения текста не удалась. Обращение к несуществуещей странице? Страница: {}, Exception: {}", page, e.getMessage());
            return "";
        }

    }

    private byte[] readBytesFromFile(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            log.error(e.toString());
            return new byte[0];
        }
    }

    private PdfReader getPdfReaderInstance(byte[] bytes){
        try {
            return new PdfReader(bytes);
        } catch (IOException e) {
            log.error(e.toString());
            return null;
        }
    }
}
