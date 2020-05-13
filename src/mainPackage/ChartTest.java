package mainPackage;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartTest extends javax.swing.JFrame {

    public ChartTest(String serieMostrar, double[] dataToShow) {
    	
    	XYSeries SERIE = new XYSeries(serieMostrar);
		for(int i=0; i<dataToShow.length; i++){
			SERIE.add(i,dataToShow[i]);
		}
		XYSeriesCollection DADOS = new XYSeriesCollection(SERIE);
	    JFreeChart CHART = ChartFactory.createXYLineChart(
	    	serieMostrar,
	        "Number of Points", 
	        "Normalized Values", 
	        DADOS,
	        PlotOrientation.VERTICAL,
	        true,
	        true,
	        false
	    );
    	
        ChartPanel cp = new ChartPanel(CHART) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(640, 480);
            }
        };
        cp.setMouseWheelEnabled(true);
        add(cp);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(ChartTest.class.getResource("PPG_CatarinaOliveira.png")));
    }
}