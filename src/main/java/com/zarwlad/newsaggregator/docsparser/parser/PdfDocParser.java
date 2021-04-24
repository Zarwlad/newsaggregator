package com.zarwlad.newsaggregator.docsparser.parser;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
@Slf4j
public class PdfDocParser {

    /***
     *  A main method parser. Ignores "содержание". Read pdf page by page.
     * @param pdfReader - instance of PdfReader. Pdf reader is connected to data source.
     * @param startPhrase - start phrase, from which parsing starts.
     * @param stopPhrase - the phrase, which stops parsing data. Most common phrase - a title of next paragraph
     * @return pure parsed text
     */
    public String parsePdfPerPage(PdfReader pdfReader, String startPhrase, String stopPhrase){
        StringBuilder str = new StringBuilder();

        boolean parseStart = false;
        try {
            for (int i = 1; i < pdfReader.getNumberOfPages(); i++) {
                String pageData = getTextFromPage(pdfReader, i);
                log.debug("Successfully read text from file, page {}. Content: {}", i, pageData);

                String purifiedContent = pageData.toLowerCase().strip();
                if (!parseStart
                        && purifiedContent.contains(startPhrase)
                        && !purifiedContent.contains("содержание")){
                    parseStart = true;
                }

                if (purifiedContent.contains(stopPhrase.toLowerCase().strip())
                        && !purifiedContent.contains("содержание"))
                    break;

                if (parseStart){
                    str.append(pageData);
                }
            }
        } finally {
            pdfReader.close();
        }
        return str.toString();
    }

    public String getTextFromPage(PdfReader pdfReader, int page){
        try {
            return PdfTextExtractor.getTextFromPage(pdfReader, page, new SimpleTextExtractionStrategy());
        } catch (IOException e) {
            log.error("Usuccessfull attempt to read text. Calling non-existing page? Page: {}, Exception: {}", page, e.getMessage());
            return "";
        }

    }

    public byte[] readBytesFromFile(MultipartFile file){
        try {
            return file.getBytes();
        } catch (IOException e) {
            log.error(e.toString());
            return new byte[0];
        }
    }

    public byte[] readBytesFromFile(URL url){
        try (InputStream in = url.openStream()){
            Path tempFile = Files.createTempFile(
                    "temp",
                    ".tmp");
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
            byte[] bytes = Files.readAllBytes(tempFile);

            tempFile.toFile().delete();
            return bytes;
        } catch (IOException e) {
            log.error(e.toString());
            return new byte[0];
        }
    }

    public PdfReader getPdfReaderInstance(byte[] bytes){
        try {
            return new PdfReader(bytes);
        } catch (IOException e) {
            log.error(e.toString());
            return null;
        }
    }

    public String parseChangeLog(MultipartFile file, String stopPhrase){
        PdfReader pdfReader = getPdfReaderInstance(readBytesFromFile(file));
        return parsePdfPerPage(pdfReader, "история изменений", stopPhrase);
    }

    public String parseChangeLog(URL file, String stopPhrase){
        PdfReader pdfReader = getPdfReaderInstance(readBytesFromFile(file));
        return parsePdfPerPage(pdfReader, "история изменений", stopPhrase);
    }

}
