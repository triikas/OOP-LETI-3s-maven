import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

class Th extends Thread {


    private int type;
    private JFrame app;
    private DefaultTableModel dtm;
    private JTable list;
    String [] columns = {"ФИО заказчика", "Автомобиль", "Неисправность", "Дата приёма", "Дата выполнения", "Работник", "Специализация работника"};
    String [] html = {
            "<!DOCTYPE html>\n<html lang=\"ru\">\n<head>\n<meta charset=\"UTF-8\">\n<title>База автосервиса</title>\n<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT\" crossorigin=\"anonymous\">\n\n</head>\n<body>\n<section class=\"d-flex justify-content-center\"><table class=\"table table-striped-columns\">\n",
            "</table></section>\n<script src=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js\" integrity=\"sha384-u1OknCvxWvY5kfmNBILK2hRnQC3Pr17a+RTT6rIHI7NnikvbZlHgTPOOmMi466C8\" crossorigin=\"anonymous\"></script></body>\n</html>"
    };

    public Th(int i, JFrame ap, DefaultTableModel dt, JTable lst) {
        type = i;
        app = ap;
        dtm = dt;
        list = lst;
    }

    public void run() {

        if (type == 1) {
            synchronized (app) {

                FileDialog fd = new FileDialog(app, "Открыть", FileDialog.LOAD);
                fd.setDirectory("A:\\leti-projects\\OOP-LETI-3s-maven\\");
//                fd.setFile("*.txt");
                fd.setVisible(true);
                String filename = fd.getFile();
//                System.out.println(filename);
//                System.out.println(Arrays.toString("data.txt".split("\\.")));
                if (filename == null) return;
                if (Objects.equals(filename.split("\\.")[1], "txt") || Objects.equals(filename.split("\\.")[1], "csv")) {
                    try {
                        File file = new File(fd.getDirectory() + filename);
                        FileReader fr = new FileReader(file);
                        BufferedReader reader = new BufferedReader(fr);
                        Vector<String> line = new Vector<String>(Arrays.asList(reader.readLine().split(";")));
                        dtm.setRowCount(0);
                        while (line != null) {
                            dtm.addRow(line);
                            try {
                                line = new Vector<String>(Arrays.asList(reader.readLine().split(";")));
                            } catch (NullPointerException ex) {
                                line = null;
                            }

                        }


                    } catch (FileNotFoundException ex) {

                        JOptionPane.showMessageDialog(app, "Файл не найден");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(app, "Возникли проблемы с файлом");
                    }
                } else if (Objects.equals(filename.split("\\.")[1], "xml")) {
                    try {
                        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
                        DocumentBuilder b = f.newDocumentBuilder();
                        Document doc = b.parse(new File(fd.getDirectory() + filename));
                        doc.getDocumentElement().normalize();
                        NodeList data_list = doc.getElementsByTagName("order");
                        dtm.setRowCount(0);
                        for (int i = 0; i < data_list.getLength(); i++) {
                            Node el = data_list.item(i);
                            NamedNodeMap atr = el.getAttributes();
                            String[] lst = {atr.getNamedItem("fio").getNodeValue(), atr.getNamedItem("car").getNodeValue(), atr.getNamedItem("breakdown").getNodeValue(), atr.getNamedItem("date_in").getNodeValue(), atr.getNamedItem("date_out").getNodeValue(), atr.getNamedItem("worker").getNodeValue(), atr.getNamedItem("specialization").getNodeValue()};
                            Vector<String> line = new Vector<String>(Arrays.asList(lst));
                            dtm.addRow(line);
                        }
                    } catch (ParserConfigurationException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } catch (SAXException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                app.notifyAll();
            }
        }
        if (type == 2) {

            synchronized (app) {
//                app.notifyAll();
                try {
                    app.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String[] lst = {"Жан Этье Ламондуа", "Мустанг 5 лет", "Перелом ноги", "11.11.1691", "23.02.1692", "Ванька", "Коновал"};
                Vector<String> line = new Vector<String>(Arrays.asList(lst));
                dtm.addRow(line);
                app.notifyAll();
            }
        }

        if (type == 3) {
            synchronized (app) {
                app.notifyAll();
                try {
                    app.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                FileDialog fds = new FileDialog(app, "Экспорт в html", FileDialog.SAVE);
                fds.setDirectory("A:\\leti-projects\\OOP-LETI-3s-maven\\");
                fds.setFile("data.html");
                fds.setVisible(true);
                String filesave = fds.getDirectory() + fds.getFile();
                if (filesave == null) return;
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(filesave));
                    writer.write(html[0]);
                    writer.write("<tr  style=\"background-color: chocolate;\">\n");
                    for (int i = 0; i < 7; i++) {
                        writer.write("<td>" + columns[i] + "</td>");
                    }
                    writer.write("</tr>\r\n");
                    for(int i = 0; i < list.getRowCount(); i++){
                        writer.write("<tr>\n");
                        for(int j = 0; j < list.getColumnCount(); j++){
                            writer.write("<td>" + (String)list.getValueAt(i,j) + "</td>");
                        }
                        writer.write("</tr>\r\n");
                    }
                    writer.write(html[1]);
                    writer.close();

                }catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }


}
