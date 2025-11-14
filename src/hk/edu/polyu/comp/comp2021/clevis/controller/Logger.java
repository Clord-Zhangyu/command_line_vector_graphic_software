package hk.edu.polyu.comp.comp2021.clevis.controller;

import hk.edu.polyu.comp.comp2021.clevis.model.Data;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The Logger class
 */
public class Logger {

    private FileWriter HtmlLog = null;
    private boolean WriteHtml;
    private FileWriter TxtLog = null;
    private boolean WriteTxt;

    /**
     * Contructer for logger
     *
     * @param html the file writer for html
     * @param txt  the file writer for txt
     * @throws IOException thrown when the file is not accessable
     */
    public Logger(FileWriter html, FileWriter txt) throws IOException {
        if (html != null) {
            WriteHtml = true;
            HtmlLog = html;
            try {
                HtmlLog.write("""
                        <style>
                            table {
                                border-collapse: collapse;
                                border: 1px solid black;
                            }
                            th, td {
                                border: 1px solid black;
                            }
                            td:first-child {
                                width: 80px;
                            }
                        </style>
                        <table>
                        """);
            } catch (Exception e) {
                System.out.println("Writing html log failed");
                WriteHtml = false;
            }
        }
        if (txt != null) WriteTxt = true;
        TxtLog = txt;
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (WriteHtml) {
                try {
                    HtmlLog.write("</table>");
                    HtmlLog.flush();
                } catch (IOException e) {
                    System.out.println("Writing html log failed");
                }
            }
        }));
    }

    /**
     * Log the content
     *
     * @param content the content in string format
     */
    public void Log(String content) {
        if (content == null) return;
        if (WriteHtml) {
            try {
                HtmlLog.write("\t<tr>\n\t\t<td>\n\t\t\t" + Data.GetCommandCount() + "\n\t\t</td>\n\t\t<td>\n\t\t\t" + content + "\n\t\t</td>\n\t</tr>\n");
                HtmlLog.flush();
            } catch (IOException e) {
                System.out.println("Writing html log failed");
                WriteHtml = false;
            }
        }
        if (WriteTxt)
            try {
                TxtLog.write(content + "\n");
                TxtLog.flush();
            } catch (IOException e) {
                System.out.println("Writing txt log failed");
                WriteTxt = false;
            }
    }
}
