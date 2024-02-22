package ar.edu.utn.frba.dds.models.archivoCSV;

import io.javalin.http.UploadedFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV implements AdapterCSVFileReader {
  @Override
  public List<String> leerArchivoCSV(UploadedFile archivoCSV) throws IOException {
    BufferedInputStream buff = new BufferedInputStream(archivoCSV.content());
    BufferedReader bfr = new BufferedReader(new InputStreamReader(buff,StandardCharsets.ISO_8859_1));

    List<String> lecturas = new ArrayList<>();
    String line;
    line = bfr.readLine();
    while ((line = bfr.readLine()) != null) {

      lecturas.add(line);

    }
    return lecturas;
  }
}
