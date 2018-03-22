package life.qbic.presenter.utils;

import com.vaadin.addon.charts.model.style.Color;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.addon.charts.themes.ValoLightTheme;

import java.util.Random;

/**
 * @author fhanssen
 * Helper methods likely used in many presenters.
 */
public final class Helper {

    public static final Color[] colors = new ValoLightTheme().getColors();
    private static Random rand = new Random(0);


    public static SolidColor getRandomOphaque(Color c) {


        String cStr = c.toString().substring(1);

        int r = Integer.parseInt(cStr.substring(0, 2), 16);
        int g = Integer.parseInt(cStr.substring(2, 4), 16);
        int b = Integer.parseInt(cStr.substring(4, 6), 16);

        double opacity = (50 + rand.nextInt(95 - 50)) / 100.0;

        return new SolidColor(r, g, b, opacity);
    }
}
