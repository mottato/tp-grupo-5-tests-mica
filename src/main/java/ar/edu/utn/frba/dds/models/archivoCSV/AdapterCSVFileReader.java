package ar.edu.utn.frba.dds.models.archivoCSV;

import io.javalin.http.UploadedFile;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.util.List;

public interface AdapterCSVFileReader {
  public List<String> leerArchivoCSV(UploadedFile archivoCSV) throws IOException;
}
