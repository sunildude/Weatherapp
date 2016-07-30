/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testweatherapp;

import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class WeatherAPPUI extends javax.swing.JFrame {

    public WeatherAPPUI() {
        initComponents();
        globalobj = (new GlobalWeather()).getGlobalWeatherSoap12();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtCountry = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        listCity = new javax.swing.JList();
        btnLoadCity = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCities = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Weather Now");

        jLabel1.setText("Enter Country Name");

        txtCountry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCountryActionPerformed(evt);
            }
        });

        listCity.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "item1" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listCity.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listCityMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listCity);

        btnLoadCity.setText("Load Cities");
        btnLoadCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadCityActionPerformed(evt);
            }
        });

        txtCities.setColumns(20);
        txtCities.setRows(5);
        jScrollPane2.setViewportView(txtCities);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(193, 193, 193)
                            .addComponent(btnLoadCity, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(136, 136, 136)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLoadCity)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoadCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadCityActionPerformed
        txtCities.removeAll();
        listCity.removeAll();
        ArrayList<String> citiesArr = new ArrayList<String>();
        String country = txtCountry.getText();
        String cities = globalobj.getCitiesByCountry(country);
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(cities));
            Document doc = builder.parse(is);
            doc.normalize();
            NodeList retList = doc.getElementsByTagName("Table");

            for (int i = 0; i < retList.getLength(); i++) {
                int addCityFlag = 0;
                NodeList tableList = retList.item(i).getChildNodes();
                for (int j = 0; j < tableList.getLength(); j++) {
                    Node tempNode = tableList.item(j);

                    if (tempNode.getNodeName().equalsIgnoreCase("country") && tempNode.getTextContent().equalsIgnoreCase(country)) {
                        addCityFlag = 1;
                    }
                    if (tempNode.getNodeName().equalsIgnoreCase("city") && addCityFlag == 1) {
                        citiesArr.add(tempNode.getTextContent());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        DefaultListModel lm = new DefaultListModel();
        for (String s : citiesArr) {
            System.out.println("City name:" + s);
            lm.addElement(s);
        }
        
        //lm.removeAllElements();
        listCity.setModel(lm);
        listCity.setVisible(true);


    }//GEN-LAST:event_btnLoadCityActionPerformed

    private void txtCountryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCountryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCountryActionPerformed

    private void listCityMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listCityMouseClicked
        String country = txtCountry.getText();
        String city=listCity.getSelectedValue().toString();
        
        System.out.println("City:"+city+"selected for country:"+country);
        
        String weatherXml=globalobj.getWeather(city, country);
        if(weatherXml.isEmpty() || weatherXml.equalsIgnoreCase("Data Not Found")){
            txtCities.setText("No Weather information available for "+city+" in "+country);
            return;
        }
        String weatherInfo=formatWeatherXml(weatherXml);
        txtCities.setText(weatherInfo);
        
        
    }//GEN-LAST:event_listCityMouseClicked

    private String formatWeatherXml(String weather){
        String outputStr=new String();
        try{
            
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(weather));
            Document doc = builder.parse(is);
            doc.normalize();
            NodeList retList = doc.getElementsByTagName("CurrentWeather");
            for(int i=0;i<retList.getLength();i++){
               NodeList weatherList=retList.item(i).getChildNodes();
               for(int j=0;j<weatherList.getLength();j++){
                   Node weatherNode=weatherList.item(j);
                   if(weatherNode.getNodeName().isEmpty() || weatherNode.getNodeName().equalsIgnoreCase("#text")
                           ||weatherNode.getNodeName().equalsIgnoreCase("status") ){
                   continue;
               }
                   if(outputStr.isEmpty()){
                       outputStr=weatherNode.getNodeName()+":"+weatherNode.getTextContent();
                   }else{
                   outputStr=outputStr+"\n"+weatherNode.getNodeName()+":"+weatherNode.getTextContent();
                   }
               }
               
               
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputStr;
    }
    GlobalWeatherSoap globalobj;

    public static void main(String args[]) {
        GetCitiesByCountryResponse obj;
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WeatherAPPUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLoadCity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listCity;
    private javax.swing.JTextArea txtCities;
    private javax.swing.JTextField txtCountry;
    // End of variables declaration//GEN-END:variables
}
