package heatmap.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import heatmap.controller.HeatMapController;
import heatmap.util.HeatCalculations;

/**
 * Base UI for HeatMap application
 * @author Patrik Parkkila
 */
public class HeatMapUI extends JFrame {

    /** The serial version ID */
    private static final long serialVersionUID = 1L;

    /** The controller to manage the heat transfer */
    private HeatMapController controller;

    private JButton[][]       buttons;

    public HeatMapUI () {
        // Set up the JFrame

        //
        //
        //
        //
        //

        // create a new instance of the controller to manage
        controller = HeatMapController.getInstance();
        controller.generateGrid();

        //
        //

        setTitle( "Build Your Own Heat Island" );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        // Create a panel to hold the grid of buttons
        JPanel buttonPanel = new JPanel( new GridLayout( 10, 10, 0, 0 ) ); // 10x10
                                                                           // grid
                                                                           // with
                                                                           // 2
                                                                           // pixels
                                                                           // horizontal
                                                                           // and
                                                                           // vertical
                                                                           // gap
        buttons = new JButton[10][10];
        buttonPanel.setBackground( new Color( 232, 255, 225 ) );
        // Add buttons to the panel
        for ( int i = 0; i < 10; i++ ) {
            for ( int j = 0; j < 10; j++ ) {

                try {
                    String imagePath = controller.getFilePath( i + 1, j + 1 );
                    JButton button = createImageButton( imagePath, i, j );
                    buttons[i][j] = button;
                    buttonPanel.add( button );
                }
                catch ( Exception e ) {
                    e.printStackTrace();
                }

            }
        }

        // Create an additional border around the buttons
        LineBorder additionalBorder = new LineBorder( Color.BLACK, 2 );
        CompoundBorder compoundBorder = new CompoundBorder( additionalBorder, new EmptyBorder( 100, 100, 100, 100 ) );
        buttonPanel.setBorder( compoundBorder );

        // Add the button panel to the content pane
        Container container = getContentPane();
        container.setLayout( new BorderLayout() );
        container.add( buttonPanel, BorderLayout.CENTER );

        // Add a new container for the button at the bottom
        JPanel bottomPanel = new JPanel();
        JButton calculateButton = new JButton( "Calculate" );
        bottomPanel.add( calculateButton );

        // Add an ActionListener to the "Calculate" button
        calculateButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ) {
                double calculation = HeatCalculations.getInstance().clusterCalculation();

                String str = String.format( "%10.3f", calculation );

                String message = "The current heat total for your model is: " + str + " (heat units).";
                JOptionPane.showMessageDialog( HeatMapUI.this, message );
                // TODO implementation for calculation section

            }
        } );

        // Add the bottom panel to the content pane at the SOUTH position
        container.add( bottomPanel, BorderLayout.SOUTH );

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar( menuBar );

        // // Calculate the size for the button grid based on 30% of the JFrame
        // // size
        // int frameWidth = getWidth();
        // int frameHeight = getHeight();
        // int gridWidth = (int) ( frameWidth * 0.25 );
        // int gridHeight = (int) ( frameHeight * 0.25 );
        // buttonPanel.setPreferredSize( new Dimension( gridWidth, gridHeight )
        // );

        // setSize( 700, 1200 );
    }

    private JButton createImageButton ( String imagePath, int row, int col ) {
        JButton button = new JButton();
        button.setContentAreaFilled( false ); // Make the button transparent
        button.setBorderPainted( false ); // Remove the button border
        button.setFocusPainted( false ); // Remove the focus border

        try {
            BufferedImage image = ImageIO.read( new File( imagePath ) );
            ImageIcon icon = new ImageIcon( image.getScaledInstance( 50, 50, Image.SCALE_SMOOTH ) );

            // Set the preferred size of the button
            button.setPreferredSize( new Dimension( 50, 50 ) );

            button.setIcon( icon );
        }
        catch ( IOException e ) {
            e.printStackTrace();
        }

        // Add an ActionListener to the button
        button.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed ( ActionEvent e ) {
                handleGridButtonClick( row, col );
            }
        } );

        return button;
    }

    private void handleGridButtonClick ( int row, int col ) {

        // This method will be called when the button is clicked
        String userInput = JOptionPane.showInputDialog( HeatMapUI.this, "Enter one of the options:" );
        // TODO cause update in the current selected button
        // if ( userInput != null ) {
        // JOptionPane.showMessageDialog( HeatMapUI.this, "You entered: " +
        // userInput );
        // }

        // final int numRows = 10; // Set the number of rows in your grid
        // final int numCols = 10; // Set the number of columns in your grid
        //
        // int row = ( buttonIndex ) / numCols; // Calculate the row index
        // int col = ( buttonIndex ) % numCols; // Calculate the column index

        if ( userInput != null ) {
            // need to update the button's image
            ;

            try {
                BufferedImage newImage = ImageIO
                        .read( new File( HeatMapController.getInstance().updateEntry( row + 1, col + 1, userInput ) ) );
                ImageIcon newIcon = new ImageIcon( newImage.getScaledInstance( 100, 100, Image.SCALE_SMOOTH ) );

                // Update the icon of the clicked button
                buttons[row][col].setPreferredSize( new Dimension( 50, 50 ) );
                buttons[row][col].setIcon( newIcon );
            }
            catch ( IOException ex ) {
                ex.printStackTrace();
            }

        }

        // Print or use the row and column information as needed
        System.out.println( "Button clicked in Row: " + row + ", Column: " + col );
    }

    public static void main ( String[] args ) {
        SwingUtilities.invokeLater( () -> {
            HeatMapUI example = new HeatMapUI();
            example.setSize( 800, 800 ); // Set the initial size of the JFrame
            example.setVisible( true );
        } );
    }

    // private String convertDoubletoString ( double val ) {
    // StringBuilder sb = new StringBuilder();
    //
    //
    //
    // }
}
