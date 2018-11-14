package com.fh.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.*;

class TipWindow extends JDialog {
	private static final long serialVersionUID = 8541659783234673950L;
	private static Dimension dim;
	private int x, y;
	private int width, height;
	private static Insets screenInsets;

	public TipWindow(int width, int height) {
		this.width = width;
		this.height = height;
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(
				this.getGraphicsConfiguration());
		x = (int) (dim.getWidth() - width - 3);
		y = (int) (dim.getHeight() - screenInsets.bottom - 3);
		initComponents();
	}

	public void run() {
		for (int i = 0; i <= height; i += 10) {
			try {
				this.setLocation(x, y - i);
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}
		}
		// 此处代码用来实现让消息提示框5秒后自动消失
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		close();
	}

	private void initComponents() {
		this.setSize(width, height);
		this.setLocation(x, y);
		this.setBackground(Color.black);
	}

	public void close() {
		x = this.getX();
		y = this.getY();
		int ybottom = (int) dim.getHeight() - screenInsets.bottom;
		for (int i = 0; i <= ybottom - y; i += 10) {
			try {
				setLocation(x, y + i);
				Thread.sleep(5);
			} catch (InterruptedException ex) {
			}
		}
		dispose();
	}


	public static void main(String args[]){
		InfoUtil tool = new InfoUtil();
		tool.show(
				" QQ提示消息工具类 - Develop By qsuron",
				"1.博客:blog.csdn.net/qsuron\n" +"2.博主:qsuron\n" +"3.开发相关:Eclipse + Java\n" +"4.数据库类型:无 5.1\n" +"5.开发人员:小树\n" +"6.联系方式:QQ 280970160"
				);
	}
}
