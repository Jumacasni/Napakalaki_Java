/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import napakalakijava.BadConsequence;

/**
 *
 * @author jumacasni
 */
public class PendingBadConsequenceView extends javax.swing.JPanel {

    BadConsequence pendingBadConsequenceModel;
    
    public void setPendingBadConsequence(BadConsequence pbc){
        pendingBadConsequenceModel = pbc;
        pendingBadConsequence.setText(pendingBadConsequenceModel.toString());
        
        repaint();
        revalidate();
    }
    
    public javax.swing.JTextArea getText(){
        return pendingBadConsequence;
    }
    
    public PendingBadConsequenceView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        pendingBadConsequence = new javax.swing.JTextArea();

        pendingBadConsequence.setColumns(20);
        pendingBadConsequence.setRows(5);
        jScrollPane1.setViewportView(pendingBadConsequence);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea pendingBadConsequence;
    // End of variables declaration//GEN-END:variables
}