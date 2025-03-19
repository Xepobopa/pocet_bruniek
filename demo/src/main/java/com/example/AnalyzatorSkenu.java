package com.example;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class AnalyzatorSkenu {
    /**
     * Naskenovany obrazok
     */
    private BufferedImage obrazok;

    /**
     * Sirka nacitaneho obrazka
     */
    private int sirka;

    /**
     * Vyska nacitaneho obrazka
     */
    private int vyska;

    /**
     * Hustota buniek - podiel plochy pokrytej bunkami k ploche skenu
     */
    private double hustotaBuniek;

    /**
     * Array of coordinates of checked sections (that contains cells)
     */
    private List<Section> checkedSections;

    private File file;

    /**
     * Vytvori novy analyzator skenu buniek a spusti zakladnu analyzu
     *
     * @param nazovSuboru
     *            nazov suboru so skenom buniek
     */
    public AnalyzatorSkenu(String nazovSuboru) {
        try {
            file = new File(nazovSuboru);
            obrazok = ImageIO.read(file);
            sirka = obrazok.getWidth();
            vyska = obrazok.getHeight();
            checkedSections = new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Subor " + nazovSuboru
                    + " sa nepodarilo nacitat.");
        }

        analyzuj();
    }

    /**
     * Vrati, ci je pixel na suradniciach [x, y] pixelom bunky.
     */
    private boolean jePixelBunky(int x, int y) {
        if (x < 0 || x >= sirka || y < 0 || y >= vyska)
            return false;

        Color pixel = new Color(obrazok.getRGB(x, y));
        return !pixel.equals(Color.black);
    }

    /**
     * Vypocita hustotu buniek
     */
    private void vypocitajHustotuBuniek() {
        int pocetBunkovychPixelov = 0;
        for (int y = 0; y < vyska; y++)
            for (int x = 0; x < sirka; x++)
                if (jePixelBunky(x, y))
                    pocetBunkovychPixelov++;

        hustotaBuniek = pocetBunkovychPixelov / ((double) sirka * vyska);
    }

    /**
     * Realizuje zakladnu analyzu obrazka so skenom buniek
     */
    private void analyzuj() {
        vypocitajHustotuBuniek();
        // ???
        // ... dalsia analyza obrazku spustena po nacitani skenu
    }

    
    /**
     * Returns the Section containing the cell. Passed coordinates is a first pixel of the cell.
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return Section containing the cell at the specified coordinates
     */
    private Section getCellSection(int x, int y) {
        Section section = new Section(x, y, x, y);
        int curX = x;
        int curY = y;
        boolean cycle = true;

        while (cycle) { 
            // move to the end of current y coord
            if (jePixelBunky(curX + 1, curY)) {
                curX++;
            } else { // move to the curY + 1 start point 
                curY++;
                while (true) { 
                    curX--;

                    if (!jePixelBunky(curX - 1, curY) && jePixelBunky(curX, curY)) 
                        break;

                    if (!jePixelBunky(curX - 1, curY) && ((curX - 1) < section.getStartX())) {
                        cycle = false;
                        break;
                    }
                }
            }

            if (curX > section.getEndX())
                section.setEndX(curX);
            if (curX < section.getStartX())
                section.setStartX(curX);

            if (curY > section.getEndY())
                section.setEndY(curY);
            if (curY < section.getStartY())
                section.setStartY(curY);

            // System.out.println(section.toString());
        }

        return section;
    }

    public boolean pointChecked(int x, int y) {
        if (checkedSections != null) {
            for (Section sec : checkedSections) {
                if (sec.contains(x, y))
                    return true;
            }
        }

        return false;
    }

    /**
     * Vrati pocet buniek na obrazku
     */
    public int getPocetBuniek() {
        int counter = 0;

        Section section = new Section();
        // Section section = getCellSection(694, 2);
        // System.out.println(section.toString());
        // createSection(section);

        for (int y = 0; y < vyska; y++) {
            for (int x = 0; x < sirka; x++) {
                if (jePixelBunky(x, y)) {
                    // check if this point is already checked
                    if (pointChecked(x, y)) {
                        continue;
                    }

                    section = getCellSection(x, y);
                    // somethimes there are some cells with 1 px size, its a bag, so do not count it
                    if (section.getEndX() - section.getStartX() < 2 || section.getEndY() - section.getStartY() < 2) {
                        continue;
                    }

                    checkedSections.add(section);
                    createSection(section);

                    counter++;

                }
            }
        }

        // String newFileName = file.getName().split("\\.")[0] + "_analyzed.png";
        // newFileName = System.getProperty("user.dir") + "/test/results/" + newFileName;

        // We got a new image, with checked sections, so let's save it
        String newFileName = file.getName().split("\\.")[0] + "_analyzed.png";
        File output = new File(newFileName);
        try {
            ImageIO.write(obrazok, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return counter;
    }

    public void createSection(Section section) {
        for (int y = 0; y < vyska; y++) {
            for (int x = 0; x < sirka; x++) {
                if (x >= section.getStartX() && x <= section.getEndX() && 
                   (y == section.getStartY() || y == section.getEndY())) {
                    obrazok.setRGB(x, y, Color.GREEN.getRGB());
                }

                if (y >= section.getStartY() && y <= section.getEndY() && 
                   (x == section.getStartX() || x == section.getEndX())) {
                    obrazok.setRGB(x, y, Color.GREEN.getRGB());
                }
            }
        }
    }

    /**
     * Vrati hustotu buniek (pomer bunkovych pixelov k vsetkym pixelom)
     */
    public double getHustotaBuniek() {
        return hustotaBuniek;
    }

    public static void main(String[] args) {
        AnalyzatorSkenu analyzator = new AnalyzatorSkenu("test/files/SkenBuniek01.png");
        System.out.println("Hustota buniek: "
                        + (analyzator.getHustotaBuniek() * 100) + " %");

        System.out.println("Pocet buniek: " + analyzator.getPocetBuniek());
    }
}
