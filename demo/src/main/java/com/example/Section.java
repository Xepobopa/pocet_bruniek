package com.example;

public class Section {

    /*
     *  Coordinates of section. Left top corner and right bottom corner 
     */

    // start X coord
    private int sx;

    // start Y coord
    private int sy;

    // end X coord
    private int ex;

    // start y coord
    private int ey;

    public Section() {
        sx = 0;
        sy = 0;
        ex = 0;
        ey = 0;
    }

    /**
     * Constructor for Section class
     * @param sx start X coordinate
     * @param sy start Y coordinate
     * @param ex end X coordinate
     * @param ey end Y coordinate
     */
    public Section(int sx, int sy, int ex, int ey) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
    }

    /**
     * Getter for start X coordinate
     * @return start X coordinate
     */
    public int getStartX() {
        return sx;
    }

    /**
     * Getter for start Y coordinate
     * @return start Y coordinate
     */
    public int getStartY() {
        return sy;
    }

    /**
     * Getter for end X coordinate
     * @return end X coordinate
     */
    public int getEndX() {
        return ex;
    }

    /**
     * Getter for end Y coordinate
     * @return end Y coordinate
     */
    public int getEndY() {
        return ey;
    }


    /**
     * Setter for start X coordinate
     * @param sx new start X coordinate
     */
    public void setStartX(int sx) {
        this.sx = sx;
    }

    /**
     * Setter for start Y coordinate
     * @param sy new start Y coordinate
     */
    public void setStartY(int sy) {
        this.sy = sy;
    }

    /**
     * Setter for end X coordinate
     * @param ex new end X coordinate
     */
    public void setEndX(int ex) {
        this.ex = ex;
    }

    /**
     * Setter for end Y coordinate
     * @param ey new end Y coordinate
     */
    public void setEndY(int ey) {
        this.ey = ey;
    }

    @Override
    public String toString() {
        return "StartTopX: " + sx + ", StartTopY: " + sy + ", EndBottomX: " + ex + ", EndBottomY: " + ey;
    } 

    /**
     * Returns true if the point (x, y) is inside the section and false otherwise
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return true if the point is inside the section, false otherwise
     */
    public boolean contains(int x, int y) {
        return x >= sx && x <= ex && y >= sy && y <= ey;
    }
}
