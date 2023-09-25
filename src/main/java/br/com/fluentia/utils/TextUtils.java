package br.com.fluentia.utils;

import java.io.*;

public class TextUtils {

	public void generateLineReport(String texto, Boolean append, String path) {
		FileWriter arquivo;

		try {
			arquivo = new FileWriter(new File(path), append);
			arquivo.write(texto);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String readFromInputStream() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("mails/reset-mail");
		return readFromInputStream(inputStream);
	}
	public String readProfessor() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("mails/reset-mail-professor");
		return readFromInputStream(inputStream);
	}


	private String readFromInputStream(InputStream inputStream) throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}

}
