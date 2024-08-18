package com.lcl.ui;

import javax.swing.*;
import java.awt.*;

public class ServerFrame extends JFrame{
    private JPanel msgPanel;
    private int y=0;

    public ServerFrame(){
        //先设置窗体大小
        setSize(400,400);
        //设置显示位置,默认居中
        setLocationRelativeTo(null);
        //设置绝对布局
        setLayout(null);
        //设置标题
        setTitle("ChatServer");
        //初始化窗体
        initMsgPanel();
        //设置背景
        getContentPane().setBackground(Color.lightGray);
        //设置窗体大小不可以改
        setResizable(false);
        //关闭窗体退出程序
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //显示窗体
        setVisible(true);
    }

    private void initMsgPanel(){
        msgPanel = new JPanel();
        msgPanel.setBounds(5,5,375,355);
        msgPanel.setBackground(Color.white);
        add(msgPanel);
    }

    private void testClearAll(){
        if(y>=260){
            msgPanel.removeAll();
            y=0;
        }
    }
    //添加消息到界面
    public void addToPanel(String message){
        this.testClearAll();
        JLabel msgLabel = new JLabel(message,SwingConstants.CENTER);
        msgLabel.setForeground(Color.BLACK);
        msgLabel.setBackground(Color.cyan);
        msgLabel.setSize(50,25);
        msgLabel.setOpaque(true);
        //放消息的面板
        JPanel itemsPanel = new JPanel();
        itemsPanel.setBackground(Color.white);
        itemsPanel.setPreferredSize(new Dimension(msgPanel.getWidth(),25));
        itemsPanel.add(msgLabel);
        //设置左右对齐
        FlowLayout layout = (FlowLayout) itemsPanel.getLayout();
        layout.setAlignment(FlowLayout.LEFT);
        msgPanel.add(itemsPanel);
        msgPanel.updateUI();
        y+=25;
    }
}
